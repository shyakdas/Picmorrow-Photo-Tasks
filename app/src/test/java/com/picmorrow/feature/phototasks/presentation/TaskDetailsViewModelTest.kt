package com.picmorrow.feature.phototasks.presentation

import com.picmorrow.feature.phototasks.domain.model.PhotoTask
import com.picmorrow.feature.phototasks.domain.repository.PhotoTaskDetailsRepository
import com.picmorrow.feature.phototasks.presentation.model.TaskDetailsUiState
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
        val viewModel = TaskDetailsViewModel(7, repository)

        assertEquals(TaskDetailsUiState.Loading, viewModel.uiState.value)
        advanceUntilIdle()

        assertEquals(task(), (viewModel.uiState.value as TaskDetailsUiState.Content).task)
        assertEquals(7L, repository.requestedId)
    }

    @Test
    fun missingTaskShowsNotFound() = runTest(dispatcher) {
        val viewModel = TaskDetailsViewModel(7, FakeDetailsRepository(null))

        advanceUntilIdle()

        assertEquals(TaskDetailsUiState.NotFound, viewModel.uiState.value)
    }

    @Test
    fun completingActiveTaskUpdatesContent() = runTest(dispatcher) {
        val repository = FakeDetailsRepository(task())
        val viewModel = TaskDetailsViewModel(7, repository, currentTimeMillis = { 9_000L })
        advanceUntilIdle()

        viewModel.complete()
        assertTrue((viewModel.uiState.value as TaskDetailsUiState.Content).isCompleting)
        advanceUntilIdle()

        val content = viewModel.uiState.value as TaskDetailsUiState.Content
        assertEquals(9_000L, content.task.completedAtMillis)
        assertFalse(content.isCompleting)
        assertEquals(7L to 9_000L, repository.completedCall)
    }

    @Test
    fun completeFailureRestoresActiveContent() = runTest(dispatcher) {
        val repository = FakeDetailsRepository(task(), failCompletion = true)
        val viewModel = TaskDetailsViewModel(7, repository)
        advanceUntilIdle()

        viewModel.complete()
        advanceUntilIdle()

        val content = viewModel.uiState.value as TaskDetailsUiState.Content
        assertEquals(null, content.task.completedAtMillis)
        assertFalse(content.isCompleting)
    }

    @Test
    fun completedTaskCannotBeCompletedAgain() = runTest(dispatcher) {
        val repository = FakeDetailsRepository(task().copy(completedAtMillis = 8_000L))
        val viewModel = TaskDetailsViewModel(7, repository)
        advanceUntilIdle()

        viewModel.complete()
        advanceUntilIdle()

        assertEquals(null, repository.completedCall)
    }

    private fun task() = PhotoTask(7, "/photo.jpg", "Parking", "Find car", "Near lift", null, null, 1_000L)

    private class FakeDetailsRepository(
        private val task: PhotoTask?,
        private val failCompletion: Boolean = false,
    ) : PhotoTaskDetailsRepository {
        var requestedId: Long? = null
        var completedCall: Pair<Long, Long>? = null

        override suspend fun findById(id: Long): PhotoTask? {
            requestedId = id
            return task
        }

        override suspend fun complete(id: Long, completedAtMillis: Long) {
            if (failCompletion) error("Database unavailable")
            completedCall = id to completedAtMillis
        }
    }
}
