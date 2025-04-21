package com.saraiva.github.ui.screens.rankings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.saraiva.github.R
import com.saraiva.github.domain.entity.GithubRepoEntity
import com.saraiva.github.ui.theme.iconSize
import com.saraiva.github.ui.theme.spacing
import java.util.Date

@Composable
fun CardHorizontalRanking(
    modifier: Modifier = Modifier,
    repo: GithubRepoEntity,
) {
    ListItem(

        overlineContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.extraSmall)
            ) {
                AsyncImage(
                    model = repo.ownerAvatarUrl,
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(MaterialTheme.spacing.semiLarge)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.secondaryContainer
                        )
                )
                Text(
                    repo.ownerLogin,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.W600,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = MaterialTheme.spacing.small),
                )
            }
        },

        headlineContent = {
            Text(repo.name, style = MaterialTheme.typography.titleMedium)
        }, supportingContent = {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.spacing.extraSmall)
            ) {

                Text(
                    repo.description ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.W400,
                    maxLines = 2
                )

                Row(Modifier.padding(top = MaterialTheme.spacing.extraSmall)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(0.2f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_alt_route_24),
                            tint = MaterialTheme.colorScheme.secondary,
                            contentDescription = "Forks",
                            modifier = Modifier.size(MaterialTheme.iconSize.small)
                        )
                        Text(
                            repo.forksCount.toString(),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Star,
                            tint = MaterialTheme.colorScheme.secondary,
                            contentDescription = "Stargazed",
                            modifier = Modifier.size(MaterialTheme.iconSize.small)
                        )
                        Text(
                            repo.forksCount.toString(),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }

            }
        })
    HorizontalDivider(
        color = MaterialTheme.colorScheme.secondaryContainer,
        thickness = 1.dp,
        modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small)
    )
    Box(modifier = Modifier.height(MaterialTheme.spacing.small))
}

@Preview
@Composable
fun CardHorizontalRankingPreview() {
    CardHorizontalRanking(
        repo = GithubRepoEntity(
            id = 1,
            name = "amorinha",
            fullName = "Design-de-interiores",
            description = "Teste",
            htmlUrl = "Teste",
            language = "Teste",
            stargazersCount = 1,
            forksCount = 1,
            ownerLogin = "amandadev",
            ownerAvatarUrl = "Teste",
            lastUpdate = Date()
        )
    )
}

@Preview
@Composable
fun CharRoundedPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        for (i in 0..5) {

            CardHorizontalRanking(
                repo = GithubRepoEntity(
                    id = i.toLong(),
                    name = "amorinha",
                    fullName = "Design-de-interiores",
                    description = "Teste",
                    htmlUrl = "Teste",
                    language = "Teste",
                    stargazersCount = 1,
                    forksCount = 1,
                    ownerLogin = "amandadev",
                    ownerAvatarUrl = "Teste",
                    lastUpdate = Date()
                )
            )
        }
    }
}

@Composable
fun CharRounded(
    text: String, modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .size(40.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
            ), contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleMedium
        )
    }
}