package com.picmorrow.feature.phototasks.data.repository

import com.picmorrow.feature.phototasks.data.local.PhotoTaskDao
import com.picmorrow.feature.phototasks.data.local.PhotoTaskEntity
import com.picmorrow.feature.phototasks.domain.model.PhotoTaskDraft
import com.picmorrow.feature.phototasks.domain.model.PhotoTask
import com.picmorrow.feature.phototasks.domain.repository.PhotoTaskRepository
import com.picmorrow.feature.phototasks.domain.repository.PhotoTaskListingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PhotoTaskRepositoryImpl(private val dao: PhotoTaskDao) : PhotoTaskRepository, PhotoTaskListingRepository {
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

    override fun observeActive(): Flow<List<PhotoTask>> =
        dao.observeActive().map { tasks -> tasks.map { it.toDomain() } }

    override fun observeCompleted(): Flow<List<PhotoTask>> =
        dao.observeCompleted().map { tasks -> tasks.map { it.toDomain() } }

    override suspend fun complete(id: Long, completedAtMillis: Long) {
        dao.complete(id, completedAtMillis)
    }

    private fun PhotoTaskEntity.toDomain() = PhotoTask(
        id = id,
        photoPath = photoPath,
        category = category,
        title = title,
        notes = notes,
        reminderAtMillis = reminderAtMillis,
        completedAtMillis = completedAtMillis,
    )
}
