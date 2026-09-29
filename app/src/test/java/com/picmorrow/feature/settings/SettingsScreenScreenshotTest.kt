package com.picmorrow.feature.settings

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.resources.NightMode
import com.picmorrow.feature.settings.presentation.model.ThemeSettingsUiState
import com.picmorrow.feature.settings.presentation.screen.SettingsScreen
import com.picmorrow.ui.theme.PicmorrowTheme
import org.junit.Rule
import org.junit.Test

class SettingsScreenScreenshotTest {
    @get:Rule
    val paparazzi = Paparazzi(deviceConfig = DeviceConfig.PIXEL_5)

    @Test
    fun settings_light() {
        paparazzi.snapshot {
            PicmorrowTheme(darkTheme = false) {
                SettingsScreen(
                    uiState = ThemeSettingsUiState(),
                    notificationsAllowed = true,
                    onBackClick = {},
                    onThemeModeSelected = {},
                    onNotificationClick = {},
                    onAddCameraShortcutClick = {},
                    darkTheme = false,
                )
            }
        }
    }

    @Test
    fun settings_dark() {
        paparazzi.unsafeUpdateConfig(deviceConfig = DeviceConfig.PIXEL_5.copy(nightMode = NightMode.NIGHT))
        paparazzi.snapshot {
            PicmorrowTheme(darkTheme = true) {
                SettingsScreen(
                    uiState = ThemeSettingsUiState(),
                    notificationsAllowed = true,
                    onBackClick = {},
                    onThemeModeSelected = {},
                    onNotificationClick = {},
                    onAddCameraShortcutClick = {},
                    darkTheme = true,
                )
            }
        }
    }
}
