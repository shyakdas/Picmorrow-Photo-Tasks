package com.picmorrow.feature.camera.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.picmorrow.R
import com.picmorrow.ui.theme.PicmorrowTheme

@Composable
internal fun CameraIconButton(
    @DrawableRes iconRes: Int,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(CAMERA_ICON_BUTTON_SIZE),
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = contentDescription,
            modifier = Modifier.size(CAMERA_TOP_ICON_SIZE),
            tint = Color.White,
        )
    }
}

@Preview(
    name = "Camera icon button",
    showBackground = true,
    backgroundColor = 0xFF101010,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun CameraIconButtonPreview() {
    PicmorrowTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .background(CameraBackground)
                .padding(16.dp),
        ) {
            CameraIconButton(
                iconRes = R.drawable.ic_camera_flash_on,
                contentDescription = "Flash on",
                onClick = {},
            )
        }
    }
}

private val CAMERA_ICON_BUTTON_SIZE = 40.dp
private val CAMERA_TOP_ICON_SIZE = 24.dp
