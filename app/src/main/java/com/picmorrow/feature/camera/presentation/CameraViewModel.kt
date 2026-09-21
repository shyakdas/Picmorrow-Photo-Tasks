package com.picmorrow.feature.camera.presentation

import androidx.lifecycle.ViewModel
import com.picmorrow.R
import com.picmorrow.feature.phototasks.presentation.common.model.PhotoTaskCategory
import com.picmorrow.feature.camera.presentation.model.CameraUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class CameraViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CameraUiState())
    val uiState: StateFlow<CameraUiState> = _uiState.asStateFlow()

    fun toggleFlash() {
        _uiState.update { state ->
            state.copy(
                contentUiState = state.contentUiState.copy(
                    flashEnabled = !state.contentUiState.flashEnabled,
                ),
            )
        }
    }

    fun selectCategory(category: PhotoTaskCategory) {
        _uiState.update { state ->
            state.copy(
                contentUiState = state.contentUiState.copy(
                    selectedCategory = category,
                ),
            )
        }
    }

    fun onPhotoCaptured() {
        _uiState.update { state ->
            state.copy(captureStatusRes = R.string.camera_photo_saved)
        }
    }

    fun onPhotoCaptureFailed() {
        _uiState.update { state ->
            state.copy(captureStatusRes = R.string.camera_photo_failed)
        }
    }
}
