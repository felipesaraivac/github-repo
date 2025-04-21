@file:OptIn(ExperimentalMaterial3Api::class)

package com.saraiva.github.ui.screens.inprogress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.saraiva.github.R
import com.saraiva.github.core.utils.State
import com.saraiva.github.domain.entity.GithubRepoEntity
import com.saraiva.github.ui.components.ProgressBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingListScreen(
    modifier: Modifier = Modifier,
    viewModel: InProgresViewModel = viewModel()
) {
    val state = viewModel.state.collectAsState(initial = State.loading()).value
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.screen_ranking))
                },
                navigationIcon = {

                }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InProgressContent(modifier = modifier, state = State.loading())
        }
    }
}

@Composable
fun InProgressContent(
    modifier: Modifier = Modifier,
    state: State<List<GithubRepoEntity>>,
) {
    when (state) {
        is State.Failed -> {
            Text(text = state.message)
        }

        is State.Loading -> ProgressBar()

        is State.Success -> {
            StateMessage(
                modifier = modifier,
            )
        }
    }
}

@Composable
fun StateMessage(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxHeight(.5f).fillMaxWidth(.7f)) {
        Text(stringResource(R.string.screen_in_progress))
    }

}