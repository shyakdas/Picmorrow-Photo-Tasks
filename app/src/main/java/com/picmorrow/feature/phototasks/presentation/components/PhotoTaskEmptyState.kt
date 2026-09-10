@file:Suppress("MagicNumber")

package com.picmorrow.feature.phototasks.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.picmorrow.feature.phototasks.presentation.model.PhotoTaskEmptyStateConfig

@Composable
internal fun PhotoTaskEmptyState(
    config: PhotoTaskEmptyStateConfig,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxWidth()) {
        EmptyIcon(
            iconRes = config.iconRes,
            backgroundColor = config.iconBackgroundColor,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = EMPTY_ICON_CENTER_OFFSET),
        )
        EmptyCopy(
            config = config,
            modifier = Modifier.align(Alignment.Center),
        )
        EmptyActionButton(
            config = config,
            onActionClick = onActionClick,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = EMPTY_BUTTON_CENTER_OFFSET),
        )
    }
}

private val EMPTY_ICON_CENTER_OFFSET = (-118).dp
private val EMPTY_BUTTON_CENTER_OFFSET = 116.dp
