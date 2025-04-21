package com.saraiva.github.data.model

import com.google.gson.annotations.SerializedName

data class GetReposResponse(val items: List<GitHubRepoResponse>)

data class GitHubRepoResponse(
    val id: Long,
    @SerializedName("node_id") val nodeId: String,
    val name: String,
    @SerializedName("full_name") val fullName: String,
    val private: Boolean,
    val owner: Owner,
    @SerializedName("html_url") val htmlUrl: String,
    val description: String?,
    val url: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("pushed_at") val pushedAt: String,
    val homepage: String?,
    val size: Int,
    @SerializedName("stargazers_count") val stargazersCount: Int,
    @SerializedName("watchers_count") val watchersCount: Int,
    val language: String?,
    @SerializedName("forks_count") val forksCount: Int,
    val archived: Boolean,
    val disabled: Boolean,
//    val license: String?, // pode trocar pra um objeto se precisar
    @SerializedName("allow_forking") val allowForking: Boolean,
    val topics: List<String>,
    val visibility: String,
    val forks: Int,
    val score: Double
)


data class Owner(
    val login: String,
    val id: Long,
    @SerializedName("avatar_url") val avatarUrl: String,
    val url: String,
    @SerializedName("repos_url") val reposUrl: String,
)