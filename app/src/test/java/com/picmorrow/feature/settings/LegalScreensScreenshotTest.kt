package com.picmorrow.feature.settings

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.picmorrow.feature.settings.presentation.screen.AboutScreen
import com.picmorrow.feature.settings.presentation.screen.PrivacyPolicyScreen
import com.picmorrow.ui.theme.PicmorrowTheme
import org.junit.Rule
import org.junit.Test

class LegalScreensScreenshotTest {
    @get:Rule
    val paparazzi = Paparazzi(deviceConfig = DeviceConfig.PIXEL_5)

    @Test
    fun about_light() {
        paparazzi.snapshot {
            PicmorrowTheme(darkTheme = false) {
                AboutScreen(onBackClick = {}, onPrivacyPolicyClick = {})
            }
        }
    }

    @Test
    fun about_dark() {
        paparazzi.snapshot {
            PicmorrowTheme(darkTheme = true) {
                AboutScreen(onBackClick = {}, onPrivacyPolicyClick = {})
            }
        }
    }

    @Test
    fun privacy_light() {
        paparazzi.snapshot {
            PicmorrowTheme(darkTheme = false) {
                PrivacyPolicyScreen(onBackClick = {})
            }
        }
    }

    @Test
    fun privacy_dark() {
        paparazzi.snapshot {
            PicmorrowTheme(darkTheme = true) {
                PrivacyPolicyScreen(onBackClick = {})
            }
        }
    }
}
