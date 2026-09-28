package com.picmorrow.feature.phototasks.domain.usecase

import com.picmorrow.feature.phototasks.domain.repository.PhotoTaskStatusRepository

class HasPhotoTasksUseCase(private val repository: PhotoTaskStatusRepository) {
    suspend operator fun invoke(): Boolean = repository.hasPhotoTasks()
}
