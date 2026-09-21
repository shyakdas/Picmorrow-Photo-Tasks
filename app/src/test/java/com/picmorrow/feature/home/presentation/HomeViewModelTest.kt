package com.picmorrow.feature.home.presentation

import com.picmorrow.feature.phototasks.domain.model.PhotoTask
import com.picmorrow.feature.phototasks.domain.repository.PhotoTaskListingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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
class HomeViewModelTest {
    private val dispatcher = StandardTestDispatcher()

    @Before fun setUp() = Dispatchers.setMain(dispatcher)

    @After fun tearDown() = Dispatchers.resetMain()

    @Test
    fun emptyRepositoryShowsEmptyContentAfterLoading() = runTest(dispatcher) {
        val viewModel = HomeViewModel(FakeListingRepository())
        assertTrue(viewModel.contentState.value.isLoading)
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.contentState.collect {} }

        advanceUntilIdle()

        assertFalse(viewModel.contentState.value.isLoading)
        assertEquals(emptyList<PhotoTask>(), viewModel.contentState.value.activeTasks)
        assertEquals(emptyList<PhotoTask>(), viewModel.contentState.value.completedTasks)
    }

    @Test
    fun observesTasksAndCompletesSelectedTask() = runTest(dispatcher) {
        val repository = FakeListingRepository()
        val viewModel = HomeViewModel(repository)
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.contentState.collect {} }
        val task = PhotoTask(7, "/photo.jpg", "Parking", "Find car", "B2", null, null)

        repository.active.value = listOf(task)
        advanceUntilIdle()
        assertEquals(listOf(task), viewModel.contentState.value.activeTasks)

        viewModel.complete(task.id)
        advanceUntilIdle()
        assertEquals(task.id, repository.completedId)
        assertTrue(repository.completedAtMillis > 0)

        repository.active.value = emptyList()
        repository.completed.value = listOf(task.copy(completedAtMillis = repository.completedAtMillis))
        advanceUntilIdle()
        assertEquals(emptyList<PhotoTask>(), viewModel.contentState.value.activeTasks)
        assertEquals(task.id, viewModel.contentState.value.completedTasks.single().id)
    }

    private class FakeListingRepository : PhotoTaskListingRepository {
        val active = MutableStateFlow<List<PhotoTask>>(emptyList())
        val completed = MutableStateFlow<List<PhotoTask>>(emptyList())
        var completedId: Long? = null
        var completedAtMillis: Long = 0

        override fun observeActive(): Flow<List<PhotoTask>> = active

        override fun observeCompleted(): Flow<List<PhotoTask>> = completed

        override suspend fun complete(id: Long, completedAtMillis: Long) {
            completedId = id
            this.completedAtMillis = completedAtMillis
        }
    }
}
