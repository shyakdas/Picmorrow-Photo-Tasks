package com.picmorrow.feature.taskdetails.presentation

import com.picmorrow.feature.phototasks.domain.model.PhotoTask
import com.picmorrow.feature.phototasks.domain.repository.PhotoTaskDetailsRepository
import com.picmorrow.feature.taskdetails.domain.TaskPhotoStorage
import com.picmorrow.feature.taskdetails.presentation.model.TaskDetailsUiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TaskDetailsViewModelTest {
    private val dispatcher = StandardTestDispatcher()

    @Before fun setUp() = Dispatchers.setMain(dispatcher)

    @After fun tearDown() = Dispatchers.resetMain()

    @Test
    fun loadsTaskById() = runTest(dispatcher) {
        val repository = FakeDetailsRepository(task())
        val viewModel = viewModel(repository)

        assertEquals(TaskDetailsUiState.Loading, viewModel.uiState.value)
        advanceUntilIdle()

        assertEquals(task(), (viewModel.uiState.value as TaskDetailsUiState.Content).task)
        assertEquals(7L, repository.requestedId)
    }

    @Test
    fun missingTaskShowsNotFound() = runTest(dispatcher) {
        val viewModel = viewModel(FakeDetailsRepository(null))

        advanceUntilIdle()

        assertEquals(TaskDetailsUiState.NotFound, viewModel.uiState.value)
    }

    @Test
    fun actionsWhileTaskIsLoadingAreIgnored() = runTest(dispatcher) {
        val repository = FakeDetailsRepository(task())
        val storage = FakePhotoStorage()
        val viewModel = viewModel(repository, storage)

        viewModel.completeForReview()
        viewModel.deletePhotoAndTask()

        assertNull(repository.completedCall)
        assertNull(repository.deletedId)
        assertNull(storage.savedPath)
        assertNull(storage.deletedPath)
    }

    @Test
    fun reviewLaterCompletesWithoutSavingToGallery() = runTest(dispatcher) {
        val repository = FakeDetailsRepository(task())
        val storage = FakePhotoStorage()
        val viewModel = viewModel(repository, storage)
        advanceUntilIdle()

        viewModel.completeForReview()
        assertTrue((viewModel.uiState.value as TaskDetailsUiState.Content).isCompleting)
        advanceUntilIdle()

        val completed = (viewModel.uiState.value as TaskDetailsUiState.Content).task
        assertEquals(9_000L, completed.completedAtMillis)
        assertFalse(completed.isSavedToGallery)
        assertEquals(Triple(7L, 9_000L, false), repository.completedCall)
        assertNull(storage.savedPath)
    }

    @Test
    fun saveToGalleryExportsPhotoAndPersistsThatChoice() = runTest(dispatcher) {
        val repository = FakeDetailsRepository(task())
        val storage = FakePhotoStorage()
        val viewModel = viewModel(repository, storage)
        advanceUntilIdle()

        viewModel.saveToGalleryAndComplete()
        advanceUntilIdle()

        val completed = (viewModel.uiState.value as TaskDetailsUiState.Content).task
        assertEquals("/photo.jpg", storage.savedPath)
        assertTrue(completed.isSavedToGallery)
        assertEquals(Triple(7L, 9_000L, true), repository.completedCall)
    }

    @Test
    fun galleryFailureKeepsTaskActive() = runTest(dispatcher) {
        val repository = FakeDetailsRepository(task())
        val storage = FakePhotoStorage(failSave = true)
        val viewModel = viewModel(repository, storage)
        advanceUntilIdle()

        viewModel.saveToGalleryAndComplete()
        advanceUntilIdle()

        val content = viewModel.uiState.value as TaskDetailsUiState.Content
        assertNull(content.task.completedAtMillis)
        assertFalse(content.isCompleting)
        assertNull(repository.completedCall)
    }

    @Test
    fun completionRetryReusesGalleryExportAfterDatabaseFailure() = runTest(dispatcher) {
        val repository = FakeDetailsRepository(task(), failCompletion = true)
        val storage = FakePhotoStorage()
        val viewModel = viewModel(repository, storage)
        advanceUntilIdle()

        viewModel.saveToGalleryAndComplete()
        advanceUntilIdle()

        assertEquals(1, storage.galleryCopies)
        assertNull((viewModel.uiState.value as TaskDetailsUiState.Content).task.completedAtMillis)

        repository.failCompletion = false
        viewModel.saveToGalleryAndComplete()
        advanceUntilIdle()

        assertEquals(2, storage.saveCalls)
        assertEquals(1, storage.galleryCopies)
        assertTrue((viewModel.uiState.value as TaskDetailsUiState.Content).task.isSavedToGallery)
    }

    @Test
    fun deleteRemovesTaskThenItsPrivatePhoto() = runTest(dispatcher) {
        val repository = FakeDetailsRepository(task())
        val storage = FakePhotoStorage()
        val viewModel = viewModel(repository, storage)
        advanceUntilIdle()

        viewModel.deletePhotoAndTask()
        advanceUntilIdle()

        assertEquals(7L, repository.deletedId)
        assertEquals("/photo.jpg", storage.deletedPath)
        assertEquals(TaskDetailsUiState.Deleted, viewModel.uiState.value)
    }

    @Test
    fun deletingGallerySavedCompletedTaskKeepsGalleryExportUntouched() = runTest(dispatcher) {
        val completedTask = task().copy(completedAtMillis = 8_000L, isSavedToGallery = true)
        val repository = FakeDetailsRepository(completedTask)
        val storage = FakePhotoStorage()
        val viewModel = viewModel(repository, storage)
        advanceUntilIdle()

        viewModel.deletePhotoAndTask()
        advanceUntilIdle()

        assertEquals(7L, repository.deletedId)
        assertEquals("/photo.jpg", storage.deletedPath)
        assertNull(storage.savedPath)
        assertEquals(TaskDetailsUiState.Deleted, viewModel.uiState.value)
    }

    @Test
    fun localPhotoDeleteFailureDoesNotRestoreDeletedTask() = runTest(dispatcher) {
        val repository = FakeDetailsRepository(task())
        val storage = FakePhotoStorage(deleteError = IllegalStateException("File unavailable"))
        val viewModel = viewModel(repository, storage)
        advanceUntilIdle()

        viewModel.deletePhotoAndTask()
        advanceUntilIdle()

        assertEquals(7L, repository.deletedId)
        assertEquals(TaskDetailsUiState.Deleted, viewModel.uiState.value)
    }

    @Test
    fun repeatedDeleteWhileDeletionIsRunningIsIgnored() = runTest(dispatcher) {
        val deleteGate = CompletableDeferred<Unit>()
        val repository = FakeDetailsRepository(task(), deleteGate = deleteGate)
        val viewModel = viewModel(repository)
        advanceUntilIdle()

        viewModel.deletePhotoAndTask()
        dispatcher.scheduler.runCurrent()
        viewModel.deletePhotoAndTask()
        assertEquals(1, repository.deleteCalls)

        deleteGate.complete(Unit)
        advanceUntilIdle()
        assertEquals(1, repository.deleteCalls)
    }

    @Test
    fun repositoryDeleteFailureRestoresTaskContent() = runTest(dispatcher) {
        val repository = FakeDetailsRepository(task(), deleteError = IllegalStateException("Database unavailable"))
        val viewModel = viewModel(repository)
        advanceUntilIdle()

        viewModel.deletePhotoAndTask()
        advanceUntilIdle()

        assertEquals(task(), (viewModel.uiState.value as TaskDetailsUiState.Content).task)
        assertFalse((viewModel.uiState.value as TaskDetailsUiState.Content).isCompleting)
    }

    @Test
    fun cancellationDuringLocalPhotoDeleteIsPropagated() = runTest(dispatcher) {
        val repository = FakeDetailsRepository(task())
        val storage = FakePhotoStorage(deleteError = CancellationException("Cancelled"))
        val viewModel = viewModel(repository, storage)
        advanceUntilIdle()

        viewModel.deletePhotoAndTask()
        advanceUntilIdle()

        assertTrue((viewModel.uiState.value as TaskDetailsUiState.Content).isCompleting)
    }

    @Test
    fun completionFailureRestoresActiveContent() = runTest(dispatcher) {
        val repository = FakeDetailsRepository(task(), failCompletion = true)
        val viewModel = viewModel(repository)
        advanceUntilIdle()

        viewModel.completeForReview()
        advanceUntilIdle()

        val content = viewModel.uiState.value as TaskDetailsUiState.Content
        assertNull(content.task.completedAtMillis)
        assertFalse(content.isCompleting)
    }

    @Test
    fun completedTaskCannotBeCompletedAgain() = runTest(dispatcher) {
        val repository = FakeDetailsRepository(task().copy(completedAtMillis = 8_000L))
        val viewModel = viewModel(repository)
        advanceUntilIdle()

        viewModel.completeForReview()
        advanceUntilIdle()

        assertNull(repository.completedCall)
    }

    @Test
    fun repeatedCompletionWhileSavingIsIgnored() = runTest(dispatcher) {
        val completionGate = CompletableDeferred<Unit>()
        val repository = FakeDetailsRepository(task(), completionGate = completionGate)
        val viewModel = viewModel(repository)
        advanceUntilIdle()

        viewModel.completeForReview()
        dispatcher.scheduler.runCurrent()
        viewModel.completeForReview()
        assertEquals(1, repository.completionCalls)

        completionGate.complete(Unit)
        advanceUntilIdle()
        assertEquals(1, repository.completionCalls)
    }

    @Test
    fun cancellationDuringCompletionIsPropagated() = runTest(dispatcher) {
        val repository = FakeDetailsRepository(task(), completionError = CancellationException("Cancelled"))
        val viewModel = viewModel(repository)
        advanceUntilIdle()

        viewModel.completeForReview()
        advanceUntilIdle()

        assertTrue((viewModel.uiState.value as TaskDetailsUiState.Content).isCompleting)
    }

    private fun viewModel(
        repository: FakeDetailsRepository,
        storage: FakePhotoStorage = FakePhotoStorage(),
    ) = TaskDetailsViewModel(7, repository, storage, currentTimeMillis = { 9_000L })

    private fun task() = PhotoTask(7, "/photo.jpg", "Parking", "Find car", "Near lift", null, null, 1_000L)

    private class FakeDetailsRepository(
        private val task: PhotoTask?,
        var failCompletion: Boolean = false,
        private val completionError: Throwable? = null,
        private val completionGate: CompletableDeferred<Unit>? = null,
        private val deleteError: Throwable? = null,
        private val deleteGate: CompletableDeferred<Unit>? = null,
    ) : PhotoTaskDetailsRepository {
        var requestedId: Long? = null
        var completedCall: Triple<Long, Long, Boolean>? = null
        var completionCalls = 0
        var deletedId: Long? = null
        var deleteCalls = 0

        override suspend fun findById(id: Long): PhotoTask? {
            requestedId = id
            return task
        }

        override suspend fun complete(id: Long, completedAtMillis: Long, isSavedToGallery: Boolean) {
            completionCalls++
            completionGate?.await()
            completionError?.let { throw it }
            if (failCompletion) error("Database unavailable")
            completedCall = Triple(id, completedAtMillis, isSavedToGallery)
        }

        override suspend fun delete(id: Long) {
            deleteCalls++
            deleteGate?.await()
            deleteError?.let { throw it }
            deletedId = id
        }
    }

    private class FakePhotoStorage(
        private val failSave: Boolean = false,
        private val deleteError: Throwable? = null,
    ) : TaskPhotoStorage {
        var savedPath: String? = null
        var deletedPath: String? = null
        var saveCalls = 0
        var galleryCopies = 0
        private val exportedPaths = mutableSetOf<String>()

        override suspend fun saveToGallery(photoPath: String) {
            saveCalls++
            if (failSave) error("Gallery unavailable")
            savedPath = photoPath
            if (exportedPaths.add(photoPath)) galleryCopies++
        }

        override suspend fun deleteLocalPhoto(photoPath: String) {
            deleteError?.let { throw it }
            deletedPath = photoPath
        }
    }
}
