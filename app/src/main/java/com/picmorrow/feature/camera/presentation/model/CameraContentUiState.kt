package com.picmorrow.feature.camera.presentation.model

internal data class CameraContentUiState(
    val flashEnabled: Boolean = false,
    val selectedCategory: CameraCategory = CameraCategory.Parking,
)
