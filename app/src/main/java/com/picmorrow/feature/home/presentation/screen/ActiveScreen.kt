package com.picmorrow.feature.home.presentation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.picmorrow.R
import com.picmorrow.feature.phototasks.presentation.common.components.PhotoTaskEmptyState
import com.picmorrow.feature.phototasks.presentation.common.components.PhotoTaskList
import com.picmorrow.feature.phototasks.presentation.common.model.PhotoTaskEmptyStateConfig
import com.picmorrow.feature.phototasks.presentation.common.model.PhotoTaskListColors
import com.picmorrow.feature.phototasks.domain.model.PhotoTask

@Composable
@Suppress("LongParameterList")
internal fun ActiveScreen(
    tasks: List<PhotoTask>,
    colors: PhotoTaskListColors,
    darkTheme: Boolean,
    onCompleteClick: (Long) -> Unit,
    onTakePhotoClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (tasks.isNotEmpty()) {
        PhotoTaskList(
            tasks = tasks,
            colors = colors,
            darkTheme = darkTheme,
            onCompleteClick = onCompleteClick,
            modifier = modifier,
        )
    } else {
        PhotoTaskEmptyState(
            config = PhotoTaskEmptyStateConfig(
                titleRes = R.string.active_empty_title,
                bodyRes = R.string.active_empty_body,
                actionRes = R.string.active_take_photo,
                iconRes = R.drawable.ic_empty_camera,
            ),
            colors = colors,
            onActionClick = onTakePhotoClick,
            modifier = modifier.fillMaxSize(),
        )
    }
}
