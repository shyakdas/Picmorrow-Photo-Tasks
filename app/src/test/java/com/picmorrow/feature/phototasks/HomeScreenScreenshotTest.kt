package com.picmorrow.feature.phototasks

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import androidx.compose.runtime.Composable
import org.junit.Rule
import org.junit.Test
import com.picmorrow.feature.phototasks.presentation.screen.HomeScreen
import com.picmorrow.ui.theme.PicmorrowTheme

class HomeScreenScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi(deviceConfig = DeviceConfig.PIXEL_5)

    @Test
    fun active_empty_light() {
        paparazzi.snapshot {
            HomeSnapshotContent(darkTheme = false)
        }
    }

    @Test
    fun active_empty_dark() {
        paparazzi.snapshot {
            HomeSnapshotContent(darkTheme = true)
        }
    }
}

@Composable
private fun HomeSnapshotContent(darkTheme: Boolean) {
    PicmorrowTheme(darkTheme = darkTheme) {
        HomeScreen(
            darkTheme = darkTheme,
            onTakePhotoClick = {},
            onSettingsClick = {},
        )
    }
}
