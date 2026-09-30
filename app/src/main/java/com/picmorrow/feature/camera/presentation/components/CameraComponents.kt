@file:Suppress("MagicNumber", "UnusedPrivateMember", "UnusedPrivateProperty")

package com.picmorrow.feature.camera.presentation.components

import androidx.camera.core.ImageCapture
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.picmorrow.feature.phototasks.presentation.common.model.PhotoTaskCategory
import com.picmorrow.feature.camera.presentation.model.CameraContentUiState
import com.picmorrow.feature.camera.presentation.screen.CameraPreview
import com.picmorrow.ui.adaptive.LocalAdaptiveLayoutInfo
import com.picmorrow.ui.theme.PicmorrowTheme

@Composable
internal fun CameraContent(
    imageCapture: ImageCapture,
    uiState: CameraContentUiState,
    onCategorySelected: (PhotoTaskCategory) -> Unit,
    onCaptureClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CameraAdaptiveContent(
        uiState = uiState,
        onCategorySelected = onCategorySelected,
        onCaptureClick = onCaptureClick,
        modifier = modifier,
        preview = { previewModifier ->
            CameraPreviewFrame(
                imageCapture = imageCapture,
                flashEnabled = uiState.flashEnabled,
                modifier = previewModifier,
            )
        },
    )
}

@Composable
internal fun CameraAdaptiveContent(
    uiState: CameraContentUiState,
    onCategorySelected: (PhotoTaskCategory) -> Unit,
    onCaptureClick: () -> Unit,
    preview: @Composable (Modifier) -> Unit,
    modifier: Modifier = Modifier,
) {
    val adaptiveInfo = LocalAdaptiveLayoutInfo.current
    when {
        adaptiveInfo.isTabletop -> CameraTabletopContent(
            uiState = uiState,
            onCategorySelected = onCategorySelected,
            onCaptureClick = onCaptureClick,
            preview = preview,
            modifier = modifier,
        )
        adaptiveInfo.useWideLayout || adaptiveInfo.isShortHeight -> CameraWideContent(
            uiState = uiState,
            onCategorySelected = onCategorySelected,
            onCaptureClick = onCaptureClick,
            preview = preview,
            modifier = modifier,
        )
        else -> CameraPortraitContent(
            uiState = uiState,
            onCategorySelected = onCategorySelected,
            onCaptureClick = onCaptureClick,
            preview = preview,
            modifier = modifier,
        )
    }
}

@Composable
private fun CameraPortraitContent(
    uiState: CameraContentUiState,
    onCategorySelected: (PhotoTaskCategory) -> Unit,
    onCaptureClick: () -> Unit,
    preview: @Composable (Modifier) -> Unit,
    modifier: Modifier,
) {
    Column(
        modifier = modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(top = CAMERA_TOP_BAR_CLEARANCE, bottom = CAMERA_EDGE_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        preview(
            Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = PREVIEW_FRAME_HORIZONTAL_PADDING),
        )
        CameraControls(uiState, onCategorySelected, onCaptureClick)
    }
}

@Composable
private fun CameraWideContent(
    uiState: CameraContentUiState,
    onCategorySelected: (PhotoTaskCategory) -> Unit,
    onCaptureClick: () -> Unit,
    preview: @Composable (Modifier) -> Unit,
    modifier: Modifier,
) {
    Row(
        modifier = modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(
                start = CAMERA_WIDE_EDGE_PADDING,
                top = CAMERA_TOP_BAR_CLEARANCE,
                end = CAMERA_WIDE_EDGE_PADDING,
                bottom = CAMERA_EDGE_PADDING,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        preview(Modifier.weight(1f).fillMaxHeight())
        Spacer(Modifier.width(CAMERA_WIDE_PANE_SPACING))
        CameraControls(
            uiState = uiState,
            onCategorySelected = onCategorySelected,
            onCaptureClick = onCaptureClick,
            modifier = Modifier.fillMaxHeight().widthIn(max = CAMERA_CONTROLS_MAX_WIDTH),
        )
    }
}

@Composable
private fun CameraTabletopContent(
    uiState: CameraContentUiState,
    onCategorySelected: (PhotoTaskCategory) -> Unit,
    onCaptureClick: () -> Unit,
    preview: @Composable (Modifier) -> Unit,
    modifier: Modifier,
) {
    Column(
        modifier = modifier.statusBarsPadding().navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        preview(
            Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(
                    start = CAMERA_WIDE_EDGE_PADDING,
                    top = CAMERA_TOP_BAR_CLEARANCE,
                    end = CAMERA_WIDE_EDGE_PADDING,
                    bottom = CAMERA_FOLD_CLEARANCE,
                ),
        )
        CameraControls(
            uiState = uiState,
            onCategorySelected = onCategorySelected,
            onCaptureClick = onCaptureClick,
            modifier = Modifier.fillMaxWidth().weight(1f),
        )
    }
}

@Composable
private fun CameraControls(
    uiState: CameraContentUiState,
    onCategorySelected: (PhotoTaskCategory) -> Unit,
    onCaptureClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(top = CAMERA_CONTROLS_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CameraCategorySelector(
            selectedCategory = uiState.selectedCategory,
            onCategorySelected = onCategorySelected,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.size(CATEGORY_TO_CAPTURE_SPACING))
        CameraCaptureButton(onCaptureClick = onCaptureClick)
        Spacer(modifier = Modifier.size(CAPTURE_BOTTOM_SPACING))
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
                    .padding(top = CAMERA_TOP_BAR_CLEARANCE, bottom = CAMERA_EDGE_PADDING),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = PREVIEW_FRAME_HORIZONTAL_PADDING)
                        .clip(RoundedCornerShape(PREVIEW_FRAME_CORNER_RADIUS))
                        .border(
                            width = PREVIEW_FRAME_BORDER_WIDTH,
                            color = PreviewFrameBorder,
                            shape = RoundedCornerShape(PREVIEW_FRAME_CORNER_RADIUS),
                        )
                        .background(Color(0xFF5D4A36)),
                ) {
                }

                CameraCategorySelector(
                    selectedCategory = PhotoTaskCategory.Remember,
                    onCategorySelected = {},
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.size(CATEGORY_TO_CAPTURE_SPACING))

                CameraCaptureButton(onCaptureClick = {})

                Spacer(modifier = Modifier.size(CAPTURE_BOTTOM_SPACING))
            }
        }
    }
}

internal val CameraBackground = Color(0xFF101010)

private val PreviewFrameBorder = Color(0xFF454545)
private val CAMERA_TOP_BAR_CLEARANCE = 76.dp
private val CAMERA_EDGE_PADDING = 16.dp
private val CAMERA_WIDE_EDGE_PADDING = 24.dp
private val CAMERA_WIDE_PANE_SPACING = 24.dp
private val CAMERA_CONTROLS_MAX_WIDTH = 360.dp
private val CAMERA_CONTROLS_PADDING = 16.dp
private val CAMERA_FOLD_CLEARANCE = 12.dp
private val PREVIEW_FRAME_HORIZONTAL_PADDING = 25.dp
private val PREVIEW_FRAME_CORNER_RADIUS = 14.dp
private val PREVIEW_FRAME_BORDER_WIDTH = 1.dp
private val CATEGORY_TO_CAPTURE_SPACING = 24.dp
private val CAPTURE_BOTTOM_SPACING = 16.dp
