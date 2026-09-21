package com.picmorrow.feature.camera.presentation

import com.picmorrow.R
import com.picmorrow.feature.phototasks.presentation.common.model.PhotoTaskCategory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CameraViewModelTest {
    private val viewModel = CameraViewModel()

    @Test
    fun uiState_defaultsToParkingCategoryAndFlashOff() {
        val uiState = viewModel.uiState.value

        assertFalse(uiState.contentUiState.flashEnabled)
        assertEquals(PhotoTaskCategory.Parking, uiState.contentUiState.selectedCategory)
        assertEquals(null, uiState.captureStatusRes)
    }

    @Test
    fun toggleFlash_togglesFlashState() {
        viewModel.toggleFlash()

        assertTrue(viewModel.uiState.value.contentUiState.flashEnabled)

        viewModel.toggleFlash()

        assertFalse(viewModel.uiState.value.contentUiState.flashEnabled)
    }

    @Test
    fun selectCategory_updatesSelectedCategory() {
        viewModel.selectCategory(PhotoTaskCategory.Remember)

        assertEquals(PhotoTaskCategory.Remember, viewModel.uiState.value.contentUiState.selectedCategory)
    }

    @Test
    fun captureCallbacks_updateCaptureStatus() {
        viewModel.onPhotoCaptured()

        assertEquals(R.string.camera_photo_saved, viewModel.uiState.value.captureStatusRes)

        viewModel.onPhotoCaptureFailed()

        assertEquals(R.string.camera_photo_failed, viewModel.uiState.value.captureStatusRes)
    }
}
