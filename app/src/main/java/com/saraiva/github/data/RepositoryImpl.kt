package com.saraiva.github.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.saraiva.github.core.Constants
import com.saraiva.github.core.network.GHService
import com.saraiva.github.data.datasource.GithubRepoPagingSource
import com.saraiva.github.domain.entity.GithubRepoEntity
import com.saraiva.github.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val service: GHService) : Repository {

    private lateinit var pager: Pager<Int, GithubRepoEntity>

    override suspend fun getRepos(): Flow<PagingData<GithubRepoEntity>> {

        this.pager = Pager(
            config = PagingConfig(
                pageSize = Constants.ITEMS_PER_PAGE,
                prefetchDistance = 100
            ), pagingSourceFactory =
                { GithubRepoPagingSource(
                    service = service
                ) }

        )

        return pager.flow

    }

}