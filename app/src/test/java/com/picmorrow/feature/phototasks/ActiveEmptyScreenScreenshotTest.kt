package com.picmorrow.feature.phototasks

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import androidx.compose.runtime.Composable
import org.junit.Rule
import org.junit.Test
import com.picmorrow.feature.phototasks.presentation.screen.ActiveEmptyScreen
import com.picmorrow.ui.theme.PicmorrowTheme

class ActiveEmptyScreenScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi(deviceConfig = DeviceConfig.PIXEL_5)

    @Test
    fun active_empty_light() {
        paparazzi.snapshot {
            ActiveEmptySnapshotContent(darkTheme = false)
        }
    }

    @Test
    fun active_empty_dark() {
        paparazzi.snapshot {
            ActiveEmptySnapshotContent(darkTheme = true)
        }
    }
}

@Composable
private fun ActiveEmptySnapshotContent(darkTheme: Boolean) {
    PicmorrowTheme(darkTheme = darkTheme) {
        ActiveEmptyScreen(
            darkTheme = darkTheme,
            onTakePhotoClick = {},
            onSettingsClick = {},
        )
    }
}
