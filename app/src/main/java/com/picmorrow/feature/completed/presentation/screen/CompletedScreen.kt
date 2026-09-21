package com.picmorrow.feature.completed.presentation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.picmorrow.R
import com.picmorrow.feature.phototasks.domain.model.PhotoTask
import com.picmorrow.feature.phototasks.presentation.common.components.PhotoTaskEmptyState
import com.picmorrow.feature.phototasks.presentation.common.components.PhotoTaskList
import com.picmorrow.feature.phototasks.presentation.common.model.PhotoTaskEmptyStateConfig
import com.picmorrow.feature.phototasks.presentation.common.model.PhotoTaskListColors

@Composable
internal fun CompletedScreen(
    tasks: List<PhotoTask>,
    colors: PhotoTaskListColors,
    darkTheme: Boolean,
    onTakePhotoClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (tasks.isNotEmpty()) {
        PhotoTaskList(
            tasks = tasks,
            colors = colors,
            darkTheme = darkTheme,
            onCompleteClick = null,
            modifier = modifier,
            bottomPadding = 16.dp,
        )
    } else {
        PhotoTaskEmptyState(
            config = PhotoTaskEmptyStateConfig(
                titleRes = R.string.completed_empty_title,
                bodyRes = R.string.completed_empty_body,
                actionRes = R.string.active_take_photo,
                iconRes = R.drawable.ic_bottom_check,
                actionIconRes = R.drawable.ic_empty_camera,
            ),
            colors = colors,
            onActionClick = onTakePhotoClick,
            modifier = modifier.fillMaxSize(),
        )
    }
}
