package com.picmorrow.feature.camera

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.picmorrow.R
import com.picmorrow.feature.camera.presentation.components.CameraTopBar
import com.picmorrow.ui.theme.PicmorrowTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CameraTopBarUiTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun cameraTopBar_showsCloseAndFlashActions() {
        setContent()

        composeRule
            .onNodeWithContentDescription(string(R.string.camera_close_content_description))
            .assertIsDisplayed()
            .assertHasClickAction()
        composeRule
            .onNodeWithContentDescription(string(R.string.camera_flash_off_content_description))
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun clickingFlashAction_changesFlashIconState() {
        setContent()

        composeRule
            .onNodeWithContentDescription(string(R.string.camera_flash_off_content_description))
            .performClick()

        composeRule
            .onNodeWithContentDescription(string(R.string.camera_flash_on_content_description))
            .assertIsDisplayed()
    }

    private fun setContent() {
        composeRule.setContent {
            PicmorrowTheme(darkTheme = true) {
                var flashEnabled by remember { mutableStateOf(false) }

                CameraTopBar(
                    flashEnabled = flashEnabled,
                    onCloseClick = {},
                    onFlashClick = { flashEnabled = !flashEnabled },
                )
            }
        }
    }

    private fun string(@StringRes resId: Int): String =
        InstrumentationRegistry.getInstrumentation().targetContext.getString(resId)
}
