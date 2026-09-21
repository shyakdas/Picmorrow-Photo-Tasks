package com.picmorrow.feature.phototasks

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.picmorrow.feature.phototasks.presentation.common.model.PhotoTaskCategory
import com.picmorrow.feature.phototasks.presentation.screen.NewPhotoTaskScreen
import com.picmorrow.ui.theme.PicmorrowTheme
import org.junit.Rule
import org.junit.Test

class NewPhotoTaskScreenScreenshotTest {
    @get:Rule
    val paparazzi = Paparazzi(deviceConfig = DeviceConfig.PIXEL_5)

    @Test
    fun newPhotoTask_light() {
        paparazzi.snapshot {
            PicmorrowTheme(darkTheme = false) {
                NewPhotoTaskScreen(
                    photoPath = "",
                    selectedCategory = PhotoTaskCategory.Remember,
                    onCancelClick = {},
                    onRetakeClick = {},
                    onSaveClick = {},
                    darkTheme = false,
                )
            }
        }
    }

    @Test
    fun newPhotoTask_dark() {
        paparazzi.snapshot {
            PicmorrowTheme(darkTheme = true) {
                NewPhotoTaskScreen(
                    photoPath = "",
                    selectedCategory = PhotoTaskCategory.Remember,
                    onCancelClick = {},
                    onRetakeClick = {},
                    onSaveClick = {},
                    darkTheme = true,
                )
            }
        }
    }
}
