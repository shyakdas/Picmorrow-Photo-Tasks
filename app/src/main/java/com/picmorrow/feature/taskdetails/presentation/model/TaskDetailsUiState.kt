package com.picmorrow.feature.taskdetails.presentation.model

import com.picmorrow.feature.phototasks.domain.model.PhotoTask

sealed interface TaskDetailsUiState {
    data object Loading : TaskDetailsUiState

    data object NotFound : TaskDetailsUiState

    data object Deleted : TaskDetailsUiState

    data class Content(
        val task: PhotoTask,
        val isCompleting: Boolean = false,
    ) : TaskDetailsUiState
}
