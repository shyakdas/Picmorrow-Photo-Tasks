package com.picmorrow.feature.settings

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.android.resources.NightMode
import com.picmorrow.feature.settings.domain.model.AppThemeMode
import com.picmorrow.feature.settings.presentation.model.ThemeSettingsUiState
import com.picmorrow.feature.settings.presentation.screen.SettingsScreen
import com.picmorrow.feature.settings.presentation.screen.ThemeSheetContent
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
                    isCameraShortcutPinned = true,
                    onBackClick = {},
                    onThemeModeSelected = {},
                    onNotificationClick = {},
                    onAddCameraShortcutClick = {},
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
                    isCameraShortcutPinned = true,
                    onBackClick = {},
                    onThemeModeSelected = {},
                    onNotificationClick = {},
                    onAddCameraShortcutClick = {},
                )
            }
        }
    }

    @Test
    fun theme_bottom_sheet_light() {
        paparazzi.snapshot {
            PicmorrowTheme(darkTheme = false) {
                Box(
                    modifier = Modifier.fillMaxSize().background(Color(0xFF999999)),
                    contentAlignment = Alignment.BottomCenter,
                ) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                        color = MaterialTheme.colorScheme.surface,
                    ) {
                        Column {
                            Box(
                                modifier = Modifier.fillMaxWidth().height(32.dp),
                                contentAlignment = Alignment.TopCenter,
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(top = 12.dp)
                                        .size(width = 32.dp, height = 4.dp)
                                        .clip(RoundedCornerShape(2.dp))
                                        .background(Color(0xFFB8B5B2)),
                                )
                            }
                            ThemeSheetContent(
                                selectedMode = AppThemeMode.System,
                                onSelected = {},
                            )
                        }
                    }
                }
            }
        }
    }
}
