package com.picmorrow.feature.onboarding

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import androidx.compose.runtime.Composable
import org.junit.Rule
import org.junit.Test
import com.picmorrow.feature.onboarding.presentation.screen.WelcomeScreen
import com.picmorrow.ui.theme.PicmorrowTheme

class WelcomeScreenScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi(deviceConfig = DeviceConfig.PIXEL_5)

    @Test
    fun welcome_light() {
        paparazzi.snapshot {
            WelcomeSnapshotContent(darkTheme = false)
        }
    }

    @Test
    fun welcome_dark() {
        paparazzi.snapshot {
            WelcomeSnapshotContent(darkTheme = true)
        }
    }
}

@Composable
private fun WelcomeSnapshotContent(darkTheme: Boolean) {
    PicmorrowTheme(darkTheme = darkTheme) {
        WelcomeScreen(
            darkTheme = darkTheme,
            onTakeFirstPhotoClick = {},
            onExploreFirstClick = {},
        )
    }
}
