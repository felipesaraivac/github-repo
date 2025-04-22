package com.saraiva.github.domain.entity

import com.saraiva.github.core.Constants
import com.saraiva.github.data.datasource.db.model.GithubRepo
import com.saraiva.github.data.model.GitHubRepoResponse
import java.text.SimpleDateFormat

object EntityMapper {
    fun fromDbEntity(entity: GithubRepo): GithubRepoEntity {
        with(entity) {
            return GithubRepoEntity(
                id = id,
                name = name,
                fullName = fullName,
                description = description,
                htmlUrl = htmlUrl,
                language = language,
                stargazersCount = stargazersCount,
                forksCount = forksCount,
                ownerLogin = ownerLogin,
                ownerAvatarUrl = ownerAvatarUrl,
                lastUpdate = SimpleDateFormat(Constants.ISO_DATETIME_FORMAT).parse(lastUpdate)
            )
        }
    }

    fun fromResponse(response: GitHubRepoResponse): GithubRepoEntity {
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