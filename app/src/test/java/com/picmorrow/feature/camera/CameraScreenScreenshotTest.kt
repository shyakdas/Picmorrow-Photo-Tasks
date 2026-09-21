package com.picmorrow.feature.camera

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.picmorrow.feature.camera.presentation.components.CameraBackground
import com.picmorrow.feature.camera.presentation.components.CameraCaptureButton
import com.picmorrow.feature.camera.presentation.components.CameraCategorySelector
import com.picmorrow.feature.camera.presentation.components.CameraTopBar
import com.picmorrow.feature.phototasks.presentation.common.model.PhotoTaskCategory
import com.picmorrow.feature.camera.presentation.screen.CameraPermissionScreen
import com.picmorrow.ui.theme.PicmorrowTheme
import org.junit.Rule
import org.junit.Test

class CameraScreenScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi(deviceConfig = DeviceConfig.PIXEL_5)

    @Test
    fun camera_permission_light() {
        paparazzi.snapshot {
            CameraPermissionSnapshotContent(darkTheme = false)
        }
    }

    @Test
    fun camera_permission_dark() {
        paparazzi.snapshot {
            CameraPermissionSnapshotContent(darkTheme = true)
        }
    }

    @Test
    fun camera_controls_dark() {
        paparazzi.snapshot {
            CameraControlsSnapshotContent()
        }
    }
}

@Composable
private fun CameraPermissionSnapshotContent(darkTheme: Boolean) {
    PicmorrowTheme(darkTheme = darkTheme) {
        CameraPermissionScreen(
            onCloseClick = {},
            onRequestPermissionClick = {},
        )
    }
}

@Composable
private fun CameraControlsSnapshotContent() {
    PicmorrowTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(CameraBackground),
        ) {
            CameraTopBar(
                flashEnabled = true,
                onCloseClick = {},
                onFlashClick = {},
                modifier = Modifier.align(Alignment.TopCenter),
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .padding(top = CONTROLS_TOP_PADDING),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
            ) {
                CameraCategorySelector(
                    selectedCategory = PhotoTaskCategory.Remember,
                    onCategorySelected = {},
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(CATEGORY_TO_CAPTURE_SPACING))

                CameraCaptureButton(onCaptureClick = {})

                Spacer(modifier = Modifier.height(CAPTURE_BOTTOM_SPACING))
            }
        }
    }
}

private val CONTROLS_TOP_PADDING = 128.dp
private val CATEGORY_TO_CAPTURE_SPACING = 32.dp
private val CAPTURE_BOTTOM_SPACING = 44.dp
