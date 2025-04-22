package com.saraiva.github.data.datasource.db.model

import com.saraiva.github.data.model.GitHubRepoResponse

object RepoMapper {
    fun toDbEntity(response: GitHubRepoResponse, page: Int = 1): GithubRepo {
        with(response) {
            return GithubRepo(
                id = id,
                name = name,
                fullName = fullName,
                description = description ?: "",
                htmlUrl = htmlUrl,
                language = language ?: "",
                stargazersCount = stargazersCount,
                forksCount = forksCount,
                ownerLogin = owner.login,
                ownerAvatarUrl = owner.avatarUrl,
                lastUpdate = updatedAt,
                page = page
            )
        }
    }
}
