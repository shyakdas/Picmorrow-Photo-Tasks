package com.picmorrow.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.picmorrow.feature.phototasks.domain.repository.PhotoTaskListingRepository
import com.picmorrow.feature.home.presentation.model.HomeContentState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class HomeViewModel(private val repository: PhotoTaskListingRepository) : ViewModel() {
    val contentState = combine(repository.observeActive(), repository.observeCompleted()) { active, completed ->
        HomeContentState(activeTasks = active, completedTasks = completed)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(SUBSCRIPTION_TIMEOUT_MILLIS),
        HomeContentState(isLoading = true),
    )

    fun complete(id: Long) {
        viewModelScope.launch { repository.complete(id, System.currentTimeMillis()) }
    }

    class Factory(private val repository: PhotoTaskListingRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = HomeViewModel(repository) as T
    }

    private companion object {
        const val SUBSCRIPTION_TIMEOUT_MILLIS = 5_000L
    }
}
