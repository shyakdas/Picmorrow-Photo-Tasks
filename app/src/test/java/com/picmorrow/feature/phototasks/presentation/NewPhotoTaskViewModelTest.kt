package com.picmorrow.feature.phototasks.presentation

import android.util.Log
import com.picmorrow.R
import com.picmorrow.feature.phototasks.domain.model.PhotoTaskDraft
import com.picmorrow.feature.phototasks.domain.repository.PhotoTaskRepository
import com.picmorrow.feature.phototasks.domain.usecase.SavePhotoTaskUseCase
import java.io.File
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.mockito.Mockito

@OptIn(ExperimentalCoroutinesApi::class)
class NewPhotoTaskViewModelTest {
    @get:Rule val temporaryFolder = TemporaryFolder()

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun blankTitleShowsErrorAndDoesNotSave() {
        val repository = RecordingRepository()
        val viewModel = newViewModel(repository)

        viewModel.save(draft().copy(title = "  "))

        assertEquals(R.string.new_photo_task_title_required, viewModel.uiState.value.errorMessageRes)
        assertFalse(viewModel.uiState.value.isSaving)
        assertEquals(null, repository.savedDraft)

        viewModel.clearError()
        assertEquals(null, viewModel.uiState.value.errorMessageRes)
    }

    @Test
    fun saveShowsProgressThenSuccessAndIgnoresRepeatedTaps() = runTest(dispatcher) {
        val repository = RecordingRepository()
        val viewModel = newViewModel(repository)
        val draft = draft()

        viewModel.save(draft)
        viewModel.save(draft)
        assertTrue(viewModel.uiState.value.isSaving)
        runCurrent()
        assertEquals(1, repository.saveCount)

        repository.allowSave.complete(Unit)
        advanceUntilIdle()
        assertEquals(draft, repository.savedDraft)
        assertTrue(viewModel.uiState.value.isSaved)
        assertFalse(viewModel.uiState.value.isSaving)

        viewModel.save(draft)
        advanceUntilIdle()
        assertEquals(1, repository.saveCount)
    }

    @Test
    fun failedSaveShowsErrorAndCanRetry() = runTest(dispatcher) {
        Mockito.mockStatic(Log::class.java).use {
            val repository = RecordingRepository().apply { failure = IllegalStateException("Storage full") }
            val viewModel = newViewModel(repository)
            val draft = draft()

            viewModel.save(draft)
            advanceUntilIdle()
            assertEquals(R.string.new_photo_task_save_failed, viewModel.uiState.value.errorMessageRes)
            assertFalse(viewModel.uiState.value.isSaving)
            assertFalse(viewModel.uiState.value.isSaved)

            repository.failure = null
            repository.allowSave.complete(Unit)
            viewModel.clearError()
            viewModel.save(draft)
            advanceUntilIdle()
            assertTrue(viewModel.uiState.value.isSaved)
            assertEquals(2, repository.saveCount)
        }
    }

    @Test
    fun cancellationIsNotReportedAsSaveFailure() = runTest(dispatcher) {
        val repository = RecordingRepository().apply { failure = CancellationException("Screen closed") }
        val viewModel = newViewModel(repository)

        viewModel.save(draft())
        advanceUntilIdle()

        assertEquals(1, repository.saveCount)
        assertEquals(null, viewModel.uiState.value.errorMessageRes)
        assertFalse(viewModel.uiState.value.isSaved)
    }

    @Test
    fun factoryCreatesViewModel() {
        val useCase = SavePhotoTaskUseCase(RecordingRepository())

        val viewModel = NewPhotoTaskViewModel.Factory(useCase).create(NewPhotoTaskViewModel::class.java)

        assertFalse(viewModel.uiState.value.isSaved)
    }

    private fun newViewModel(repository: PhotoTaskRepository) =
        NewPhotoTaskViewModel(SavePhotoTaskUseCase(repository))

    private fun draft(): PhotoTaskDraft {
        val photo = File(temporaryFolder.root, "capture.jpg").apply { writeBytes(byteArrayOf(1)) }
        return PhotoTaskDraft(photo.absolutePath, "Parking", "Find car", "Level 2", 1234L)
    }

    private class RecordingRepository : PhotoTaskRepository {
        val allowSave = CompletableDeferred<Unit>()
        var savedDraft: PhotoTaskDraft? = null
        var saveCount = 0
        var failure: Exception? = null

        override suspend fun savePhotoTask(draft: PhotoTaskDraft): Long {
            saveCount++
            failure?.let { throw it }
            allowSave.await()
            savedDraft = draft
            return 42L
        }
    }
}
