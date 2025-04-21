package com.saraiva.github.core.network

import com.saraiva.github.core.Constants
import com.saraiva.github.data.model.GetReposResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GHService {

    @GET("search/repositories")
    suspend fun getRepos(
        @Query("per_page") perPage: Int = Constants.ITEMS_PER_PAGE,
        @Query("q") query: String = "language:kotlin",
        @Query("sort") sort: String = "stars",
        @Query("page") page: Int
    ): GetReposResponse
}
