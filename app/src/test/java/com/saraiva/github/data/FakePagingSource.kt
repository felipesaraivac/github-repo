package com.saraiva.github.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.saraiva.github.domain.entity.GithubRepoEntity

class FakePagingSource(private val items: List<GithubRepoEntity>) :
    PagingSource<Int, GithubRepoEntity>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubRepoEntity> {
        return LoadResult.Page(
            data = items,
            prevKey = null,
            nextKey = null
        )
    }

    override fun getRefreshKey(state: PagingState<Int, GithubRepoEntity>): Int? = null

}