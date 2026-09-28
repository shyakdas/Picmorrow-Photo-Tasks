package com.picmorrow.feature.phototasks.domain.repository

fun interface PhotoTaskStatusRepository {
    suspend fun hasPhotoTasks(): Boolean
}
