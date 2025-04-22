package com.saraiva.github.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.saraiva.github.core.Constants
import com.saraiva.github.core.network.GHService
import com.saraiva.github.data.datasource.GithubRepoPagingSource
import com.saraiva.github.data.datasource.db.GithubRepoDao
import com.saraiva.github.domain.entity.GithubRepoEntity
import com.saraiva.github.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import java.sql.Connection
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val service: GHService, private val dao: GithubRepoDao) : Repository {

    private lateinit var pager: Pager<Int, GithubRepoEntity>

    override suspend fun getRepos(hasConnection: Boolean): Flow<PagingData<GithubRepoEntity>> {

        this.pager = Pager(
            config = PagingConfig(
                pageSize = Constants.ITEMS_PER_PAGE,
                prefetchDistance = 100
            ), pagingSourceFactory =
                { GithubRepoPagingSource(
                    service = service,
                    dao = dao,
                    hasConnection
                ) }

        )

        return pager.flow

    }

}