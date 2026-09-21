package com.picmorrow.feature.completed

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import androidx.compose.runtime.Composable
import com.picmorrow.feature.home.presentation.model.HomeContentState
import com.picmorrow.feature.home.presentation.model.HomeTab
import com.picmorrow.feature.home.presentation.screen.HomeScreen
import com.picmorrow.feature.phototasks.domain.model.PhotoTask
import com.picmorrow.ui.theme.PicmorrowTheme
import java.time.Instant
import org.junit.Rule
import org.junit.Test

class CompletedScreenScreenshotTest {
    @get:Rule
    val paparazzi = Paparazzi(deviceConfig = DeviceConfig.PIXEL_5)

    @Test
    fun completed_tasks_light() {
        paparazzi.snapshot { CompletedSnapshotContent(darkTheme = false, populated = true) }
    }

    @Test
    fun completed_tasks_dark() {
        paparazzi.snapshot { CompletedSnapshotContent(darkTheme = true, populated = true) }
    }

    @Test
    fun completed_empty_light() {
        paparazzi.snapshot { CompletedSnapshotContent(darkTheme = false) }
    }

    @Test
    fun completed_empty_dark() {
        paparazzi.snapshot { CompletedSnapshotContent(darkTheme = true) }
    }
}

@Composable
private fun CompletedSnapshotContent(darkTheme: Boolean, populated: Boolean = false) {
    PicmorrowTheme(darkTheme = darkTheme) {
        HomeScreen(
            darkTheme = darkTheme,
            initialTab = HomeTab.Completed,
            onTakePhotoClick = {},
            onSettingsClick = {},
            contentState = if (populated) {
                HomeContentState(
                    completedTasks = listOf(
                        completedTask(1, "Parking", "Car - B2, pillar C14", "2026-09-07T12:00:00Z"),
                        completedTask(2, "Buy", "Check this bulb size", "2026-09-06T12:00:00Z"),
                        completedTask(3, "Buy", "Find this coffee again", "2026-09-05T12:00:00Z"),
                        completedTask(4, "Remember", "Router cable position", "2026-09-03T12:00:00Z"),
                    ),
                )
            } else {
                HomeContentState()
            },
        )
    }
}

private fun completedTask(id: Long, category: String, title: String, iso: String): PhotoTask =
    PhotoTask(id, "", category, title, "", null, Instant.parse(iso).toEpochMilli())
