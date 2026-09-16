package com.picmorrow.feature.camera.presentation.model

internal data class CameraUiState(
    val contentUiState: CameraContentUiState = CameraContentUiState(),
    val captureStatusRes: Int? = null,
)
