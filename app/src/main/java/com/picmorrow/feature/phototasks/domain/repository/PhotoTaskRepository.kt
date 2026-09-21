package com.picmorrow.feature.phototasks.domain.repository

import com.picmorrow.feature.phototasks.domain.model.PhotoTaskDraft

interface PhotoTaskRepository {
    suspend fun savePhotoTask(draft: PhotoTaskDraft): Long
}
