package com.picmorrow.feature.phototasks.presentation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.picmorrow.R
import com.picmorrow.feature.phototasks.presentation.components.PhotoTaskEmptyState
import com.picmorrow.feature.phototasks.presentation.model.PhotoTaskEmptyStateConfig

@Composable
internal fun ActiveScreen(
    hasPhotoTasks: Boolean,
    colors: HomeColors,
    onTakePhotoClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (hasPhotoTasks) {
        ActiveTaskListPlaceholder(modifier = modifier)
    } else {
        PhotoTaskEmptyState(
            config = PhotoTaskEmptyStateConfig(
                titleRes = R.string.active_empty_title,
                bodyRes = R.string.active_empty_body,
                actionRes = R.string.active_take_photo,
                iconRes = R.drawable.ic_empty_camera,
                iconBackgroundColor = colors.emptyIconBackground,
                primaryTextColor = colors.primaryText,
                secondaryTextColor = colors.secondaryText,
            ),
            onActionClick = onTakePhotoClick,
            modifier = modifier.fillMaxSize(),
        )
    }
}

@Composable
private fun ActiveTaskListPlaceholder(modifier: Modifier = Modifier) {
    androidx.compose.foundation.layout.Box(modifier = modifier.fillMaxSize())
}
