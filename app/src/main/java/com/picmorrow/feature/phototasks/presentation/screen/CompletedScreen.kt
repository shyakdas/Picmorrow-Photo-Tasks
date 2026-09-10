package com.picmorrow.feature.phototasks.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun CompletedScreen(
    hasCompletedPhotoTasks: Boolean,
    modifier: Modifier = Modifier,
) {
    if (hasCompletedPhotoTasks) {
        CompletedTaskListPlaceholder(modifier = modifier)
    } else {
        Box(modifier = modifier.fillMaxSize())
    }
}

@Composable
private fun CompletedTaskListPlaceholder(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize())
}
