package com.picmorrow.feature.phototasks.data.repository

import com.picmorrow.feature.phototasks.data.local.PhotoTaskDao
import com.picmorrow.feature.phototasks.data.local.PhotoTaskEntity
import com.picmorrow.feature.phototasks.domain.model.PhotoTaskDraft
import com.picmorrow.feature.phototasks.domain.repository.PhotoTaskRepository

class PhotoTaskRepositoryImpl(private val dao: PhotoTaskDao) : PhotoTaskRepository {
    override suspend fun savePhotoTask(draft: PhotoTaskDraft): Long =
        dao.insert(
            PhotoTaskEntity(
                photoPath = draft.photoPath,
                category = draft.category,
                title = draft.title,
                notes = draft.notes,
                reminderAtMillis = draft.reminderAtMillis,
            ),
        )
}
