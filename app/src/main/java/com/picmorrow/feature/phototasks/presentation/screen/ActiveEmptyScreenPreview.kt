package com.picmorrow.feature.phototasks.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.picmorrow.ui.theme.PicmorrowTheme

@Preview(showBackground = true)
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

@Preview(showBackground = true)
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
