package com.picmorrow.feature.taskdetails.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.picmorrow.feature.taskdetails.presentation.TaskDetailsViewModel
import com.picmorrow.feature.taskdetails.presentation.model.TaskDetailsUiState
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun TaskDetailsRoute(
    taskId: Long,
    onBackClick: () -> Unit,
    viewModel: TaskDetailsViewModel = koinViewModel(parameters = { parametersOf(taskId) }),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        if (uiState === TaskDetailsUiState.Deleted) {
            onBackClick()
        }
    }

    TaskDetailsScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onSaveToGalleryClick = viewModel::saveToGalleryAndComplete,
        onDeletePhotoAndTaskClick = viewModel::deletePhotoAndTask,
        onReviewLaterClick = viewModel::completeForReview,
    )
}
