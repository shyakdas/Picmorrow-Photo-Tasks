package com.picmorrow.feature.phototasks.data.repository

import com.picmorrow.feature.phototasks.data.local.PhotoTaskDao
import com.picmorrow.feature.phototasks.data.local.PhotoTaskEntity
import com.picmorrow.feature.phototasks.domain.model.PhotoTaskDraft
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class PhotoTaskRepositoryImplTest {
    @Test
    fun savePhotoTaskMapsEveryFieldAndReturnsInsertedId() = runBlocking {
        val dao = RecordingDao()
        val repository = PhotoTaskRepositoryImpl(dao)
        val draft = PhotoTaskDraft("/photos/capture.jpg", "Parking", "Find car", "Level 2", 1234L)

        assertEquals(42L, repository.savePhotoTask(draft))
        assertEquals(
            PhotoTaskEntity(
                photoPath = draft.photoPath,
                category = draft.category,
                title = draft.title,
                notes = draft.notes,
                reminderAtMillis = draft.reminderAtMillis,
            ),
            dao.inserted,
        )
    }

    @Test
    fun savePhotoTaskAllowsNoReminder() = runBlocking {
        val dao = RecordingDao()
        val draft = PhotoTaskDraft("/photos/capture.jpg", "Buy", "Milk", "", null)

        PhotoTaskRepositoryImpl(dao).savePhotoTask(draft)

        assertEquals(null, dao.inserted?.reminderAtMillis)
    }

    @Test
    fun observeAndCompleteMapsDaoTasks() = runBlocking {
        val dao = RecordingDao()
        val repository = PhotoTaskRepositoryImpl(dao)
        val task = PhotoTaskEntity(
            id = 42,
            photoPath = "/photos/capture.jpg",
            category = "Parking",
            title = "Find car",
            notes = "Level 2",
            reminderAtMillis = 1234L,
        )
        dao.active.value = listOf(task)

        val active = repository.observeActive().first().single()
        assertEquals(task.id, active.id)
        assertEquals(task.photoPath, active.photoPath)
        assertEquals(task.category, active.category)
        assertEquals(task.title, active.title)
        assertEquals(task.notes, active.notes)
        assertEquals(task.reminderAtMillis, active.reminderAtMillis)
        assertEquals(null, active.completedAtMillis)

        dao.completed.value = listOf(task.copy(completedAtMillis = 5678L))
        assertEquals(5678L, repository.observeCompleted().first().single().completedAtMillis)
        repository.complete(42, 5678L)
        assertEquals(42L to 5678L, dao.completedCall)
    }

    private class RecordingDao : PhotoTaskDao {
        var inserted: PhotoTaskEntity? = null
        val active = MutableStateFlow<List<PhotoTaskEntity>>(emptyList())
        val completed = MutableStateFlow<List<PhotoTaskEntity>>(emptyList())
        var completedCall: Pair<Long, Long>? = null

        override suspend fun insert(task: PhotoTaskEntity): Long {
            inserted = task
            return 42L
        }

        override suspend fun findById(id: Long): PhotoTaskEntity? = inserted

        override fun observeActive(): Flow<List<PhotoTaskEntity>> = active

        override fun observeCompleted(): Flow<List<PhotoTaskEntity>> = completed

        override suspend fun complete(id: Long, completedAtMillis: Long): Int {
            completedCall = id to completedAtMillis
            return 1
        }
    }
}
