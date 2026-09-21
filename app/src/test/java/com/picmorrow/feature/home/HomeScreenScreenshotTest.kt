package com.picmorrow.feature.home

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import androidx.compose.runtime.Composable
import org.junit.Rule
import org.junit.Test
import com.picmorrow.feature.home.presentation.screen.HomeScreen
import com.picmorrow.feature.phototasks.domain.model.PhotoTask
import com.picmorrow.feature.home.presentation.model.HomeContentState
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

    @Test
    fun active_tasks_light() {
        paparazzi.snapshot { HomeSnapshotContent(darkTheme = false, populated = true) }
    }

    @Test
    fun active_tasks_dark() {
        paparazzi.snapshot { HomeSnapshotContent(darkTheme = true, populated = true) }
    }

}

@Composable
private fun HomeSnapshotContent(darkTheme: Boolean, populated: Boolean = false) {
    PicmorrowTheme(darkTheme = darkTheme) {
        HomeScreen(
            darkTheme = darkTheme,
            onTakePhotoClick = {},
            onSettingsClick = {},
            contentState = if (populated) {
                HomeContentState(
                    activeTasks = listOf(
                        PhotoTask(1, "", "Parking", "Car - B2, pillar C14", "", null, null),
                        PhotoTask(2, "", "Buy", "Check this bulb size", "", null, null),
                    ),
                )
            } else {
                HomeContentState()
            },
        )
    }
}
