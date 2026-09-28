package com.picmorrow

import com.picmorrow.feature.phototasks.domain.repository.PhotoTaskStatusRepository
import com.picmorrow.feature.phototasks.domain.usecase.HasPhotoTasksUseCase
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
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {
    private val dispatcher = StandardTestDispatcher()

    @Before fun setUp() = Dispatchers.setMain(dispatcher)

    @After fun tearDown() = Dispatchers.resetMain()

    @Test
    fun noTasksOpensIntroduction() = runTest(dispatcher) {
        val viewModel = createViewModel(hasTasks = false)

        assertEquals(MainUiState.Loading, viewModel.uiState.value)
        advanceUntilIdle()

        assertEquals(MainUiState.Introduction, viewModel.uiState.value)
    }

    @Test
    fun existingTasksOpenHome() = runTest(dispatcher) {
        val viewModel = createViewModel(hasTasks = true)

        advanceUntilIdle()

        assertEquals(MainUiState.Home, viewModel.uiState.value)
    }

    @Test
    fun databaseFailureFallsBackToIntroduction() = runTest(dispatcher) {
        val repository = PhotoTaskStatusRepository { error("Database unavailable") }
        val viewModel = MainViewModel(HasPhotoTasksUseCase(repository))

        advanceUntilIdle()

        assertEquals(MainUiState.Introduction, viewModel.uiState.value)
    }

    @Test
    fun remainsLoadingWhileDatabaseQueryIsSuspended() = runTest(dispatcher) {
        val queryResult = CompletableDeferred<Boolean>()
        val repository = PhotoTaskStatusRepository {
            queryResult.await()
        }
        val viewModel = MainViewModel(HasPhotoTasksUseCase(repository))

        dispatcher.scheduler.runCurrent()
        assertEquals(MainUiState.Loading, viewModel.uiState.value)
        queryResult.complete(true)
        advanceUntilIdle()

        assertEquals(MainUiState.Home, viewModel.uiState.value)
    }

    @Test
    fun suspendedDatabaseFailureFallsBackToIntroduction() = runTest(dispatcher) {
        val queryResult = CompletableDeferred<Boolean>()
        val repository = PhotoTaskStatusRepository { queryResult.await() }
        val viewModel = MainViewModel(HasPhotoTasksUseCase(repository))

        dispatcher.scheduler.runCurrent()
        queryResult.completeExceptionally(IllegalStateException("Database unavailable"))
        advanceUntilIdle()

        assertEquals(MainUiState.Introduction, viewModel.uiState.value)
    }

    private fun createViewModel(hasTasks: Boolean): MainViewModel {
        val repository = PhotoTaskStatusRepository { hasTasks }
        return MainViewModel(HasPhotoTasksUseCase(repository))
    }
}
