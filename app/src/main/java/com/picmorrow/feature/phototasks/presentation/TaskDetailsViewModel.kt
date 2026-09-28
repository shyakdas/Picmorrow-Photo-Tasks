package com.picmorrow.feature.phototasks.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picmorrow.feature.phototasks.domain.repository.PhotoTaskDetailsRepository
import com.picmorrow.feature.phototasks.presentation.model.TaskDetailsUiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class TaskDetailsViewModel(
    private val taskId: Long,
    private val repository: PhotoTaskDetailsRepository,
    private val currentTimeMillis: () -> Long = System::currentTimeMillis,
) : ViewModel() {
    private val _uiState = MutableStateFlow<TaskDetailsUiState>(TaskDetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = repository.findById(taskId)
                ?.let(TaskDetailsUiState::Content)
                ?: TaskDetailsUiState.NotFound
        }
    }

    fun complete() {
        val content = _uiState.value as? TaskDetailsUiState.Content ?: return
        if (content.task.completedAtMillis != null || content.isCompleting) return

        _uiState.value = content.copy(isCompleting = true)
        viewModelScope.launch {
            val completedAtMillis = currentTimeMillis()
            try {
                repository.complete(taskId, completedAtMillis)
                _uiState.value = content.copy(task = content.task.copy(completedAtMillis = completedAtMillis))
            } catch (exception: CancellationException) {
                throw exception
            } catch (_: Exception) {
                _uiState.value = content
            }
        }
    }
}
