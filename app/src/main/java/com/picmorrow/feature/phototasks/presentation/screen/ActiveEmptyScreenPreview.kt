package com.picmorrow.feature.phototasks.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.picmorrow.ui.theme.PicmorrowTheme

@Preview(
    name = "Active Empty - Light",
    showBackground = true,
    showSystemUi = true,
    widthDp = PHONE_PREVIEW_WIDTH,
    heightDp = PHONE_PREVIEW_HEIGHT,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun ActiveEmptyScreenLightPreview() {
    PicmorrowTheme(darkTheme = false) {
        ActiveEmptyScreen(
            darkTheme = false,
            onTakePhotoClick = {},
            onSettingsClick = {},
        )
    }
}

@Preview(
    name = "Active Empty - Dark",
    showBackground = true,
    showSystemUi = true,
    widthDp = PHONE_PREVIEW_WIDTH,
    heightDp = PHONE_PREVIEW_HEIGHT,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun ActiveEmptyScreenDarkPreview() {
    PicmorrowTheme(darkTheme = true) {
        ActiveEmptyScreen(
            darkTheme = true,
            onTakePhotoClick = {},
            onSettingsClick = {},
        )
    }
}

private const val PHONE_PREVIEW_WIDTH = 411
private const val PHONE_PREVIEW_HEIGHT = 891
