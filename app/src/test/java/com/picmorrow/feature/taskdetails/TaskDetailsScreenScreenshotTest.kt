package com.picmorrow.feature.taskdetails

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.picmorrow.feature.phototasks.domain.model.PhotoTask
import com.picmorrow.feature.taskdetails.presentation.model.TaskDetailsUiState
import com.picmorrow.feature.taskdetails.presentation.screen.TaskDetailsScreen
import com.picmorrow.ui.theme.PicmorrowTheme
import java.time.Instant
import org.junit.Rule
import org.junit.Test

class TaskDetailsScreenScreenshotTest {
    @get:Rule val paparazzi = Paparazzi(deviceConfig = DeviceConfig.PIXEL_5)

    @Test fun active_light() = snapshot(darkTheme = false, completed = false)

    @Test fun active_dark() = snapshot(darkTheme = true, completed = false)

    @Test fun completed_light() = snapshot(darkTheme = false, completed = true)

    @Test fun completed_dark() = snapshot(darkTheme = true, completed = true)

    private fun snapshot(darkTheme: Boolean, completed: Boolean) {
        val capturedAt = Instant.parse("2025-09-08T10:00:00Z").toEpochMilli()
        val task = PhotoTask(
            id = 1,
            photoPath = "",
            category = "Parking",
            title = "Car - B2, pillar C14",
            notes = "Near the lift",
            reminderAtMillis = null,
            completedAtMillis = if (completed) capturedAt + 86_400_000L else null,
            capturedAtMillis = capturedAt,
        )
        paparazzi.snapshot {
            PicmorrowTheme(darkTheme) {
                TaskDetailsScreen(TaskDetailsUiState.Content(task), {}, {}, {}, {}, darkTheme = darkTheme)
            }
        }
    }
}
