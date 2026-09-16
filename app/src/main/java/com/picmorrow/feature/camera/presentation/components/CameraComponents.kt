@file:Suppress("MagicNumber", "UnusedPrivateMember", "UnusedPrivateProperty")

package com.picmorrow.feature.camera.presentation.components

import androidx.camera.core.ImageCapture
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.picmorrow.feature.camera.presentation.model.CameraCategory
import com.picmorrow.feature.camera.presentation.model.CameraContentUiState
import com.picmorrow.feature.camera.presentation.screen.CameraPreview
import com.picmorrow.ui.theme.PicmorrowTheme

@Composable
internal fun CameraContent(
    imageCapture: ImageCapture,
    uiState: CameraContentUiState,
    onCategorySelected: (CameraCategory) -> Unit,
    onCaptureClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(top = CAMERA_CONTENT_TOP_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CameraPreviewFrame(
            imageCapture = imageCapture,
            flashEnabled = uiState.flashEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = PREVIEW_FRAME_HORIZONTAL_PADDING),
        )

        Spacer(modifier = Modifier.weight(1f))

        CameraCategorySelector(
            selectedCategory = uiState.selectedCategory,
            onCategorySelected = onCategorySelected,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.size(CATEGORY_TO_CAPTURE_SPACING))

        CameraCaptureButton(onCaptureClick = onCaptureClick)

        Spacer(modifier = Modifier.size(CAPTURE_TO_HOME_INDICATOR_SPACING))
    }
}

@Composable
private fun CameraPreviewFrame(
    imageCapture: ImageCapture,
    flashEnabled: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .aspectRatio(PREVIEW_FRAME_ASPECT_RATIO)
            .clip(RoundedCornerShape(PREVIEW_FRAME_CORNER_RADIUS))
            .border(
                width = PREVIEW_FRAME_BORDER_WIDTH,
                color = PreviewFrameBorder,
                shape = RoundedCornerShape(PREVIEW_FRAME_CORNER_RADIUS),
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
        ) {
            CameraPreview(
                imageCapture = imageCapture,
                flashEnabled = flashEnabled,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Preview(
    name = "Camera components",
    showBackground = true,
    backgroundColor = 0xFF101010,
    widthDp = 423,
    heightDp = 915,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun CameraComponentsPreview() {
    PicmorrowTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(CameraBackground),
        ) {
            CameraTopBar(
                onCloseClick = {},
                modifier = Modifier.align(Alignment.TopCenter),
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .padding(top = CAMERA_CONTENT_TOP_PADDING),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = PREVIEW_FRAME_HORIZONTAL_PADDING)
                        .aspectRatio(PREVIEW_FRAME_ASPECT_RATIO)
                        .clip(RoundedCornerShape(PREVIEW_FRAME_CORNER_RADIUS))
                        .border(
                            width = PREVIEW_FRAME_BORDER_WIDTH,
                            color = PreviewFrameBorder,
                            shape = RoundedCornerShape(PREVIEW_FRAME_CORNER_RADIUS),
                        )
                        .background(Color(0xFF5D4A36)),
                ) {
                }

                Spacer(modifier = Modifier.weight(1f))

                CameraCategorySelector(
                    selectedCategory = CameraCategory.Remember,
                    onCategorySelected = {},
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.size(CATEGORY_TO_CAPTURE_SPACING))

                CameraCaptureButton(onCaptureClick = {})

                Spacer(modifier = Modifier.size(CAPTURE_TO_HOME_INDICATOR_SPACING))
            }
        }
    }
}

internal val CameraBackground = Color(0xFF101010)

private val PreviewFrameBorder = Color(0xFF454545)
private val CAMERA_CONTENT_TOP_PADDING = 128.dp
private val PREVIEW_FRAME_HORIZONTAL_PADDING = 25.dp
private val PREVIEW_FRAME_CORNER_RADIUS = 14.dp
private val PREVIEW_FRAME_BORDER_WIDTH = 1.dp
private const val PREVIEW_FRAME_ASPECT_RATIO = 0.846f
private val CATEGORY_TO_CAPTURE_SPACING = 32.dp
private val CAPTURE_TO_HOME_INDICATOR_SPACING = 40.dp
