package com.saraiva.github.data.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.log
import com.saraiva.github.core.Constants
import com.saraiva.github.core.network.GHService
import com.saraiva.github.data.model.GitHubRepoResponse
import com.saraiva.github.domain.entity.GithubRepoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

class GithubRepoPagingSource(private val service: GHService) :
    PagingSource<Int, GithubRepoEntity>() {
    override fun getRefreshKey(state: PagingState<Int, GithubRepoEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubRepoEntity> {
        return withContext(Dispatchers.IO) {
            try {
                val currentNumber = params.key ?: 1
                Log.d("Page", "Loading more items")
                val response = service.getRepos(page = currentNumber).items
                Log.d("Page", currentNumber.toString())
                Log.d("Quantity", response.size.toString())
                LoadResult.Page(
                    data = response.map(::toEntity),
                    prevKey = if(currentNumber > 1) currentNumber - 1 else null,
                    nextKey = currentNumber + 1
                )
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }
    }

    fun toEntity(response: GitHubRepoResponse): GithubRepoEntity {
        with(response) {
            return GithubRepoEntity(
                id = id,
                name = name,
                fullName = fullName,
                description = description,
                htmlUrl = htmlUrl,
                language = language,
                stargazersCount = stargazersCount,
                forksCount = forksCount,
                ownerLogin = owner.login,
                ownerAvatarUrl = owner.avatarUrl,
                lastUpdate = SimpleDateFormat(Constants.ISO_DATETIME_FORMAT).parse(updatedAt)
                    ?: SimpleDateFormat(Constants.ISO_DATETIME_FORMAT).parse(createdAt)!!,
            )
        }
    }
}