package com.picmorrow.feature.phototasks

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.picmorrow.core.data.local.AppDatabase
import com.picmorrow.feature.phototasks.data.repository.PhotoTaskRepositoryImpl
import com.picmorrow.feature.phototasks.domain.model.PhotoTaskDraft
import com.picmorrow.feature.phototasks.domain.usecase.SavePhotoTaskUseCase
import com.picmorrow.feature.phototasks.presentation.NewPhotoTaskViewModel
import com.picmorrow.R
import java.io.File
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PhotoTaskSaveIntegrationTest {
    @Test
    fun saveFromViewModelInsertsPhotoTask() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val photo = File(context.filesDir, "room-save-test.jpg").apply { writeBytes(byteArrayOf(1)) }
        val database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()

        try {
            val viewModel = NewPhotoTaskViewModel(
                SavePhotoTaskUseCase(PhotoTaskRepositoryImpl(database.photoTaskDao())),
            )
            val draft = PhotoTaskDraft(photo.absolutePath, "Parking", "  Find car  ", "  Level 2  ", 1234L)

            InstrumentationRegistry.getInstrumentation().runOnMainSync { viewModel.save(draft) }
            withTimeout(5_000) { viewModel.uiState.first { it.isSaved } }

            val saved = database.photoTaskDao().findById(1)
            assertEquals(photo.absolutePath, saved?.photoPath)
            assertEquals("Parking", saved?.category)
            assertEquals("Find car", saved?.title)
            assertEquals("Level 2", saved?.notes)
            assertEquals(1234L, saved?.reminderAtMillis)
        } finally {
            database.close()
            photo.delete()
        }
    }

    @Test
    fun blankTitleDoesNotInsertPhotoTask() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()

        try {
            val viewModel = NewPhotoTaskViewModel(
                SavePhotoTaskUseCase(PhotoTaskRepositoryImpl(database.photoTaskDao())),
            )
            val draft = PhotoTaskDraft("/missing/photo.jpg", "Parking", "   ", "", null)

            InstrumentationRegistry.getInstrumentation().runOnMainSync { viewModel.save(draft) }

            assertEquals(R.string.new_photo_task_title_required, viewModel.uiState.value.errorMessageRes)
            assertNull(database.photoTaskDao().findById(1))
        } finally {
            database.close()
        }
    }

    @Test
    fun missingPhotoLeavesFormReadyToRetry() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()

        try {
            val viewModel = NewPhotoTaskViewModel(
                SavePhotoTaskUseCase(PhotoTaskRepositoryImpl(database.photoTaskDao())),
            )
            val draft = PhotoTaskDraft("/missing/photo.jpg", "Parking", "Find car", "", null)

            InstrumentationRegistry.getInstrumentation().runOnMainSync { viewModel.save(draft) }
            withTimeout(5_000) { viewModel.uiState.first { it.errorMessageRes != null } }

            assertEquals(R.string.new_photo_task_save_failed, viewModel.uiState.value.errorMessageRes)
            assertEquals(false, viewModel.uiState.value.isSaving)
            assertNull(database.photoTaskDao().findById(1))
        } finally {
            database.close()
        }
    }
}
