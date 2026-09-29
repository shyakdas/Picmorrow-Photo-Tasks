package com.picmorrow.feature.taskdetails.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picmorrow.feature.phototasks.domain.repository.PhotoTaskDetailsRepository
import com.picmorrow.feature.taskdetails.domain.TaskPhotoStorage
import com.picmorrow.feature.taskdetails.presentation.model.TaskDetailsUiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class TaskDetailsViewModel(
    private val taskId: Long,
    private val repository: PhotoTaskDetailsRepository,
    private val photoStorage: TaskPhotoStorage,
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

    fun saveToGalleryAndComplete() = complete(saveToGallery = true)

    fun completeForReview() = complete(saveToGallery = false)

    fun deletePhotoAndTask() {
        val content = _uiState.value as? TaskDetailsUiState.Content ?: return
        if (content.isCompleting) return

        _uiState.value = content.copy(isCompleting = true)
        viewModelScope.launch {
            try {
                repository.delete(taskId)
                try {
                    photoStorage.deleteLocalPhoto(content.task.photoPath)
                } catch (exception: CancellationException) {
                    throw exception
                } catch (_: Exception) {
                    // The task is already removed; an orphaned private file must not restore it.
                }
                _uiState.value = TaskDetailsUiState.Deleted
            } catch (exception: CancellationException) {
                throw exception
            } catch (_: Exception) {
                _uiState.value = content
            }
        }
    }

    private fun complete(saveToGallery: Boolean) {
        val content = _uiState.value as? TaskDetailsUiState.Content ?: return
        if (content.task.completedAtMillis != null || content.isCompleting) return

        _uiState.value = content.copy(isCompleting = true)
        viewModelScope.launch {
            val completedAtMillis = currentTimeMillis()
            try {
                if (saveToGallery) photoStorage.saveToGallery(content.task.photoPath)
                repository.complete(taskId, completedAtMillis, saveToGallery)
                _uiState.value = content.copy(
                    task = content.task.copy(
                        completedAtMillis = completedAtMillis,
                        isSavedToGallery = saveToGallery,
                    ),
                )
            } catch (exception: CancellationException) {
                throw exception
            } catch (_: Exception) {
                _uiState.value = content
            }
        }
    }
}
