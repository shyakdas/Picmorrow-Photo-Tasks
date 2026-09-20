package com.picmorrow.feature.phototasks

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.picmorrow.feature.camera.presentation.model.CameraCategory
import com.picmorrow.feature.phototasks.presentation.screen.NewPhotoTaskScreen
import com.picmorrow.navigation.AppDestination
import com.picmorrow.ui.theme.PicmorrowTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewPhotoTaskScreenUiTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun retakeInvokesCameraReturn() {
        var retakeCount = 0
        composeRule.setContent {
            PicmorrowTheme {
                NewPhotoTaskScreen(
                    photoPath = "",
                    selectedCategory = CameraCategory.Remember,
                    onCancelClick = {},
                    onRetakeClick = { retakeCount++ },
                    onSaveClick = {},
                )
            }
        }

        composeRule.onNodeWithText("New Photo-Task").assertIsDisplayed()
        composeRule.onNodeWithText("Retake").performClick()
        assertEquals(1, retakeCount)
    }

    @Test
    fun capturedPhotoPathIsEncodedInRoute() {
        assertEquals(
            "new-photo-task?photoPath=%2Fdata%2Fuser%2F0%2Fphoto%20one.jpg&category=Remember",
            AppDestination.NewPhotoTask.route("/data/user/0/photo one.jpg", "Remember"),
        )
    }

    @Test
    fun notesCanBeFocusedWhileSaveActionRemainsVisible() {
        composeRule.setContent {
            PicmorrowTheme {
                NewPhotoTaskScreen(
                    photoPath = "",
                    selectedCategory = CameraCategory.Remember,
                    onCancelClick = {},
                    onRetakeClick = {},
                    onSaveClick = {},
                )
            }
        }

        composeRule.onNodeWithText("What needs to be done?").assertIsDisplayed()
        composeRule.onAllNodes(hasSetTextAction())[1].performClick()
        composeRule.onNodeWithText("Save task").assertIsDisplayed()
    }

    @Test
    fun reminderCanBeSelectedAndRemoved() {
        composeRule.setContent {
            PicmorrowTheme {
                NewPhotoTaskScreen(
                    photoPath = "",
                    selectedCategory = CameraCategory.Remember,
                    onCancelClick = {},
                    onRetakeClick = {},
                    onSaveClick = {},
                )
            }
        }

        composeRule.onNodeWithTag("reminder_row").performClick()
        composeRule.onNodeWithText("In 1 hour").assertIsDisplayed().performClick()
        composeRule.onNodeWithTag("reminder_row").performClick()
        composeRule.onNodeWithText("Remove reminder").assertIsDisplayed().performClick()
        composeRule.onNodeWithText("Set a reminder").assertIsDisplayed()
    }
}
