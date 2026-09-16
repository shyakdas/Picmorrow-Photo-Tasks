package com.picmorrow.feature.camera

import androidx.annotation.StringRes
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.picmorrow.R
import com.picmorrow.feature.camera.presentation.screen.CameraPermissionScreen
import com.picmorrow.ui.theme.PicmorrowTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CameraPermissionScreenUiTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun cameraPermissionScreen_showsPermissionContent() {
        setContent()

        composeRule.onNodeWithText(string(R.string.camera_permission_title)).assertIsDisplayed()
        composeRule.onNodeWithText(string(R.string.camera_permission_body)).assertIsDisplayed()
        composeRule.onNodeWithText(string(R.string.camera_permission_action)).assertIsDisplayed()
        composeRule
            .onNodeWithContentDescription(string(R.string.camera_close_content_description))
            .assertIsDisplayed()
    }

    @Test
    fun cameraPermissionScreen_actionsAreClickable() {
        setContent()

        composeRule
            .onNodeWithText(string(R.string.camera_permission_action))
            .assertHasClickAction()
        composeRule
            .onNodeWithContentDescription(string(R.string.camera_close_content_description))
            .assertHasClickAction()
    }

    @Test
    fun cameraPermissionScreen_invokesActions() {
        var closeClickCount = 0
        var requestPermissionClickCount = 0

        setContent(
            onCloseClick = { closeClickCount++ },
            onRequestPermissionClick = { requestPermissionClickCount++ },
        )

        composeRule
            .onNodeWithContentDescription(string(R.string.camera_close_content_description))
            .performClick()
        composeRule.onNodeWithText(string(R.string.camera_permission_action)).performClick()

        assertEquals(1, closeClickCount)
        assertEquals(1, requestPermissionClickCount)
    }

    private fun setContent(
        onCloseClick: () -> Unit = {},
        onRequestPermissionClick: () -> Unit = {},
    ) {
        composeRule.setContent {
            PicmorrowTheme {
                CameraPermissionScreen(
                    onCloseClick = onCloseClick,
                    onRequestPermissionClick = onRequestPermissionClick,
                )
            }
        }
    }

    private fun string(@StringRes resId: Int): String =
        InstrumentationRegistry.getInstrumentation().targetContext.getString(resId)
}
