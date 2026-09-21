package com.picmorrow.feature.phototasks.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.picmorrow.R
import com.picmorrow.feature.phototasks.domain.model.PhotoTaskDraft
import com.picmorrow.feature.phototasks.domain.usecase.SavePhotoTaskUseCase
import com.picmorrow.feature.phototasks.presentation.model.PhotoTaskSaveUiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class NewPhotoTaskViewModel(
    private val savePhotoTask: SavePhotoTaskUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(PhotoTaskSaveUiState())
    val uiState = _uiState.asStateFlow()

    @Suppress("TooGenericExceptionCaught")
    fun save(draft: PhotoTaskDraft) {
        if (_uiState.value.isSaving || _uiState.value.isSaved) return

        if (draft.title.isBlank()) {
            _uiState.update { it.copy(errorMessageRes = R.string.new_photo_task_title_required) }
            return
        }

        _uiState.update { it.copy(isSaving = true, errorMessageRes = null) }
        viewModelScope.launch {
            try {
                savePhotoTask(draft)
                _uiState.update { it.copy(isSaving = false, isSaved = true) }
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {
                Log.e(TAG, "Failed to save photo task", exception)
                _uiState.update { it.copy(isSaving = false, errorMessageRes = R.string.new_photo_task_save_failed) }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessageRes = null) }
    }

    class Factory(private val savePhotoTask: SavePhotoTaskUseCase) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            NewPhotoTaskViewModel(savePhotoTask) as T
    }

    private companion object {
        const val TAG = "NewPhotoTaskViewModel"
    }
}
