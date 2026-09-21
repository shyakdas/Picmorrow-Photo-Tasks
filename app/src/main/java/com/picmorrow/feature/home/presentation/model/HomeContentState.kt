package com.picmorrow.feature.home.presentation.model

import com.picmorrow.feature.phototasks.domain.model.PhotoTask

internal data class HomeContentState(
    val activeTasks: List<PhotoTask> = emptyList(),
    val completedTasks: List<PhotoTask> = emptyList(),
    val isLoading: Boolean = false,
)
