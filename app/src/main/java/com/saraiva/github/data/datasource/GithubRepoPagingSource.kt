package com.saraiva.github.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.saraiva.github.core.Constants
import com.saraiva.github.core.network.GHService
import com.saraiva.github.data.datasource.db.GithubRepoDao
import com.saraiva.github.data.datasource.db.model.RepoMapper
import com.saraiva.github.data.datasource.db.model.RepoMapper.toDbEntity
import com.saraiva.github.data.model.GitHubRepoResponse
import com.saraiva.github.domain.entity.EntityMapper
import com.saraiva.github.domain.entity.GithubRepoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GithubRepoPagingSource(
    private val service: GHService,
    private val dao: GithubRepoDao,
    val isConnected: Boolean
) :
    PagingSource<Int, GithubRepoEntity>() {


    override fun getRefreshKey(state: PagingState<Int, GithubRepoEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubRepoEntity> {
        return withContext(Dispatchers.IO) {
            val currentPage = params.key ?: 1
            val cached = dao.getPagedList(currentPage)
            try {
                if (cached.isNotEmpty() && cached.size >= Constants.ITEMS_PER_PAGE) {
                    if (isConnected) updateInBackground(currentPage)
                    return@withContext LoadResult.Page(
                        data = cached.map(EntityMapper::fromDbEntity),
                        prevKey = if (currentPage > 1) currentPage - 1 else null,
                        nextKey = currentPage + 1
                    )
                } else {
                    if (!isConnected) return@withContext LoadResult.Error(Exception("No internet connection"))
                    val response = service.getRepos(page = currentPage).items
                    launch { dao.insertAll(response.map(::toDbEntity)) }
                    LoadResult.Page(
                        data = response.map(EntityMapper::fromResponse),
                        prevKey = if (currentPage > 1) currentPage - 1 else null,
                        nextKey = currentPage + 1
                    )
                }
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }
    }

    private fun updateInBackground(page: Int, list: List<GitHubRepoResponse>? = null) =
        GlobalScope.launch(Dispatchers.IO) {
            runCatching {
                if (list != null) {
                    dao.insertAll(list.map(::toDbEntity))
                    return@launch
                }
                val response = service.getRepos(page = page).items
                dao.insertAll(
                    response.map { RepoMapper.toDbEntity(it, page) }
                )
            }
        }

}