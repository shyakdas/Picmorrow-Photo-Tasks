@file:Suppress("MagicNumber")

package com.picmorrow.feature.phototasks

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
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.picmorrow.feature.phototasks.presentation.components.ReminderSheetContent
import com.picmorrow.ui.theme.PicmorrowTheme
import java.time.ZoneId
import java.time.ZonedDateTime
import org.junit.Rule
import org.junit.Test

class ReminderSheetScreenshotTest {
    @get:Rule
    val paparazzi = Paparazzi(deviceConfig = DeviceConfig.PIXEL_5)

    @Test
    fun reminderSheet_light() {
        paparazzi.snapshot { ReminderSheetSnapshot(darkTheme = false) }
    }

    @Test
    fun reminderSheet_dark() {
        paparazzi.snapshot { ReminderSheetSnapshot(darkTheme = true) }
    }
}

@Composable
private fun ReminderSheetSnapshot(darkTheme: Boolean) {
    val now = ZonedDateTime.of(2026, 9, 18, 9, 41, 0, 0, ZoneId.systemDefault())
    PicmorrowTheme(darkTheme = darkTheme) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(if (darkTheme) Color(0xFF111111) else Color(0xFF999999)),
            contentAlignment = Alignment.BottomCenter,
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp),
                color = if (darkTheme) Color(0xFF272727) else Color(0xFFFEFCFA),
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        contentAlignment = Alignment.TopCenter,
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .size(width = 36.dp, height = 4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(if (darkTheme) Color(0xFF3B3B3B) else Color(0xFFE5E3E0)),
                        )
                    }
                    ReminderSheetContent(darkTheme, now, {}, {}, {})
                }
            }
        }
    }
}
