package com.picmorrow.feature.phototasks.domain.usecase

import com.picmorrow.feature.phototasks.domain.model.PhotoTaskDraft
import com.picmorrow.feature.phototasks.domain.repository.PhotoTaskRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class SavePhotoTaskUseCaseTest {
    @get:Rule val temporaryFolder = TemporaryFolder()

    @Test
    fun savesPhotoMetadataAndTrimsText() = runBlocking {
        val photo = temporaryFolder.newFile("photo.jpg")
        val repository = FakePhotoTaskRepository()
        val draft = PhotoTaskDraft(photo.absolutePath, "Parking", "  Find car  ", "  Level 2  ", 1234L)

        val id = SavePhotoTaskUseCase(repository)(draft)

        assertEquals(42L, id)
        assertEquals(draft.copy(title = "Find car", notes = "Level 2"), repository.savedDraft)
    }

    @Test
    fun rejectsBlankTitleWithoutWriting() {
        val photo = temporaryFolder.newFile("photo.jpg")
        val repository = FakePhotoTaskRepository()
        val draft = PhotoTaskDraft(photo.absolutePath, "Buy", "   ", "", null)

        assertThrows(IllegalArgumentException::class.java) {
            runBlocking { SavePhotoTaskUseCase(repository)(draft) }
        }
        assertNull(repository.savedDraft)
    }

    @Test
    fun rejectsMissingPhotoWithoutWriting() {
        val repository = FakePhotoTaskRepository()
        val draft = PhotoTaskDraft("/missing/photo.jpg", "Remember", "Remember code", "", null)

        assertThrows(IllegalArgumentException::class.java) {
            runBlocking { SavePhotoTaskUseCase(repository)(draft) }
        }
        assertNull(repository.savedDraft)
    }

    private class FakePhotoTaskRepository : PhotoTaskRepository {
        var savedDraft: PhotoTaskDraft? = null

        override suspend fun savePhotoTask(draft: PhotoTaskDraft): Long {
            savedDraft = draft
            return 42L
        }
    }
}
