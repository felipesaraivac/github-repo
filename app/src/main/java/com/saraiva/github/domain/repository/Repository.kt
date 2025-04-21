package com.saraiva.github.domain.repository

import androidx.paging.PagingData
import com.saraiva.github.domain.entity.GithubRepoEntity
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getRepos(): Flow<PagingData<GithubRepoEntity>>

}