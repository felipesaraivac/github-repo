@file:OptIn(ExperimentalMaterial3Api::class)

package com.saraiva.github.ui.screens.rankings

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.saraiva.github.R
import com.saraiva.github.domain.entity.GithubRepoEntity
import com.saraiva.github.ui.components.ProgressBar
import com.saraiva.github.ui.screens.rankings.components.CardHorizontalRanking
import com.saraiva.github.ui.theme.spacing
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingListScreen(
    modifier: Modifier = Modifier,
    viewModel: RankingViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getTopRepos()
    }
    val repos = viewModel.repos.collectAsLazyPagingItems()
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.screen_ranking))
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .displayCutoutPadding(),
        ) {
            PullToRefreshBox(
                state = rememberPullToRefreshState(),
                isRefreshing = repos.loadState.refresh is LoadState.Loading && repos.itemCount > 0,
                onRefresh = {
                    repos.refresh();
                },
            ) {
                RankingContent(
                    modifier,
                    repos,
                )
            }
        }
    }
}

@Composable
fun RankingContent(
    modifier: Modifier = Modifier,
    repos: LazyPagingItems<GithubRepoEntity>,
) {

    when {
        repos.loadState.refresh is LoadState.Error && repos.itemCount == 0 -> {
            Box(
                modifier = modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(stringResource(R.string.data_could_not_be_loaded))
                    Button(onClick = { repos.refresh() }) {
                        Text(stringResource(R.string.try_again))
                    }
                }
            }
        }

        repos.loadState.append is LoadState.Error -> {
            val context = LocalContext.current
            Toast.makeText(
                context,
                stringResource(R.string.data_could_not_be_loaded),
                Toast.LENGTH_SHORT
            ).show()
        }

        repos.loadState.refresh is LoadState.Loading -> {
            ProgressBar()
        }
    }
    RankingList(
        modifier = modifier,
        repos = repos,
    )

}

@Composable
fun RankingList(
    repos: LazyPagingItems<GithubRepoEntity>,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    val showButton by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0
        }
    }
    val scope = rememberCoroutineScope()
    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        items(repos.itemCount, key = { index ->
            val repo = repos[index]
            repo?.id!!
        }) { index ->
            val repo = repos[index]
            repo?.let {
                CardHorizontalRanking(repo = repo)
            }
        }

    }
    AnimatedVisibility(
        visible = showButton,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        ScrollToTopButton(onClick = {
            scope.launch {
                listState.animateScrollToItem(0)
            }
        })
    }
}

@Composable
fun ScrollToTopButton(onClick: () -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(end = MaterialTheme.spacing.small),
        contentAlignment = Alignment.TopCenter
    ) {
        IconButton(
            onClick = onClick,
            Modifier.background(
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f),
                shape = CircleShape,
            )
        ) {
            Icon(imageVector = Icons.Default.ArrowUpward, contentDescription = "Scroll to top")
        }
    }
}

@Preview
@Composable
fun EmptyStatePreview(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(R.string.data_could_not_be_loaded),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleMedium
            )
            Button(onClick = { }) {
                Text(stringResource(R.string.try_again))
            }
        }
    }
}