package com.picmorrow.feature.camera.presentation.model

import com.picmorrow.feature.phototasks.presentation.common.model.PhotoTaskCategory

internal data class CameraContentUiState(
    val flashEnabled: Boolean = false,
    val selectedCategory: PhotoTaskCategory = PhotoTaskCategory.Parking,
)
