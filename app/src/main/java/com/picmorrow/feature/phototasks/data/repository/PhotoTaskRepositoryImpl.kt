package com.picmorrow.feature.phototasks.data.repository

import com.picmorrow.feature.phototasks.data.local.PhotoTaskDao
import com.picmorrow.feature.phototasks.data.local.PhotoTaskEntity
import com.picmorrow.feature.phototasks.domain.model.PhotoTaskDraft
import com.picmorrow.feature.phototasks.domain.model.PhotoTask
import com.picmorrow.feature.phototasks.domain.repository.PhotoTaskRepository
import com.picmorrow.feature.phototasks.domain.repository.PhotoTaskListingRepository
import com.picmorrow.feature.phototasks.domain.repository.PhotoTaskStatusRepository
import com.picmorrow.feature.phototasks.domain.repository.PhotoTaskDetailsRepository
import com.picmorrow.feature.phototasks.domain.reminder.TaskReminderScheduler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PhotoTaskRepositoryImpl(
    private val dao: PhotoTaskDao,
    private val reminderScheduler: TaskReminderScheduler,
) :
    PhotoTaskRepository,
    PhotoTaskListingRepository,
    PhotoTaskStatusRepository,
    PhotoTaskDetailsRepository {
    override suspend fun savePhotoTask(draft: PhotoTaskDraft): Long {
        val taskId = dao.insert(
            PhotoTaskEntity(
                photoPath = draft.photoPath,
                category = draft.category,
                title = draft.title,
                notes = draft.notes,
                reminderAtMillis = draft.reminderAtMillis,
                capturedAtMillis = draft.capturedAtMillis,
            ),
        )
        draft.reminderAtMillis?.let { reminderScheduler.schedule(taskId, it) }
        return taskId
    }

    override fun observeActive(): Flow<List<PhotoTask>> =
        dao.observeActive().map { tasks -> tasks.map { it.toDomain() } }

    override fun observeCompleted(): Flow<List<PhotoTask>> =
        dao.observeCompleted().map { tasks -> tasks.map { it.toDomain() } }

    override suspend fun complete(id: Long, completedAtMillis: Long) {
        if (dao.complete(id, completedAtMillis) > 0) reminderScheduler.cancel(id)
    }

    override suspend fun complete(id: Long, completedAtMillis: Long, isSavedToGallery: Boolean) {
        if (dao.complete(id, completedAtMillis, isSavedToGallery) > 0) reminderScheduler.cancel(id)
    }

    override suspend fun delete(id: Long) {
        if (dao.delete(id) > 0) reminderScheduler.cancel(id)
    }

    override suspend fun hasPhotoTasks(): Boolean = dao.hasTasks()

    override suspend fun findById(id: Long): PhotoTask? = dao.findById(id)?.toDomain()

    private fun PhotoTaskEntity.toDomain() = PhotoTask(
        id = id,
        photoPath = photoPath,
        category = category,
        title = title,
        notes = notes,
        reminderAtMillis = reminderAtMillis,
        completedAtMillis = completedAtMillis,
        capturedAtMillis = capturedAtMillis,
        isSavedToGallery = isSavedToGallery,
    )
}
