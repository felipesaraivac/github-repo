package com.saraiva.github.domain.entity

import java.util.Date

data class GithubRepoEntity(
    val id: Long,
    val name: String,
    val fullName: String,
    val description: String?,
    val htmlUrl: String,
    val language: String?,
    val stargazersCount: Int,
    val forksCount: Int,
    val ownerLogin: String,
    val ownerAvatarUrl: String,
    val lastUpdate: Date,
)
