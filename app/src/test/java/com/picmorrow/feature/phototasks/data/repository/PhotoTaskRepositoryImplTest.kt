package com.picmorrow.feature.phototasks.data.repository

import com.picmorrow.feature.phototasks.data.local.PhotoTaskDao
import com.picmorrow.feature.phototasks.data.local.PhotoTaskEntity
import com.picmorrow.feature.phototasks.domain.model.PhotoTaskDraft
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

    private class RecordingDao : PhotoTaskDao {
        var inserted: PhotoTaskEntity? = null

        override suspend fun insert(task: PhotoTaskEntity): Long {
            inserted = task
            return 42L
        }

        override suspend fun findById(id: Long): PhotoTaskEntity? = inserted
    }
}
