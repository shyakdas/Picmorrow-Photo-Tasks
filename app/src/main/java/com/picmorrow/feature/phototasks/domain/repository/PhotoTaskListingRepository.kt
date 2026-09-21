package com.picmorrow.feature.phototasks.domain.repository

import com.picmorrow.feature.phototasks.domain.model.PhotoTask
import kotlinx.coroutines.flow.Flow

interface PhotoTaskListingRepository {
    fun observeActive(): Flow<List<PhotoTask>>

    fun observeCompleted(): Flow<List<PhotoTask>>

    suspend fun complete(id: Long, completedAtMillis: Long)
}
