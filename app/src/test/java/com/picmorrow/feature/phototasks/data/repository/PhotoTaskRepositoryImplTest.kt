package com.picmorrow.feature.phototasks.data.repository

import com.picmorrow.feature.phototasks.data.local.PhotoTaskDao
import com.picmorrow.feature.phototasks.data.local.PhotoTaskEntity
import com.picmorrow.feature.phototasks.domain.model.PhotoTaskDraft
import com.picmorrow.feature.phototasks.domain.reminder.TaskReminderScheduler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PhotoTaskRepositoryImplTest {
    @Test
    fun savePhotoTaskMapsEveryFieldAndReturnsInsertedId() = runBlocking {
        val dao = RecordingDao()
        val scheduler = RecordingReminderScheduler()
        val repository = PhotoTaskRepositoryImpl(dao, scheduler)
        val draft = PhotoTaskDraft("/photos/capture.jpg", "Parking", "Find car", "Level 2", 1234L)

        assertEquals(42L, repository.savePhotoTask(draft))
        assertEquals(
            PhotoTaskEntity(
                photoPath = draft.photoPath,
                category = draft.category,
                title = draft.title,
                notes = draft.notes,
                reminderAtMillis = draft.reminderAtMillis,
                capturedAtMillis = draft.capturedAtMillis,
            ),
            dao.inserted,
        )
        assertEquals(42L to 1234L, scheduler.scheduled)
    }

    @Test
    fun savePhotoTaskAllowsNoReminder() = runBlocking {
        val dao = RecordingDao()
        val draft = PhotoTaskDraft("/photos/capture.jpg", "Buy", "Milk", "", null)

        val scheduler = RecordingReminderScheduler()
        PhotoTaskRepositoryImpl(dao, scheduler).savePhotoTask(draft)

        assertEquals(null, dao.inserted?.reminderAtMillis)
        assertEquals(null, scheduler.scheduled)
    }

    @Test
    fun observeAndCompleteMapsDaoTasks() = runBlocking {
        val dao = RecordingDao()
        val scheduler = RecordingReminderScheduler()
        val repository = PhotoTaskRepositoryImpl(dao, scheduler)
        val task = PhotoTaskEntity(
            id = 42,
            photoPath = "/photos/capture.jpg",
            category = "Parking",
            title = "Find car",
            notes = "Level 2",
            reminderAtMillis = 1234L,
        )
        dao.inserted = task
        dao.active.value = listOf(task)

        val active = repository.observeActive().first().single()
        assertEquals(task.id, active.id)
        assertEquals(task.photoPath, active.photoPath)
        assertEquals(task.category, active.category)
        assertEquals(task.title, active.title)
        assertEquals(task.notes, active.notes)
        assertEquals(task.reminderAtMillis, active.reminderAtMillis)
        assertEquals(task.capturedAtMillis, active.capturedAtMillis)
        assertEquals(null, active.completedAtMillis)

        dao.completed.value = listOf(task.copy(completedAtMillis = 5678L))
        assertEquals(5678L, repository.observeCompleted().first().single().completedAtMillis)
        repository.complete(42, 5678L)
        assertEquals(Triple(42L, 5678L, false), dao.completedCall)
        assertEquals(42L, scheduler.cancelledId)
        assertEquals(active, repository.findById(42))
    }

    @Test
    fun hasPhotoTasksDelegatesToDao() = runBlocking {
        val dao = RecordingDao()
        val repository = PhotoTaskRepositoryImpl(dao, RecordingReminderScheduler())

        assertFalse(repository.hasPhotoTasks())
        dao.insert(
            PhotoTaskEntity(
                photoPath = "/photo.jpg",
                category = "Parking",
                title = "Find car",
                notes = "",
                reminderAtMillis = null,
            ),
        )
        assertTrue(repository.hasPhotoTasks())
    }

    @Test
    fun findByIdReturnsNullWhenTaskDoesNotExist() = runBlocking {
        val repository = PhotoTaskRepositoryImpl(RecordingDao(), RecordingReminderScheduler())

        assertEquals(null, repository.findById(99))
    }

    @Test
    fun detailsActionsPersistGalleryChoiceAndDeleteTask() = runBlocking {
        val dao = RecordingDao()
        val scheduler = RecordingReminderScheduler()
        val repository = PhotoTaskRepositoryImpl(dao, scheduler)

        repository.complete(42, 5678L, isSavedToGallery = true)
        repository.delete(42)

        assertEquals(Triple(42L, 5678L, true), dao.completedCall)
        assertEquals(42L, dao.deletedId)
        assertEquals(listOf(42L, 42L), scheduler.cancelledIds)
    }

    @Test
    fun failedDatabaseMutationDoesNotCancelReminder() = runBlocking {
        val dao = RecordingDao().apply { affectedRows = 0 }
        val scheduler = RecordingReminderScheduler()
        val repository = PhotoTaskRepositoryImpl(dao, scheduler)

        repository.complete(42, 5678L)
        repository.delete(42)

        assertTrue(scheduler.cancelledIds.isEmpty())
    }

    private class RecordingDao : PhotoTaskDao {
        var inserted: PhotoTaskEntity? = null
        val active = MutableStateFlow<List<PhotoTaskEntity>>(emptyList())
        val completed = MutableStateFlow<List<PhotoTaskEntity>>(emptyList())
        var completedCall: Triple<Long, Long, Boolean>? = null
        var deletedId: Long? = null
        var affectedRows = 1

        override suspend fun insert(task: PhotoTaskEntity): Long {
            inserted = task
            return 42L
        }

        override suspend fun findById(id: Long): PhotoTaskEntity? = inserted

        override suspend fun hasTasks(): Boolean = inserted != null

        override fun observeActive(): Flow<List<PhotoTaskEntity>> = active

        override fun observeCompleted(): Flow<List<PhotoTaskEntity>> = completed

        override suspend fun complete(id: Long, completedAtMillis: Long, isSavedToGallery: Boolean): Int {
            completedCall = Triple(id, completedAtMillis, isSavedToGallery)
            return affectedRows
        }

        override suspend fun delete(id: Long): Int {
            deletedId = id
            return affectedRows
        }
    }

    private class RecordingReminderScheduler : TaskReminderScheduler {
        var scheduled: Pair<Long, Long>? = null
        val cancelledIds = mutableListOf<Long>()
        val cancelledId: Long?
            get() = cancelledIds.lastOrNull()

        override fun schedule(taskId: Long, reminderAtMillis: Long) {
            scheduled = taskId to reminderAtMillis
        }

        override fun cancel(taskId: Long) {
            cancelledIds += taskId
        }
    }
}
