package com.picmorrow.feature.home

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import androidx.compose.runtime.Composable
import com.android.resources.Density
import com.android.resources.ScreenOrientation
import org.junit.Rule
import org.junit.Test
import com.picmorrow.feature.home.presentation.screen.HomeScreen
import com.picmorrow.feature.phototasks.domain.model.PhotoTask
import com.picmorrow.feature.home.presentation.model.HomeContentState
import com.picmorrow.ui.theme.PicmorrowTheme
import com.picmorrow.ui.adaptive.AdaptiveLayoutInfo
import com.picmorrow.ui.adaptive.AdaptiveWidthClass
import com.picmorrow.ui.adaptive.ProvideAdaptiveLayoutInfo

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

    @Test
    fun active_tasks_medium() {
        paparazzi.unsafeUpdateConfig(
            DeviceConfig.PIXEL_5.copy(
                screenWidth = 1_400,
                screenHeight = 1_800,
                density = Density.XHIGH,
            ),
        )
        paparazzi.snapshot {
            HomeAdaptiveSnapshotContent(AdaptiveWidthClass.Medium)
        }
    }

    @Test
    fun active_tasks_expanded_foldable() {
        paparazzi.unsafeUpdateConfig(
            DeviceConfig.PIXEL_5.copy(
                screenWidth = 1_800,
                screenHeight = 1_200,
                density = Density.XHIGH,
                orientation = ScreenOrientation.LANDSCAPE,
            ),
        )
        paparazzi.snapshot {
            ProvideAdaptiveLayoutInfo(
                AdaptiveLayoutInfo(AdaptiveWidthClass.Expanded, false, false, true),
            ) {
                HomeSnapshotContent(darkTheme = false, populated = true)
            }
        }
    }

}

@Composable
private fun HomeAdaptiveSnapshotContent(widthClass: AdaptiveWidthClass) {
    ProvideAdaptiveLayoutInfo(AdaptiveLayoutInfo(widthClass, false, false, false)) {
        HomeSnapshotContent(darkTheme = false, populated = true)
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
