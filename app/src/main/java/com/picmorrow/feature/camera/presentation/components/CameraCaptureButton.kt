package com.picmorrow.feature.camera.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.picmorrow.R
import com.picmorrow.ui.theme.PicmorrowTheme

@Composable
internal fun CameraCaptureButton(
    onCaptureClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val contentDescription = stringResource(R.string.camera_capture_content_description)

    Surface(
        onClick = onCaptureClick,
        modifier = modifier
            .size(CAPTURE_BUTTON_SIZE)
            .semantics {
                this.contentDescription = contentDescription
            },
        shape = CircleShape,
        color = Color.Transparent,
        border = BorderStroke(CAPTURE_RING_WIDTH, Color.White),
    ) {
        Box(contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .size(CAPTURE_BUTTON_INNER_SIZE)
                    .clip(CircleShape)
                    .background(Color.White),
            )
        }
    }
}

@Preview(
    name = "Camera capture button",
    showBackground = true,
    backgroundColor = 0xFF101010,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun CameraCaptureButtonPreview() {
    PicmorrowTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .background(CameraBackground)
                .padding(24.dp),
        ) {
            CameraCaptureButton(onCaptureClick = {})
        }
    }
}

private val CAPTURE_BUTTON_SIZE = 78.dp
private val CAPTURE_BUTTON_INNER_SIZE = 58.dp
private val CAPTURE_RING_WIDTH = 4.dp
