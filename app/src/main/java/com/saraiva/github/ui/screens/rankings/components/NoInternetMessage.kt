package com.saraiva.github.ui.screens.rankings.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.saraiva.github.R
import com.saraiva.github.ui.theme.spacing

@Composable
fun NoInternetMessage(
    modifier: Modifier = Modifier,
    visible: State<Boolean>,
) {
    AnimatedVisibility(
        visible = !visible.value,
        enter = expandIn(),
        exit = shrinkVertically()
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.error)
                .padding(vertical = MaterialTheme.spacing.extraSmall)
        ) {
            Text(
                stringResource((R.string.no_internet)),
                color = MaterialTheme.colorScheme.onError,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}