package com.saraiva.github.data.datasource.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "github_repo")
data class GithubRepo(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val name: String,
    val fullName: String,
    val description: String,
    val htmlUrl: String,
    val language: String,
    val stargazersCount: Int,
    val forksCount: Int,
    val ownerLogin: String,
    val ownerAvatarUrl: String,
    val lastUpdate: String,
    val page: Int
)