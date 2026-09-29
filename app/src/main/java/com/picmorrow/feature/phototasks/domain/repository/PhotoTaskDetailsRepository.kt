package com.picmorrow.feature.phototasks.domain.repository

import com.picmorrow.feature.phototasks.domain.model.PhotoTask

interface PhotoTaskDetailsRepository {
    suspend fun findById(id: Long): PhotoTask?

    suspend fun complete(id: Long, completedAtMillis: Long, isSavedToGallery: Boolean)

    suspend fun delete(id: Long)
}
