package com.picmorrow.feature.camera.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.picmorrow.R
import com.picmorrow.ui.theme.PicmorrowTheme

@Composable
internal fun CameraTopBar(
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit,
    flashEnabled: Boolean = false,
    onFlashClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(
                horizontal = CAMERA_EDGE_PADDING,
                vertical = CAMERA_TOP_BAR_VERTICAL_PADDING,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CameraIconButton(
            onClick = onCloseClick,
            iconRes = R.drawable.ic_camera_close,
            contentDescription = stringResource(R.string.camera_close_content_description),
        )

        CameraIconButton(
            onClick = onFlashClick,
            iconRes = if (flashEnabled) R.drawable.ic_camera_flash_on else R.drawable.ic_camera_flash_off,
            contentDescription =
                stringResource(
                    if (flashEnabled) {
                        R.string.camera_flash_on_content_description
                    } else {
                        R.string.camera_flash_off_content_description
                    },
                ),
        )
    }
}

@Preview(
    name = "Camera top bar",
    showBackground = true,
    backgroundColor = 0xFF101010,
    widthDp = 423,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun CameraTopBarPreview() {
    PicmorrowTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(CameraBackground),
        ) {
            CameraTopBar(
                flashEnabled = true,
                onCloseClick = {},
                onFlashClick = {},
            )
        }
    }
}

private val CAMERA_EDGE_PADDING = 15.dp
private val CAMERA_TOP_BAR_VERTICAL_PADDING = 12.dp
