package com.picmorrow.feature.phototasks

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextReplacement
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.picmorrow.feature.phototasks.presentation.common.model.PhotoTaskCategory
import com.picmorrow.feature.phototasks.domain.model.PhotoTaskDraft
import com.picmorrow.feature.phototasks.presentation.screen.NewPhotoTaskScreen
import com.picmorrow.navigation.AppDestination
import com.picmorrow.ui.theme.PicmorrowTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
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
                    selectedCategory = PhotoTaskCategory.Remember,
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
                    selectedCategory = PhotoTaskCategory.Remember,
                    onCancelClick = {},
                    onRetakeClick = {},
                    onSaveClick = {},
                )
            }
        }

        composeRule.onNodeWithText("What needs to be done?").assertIsDisplayed()
        composeRule.onAllNodes(hasSetTextAction())[1].performClick()
        composeRule.onNodeWithText("Save task").assertIsDisplayed()
        Espresso.closeSoftKeyboard()
    }

    @Test
    fun titleIsLimitedTo60Characters() {
        composeRule.setContent {
            PicmorrowTheme {
                NewPhotoTaskScreen(
                    photoPath = "",
                    selectedCategory = PhotoTaskCategory.Parking,
                    onCancelClick = {},
                    onRetakeClick = {},
                    onSaveClick = {},
                )
            }
        }

        val title = composeRule.onAllNodes(hasSetTextAction())[0]
        title.performTextInput("T".repeat(60))
        title.performTextInput("X")

        title.assertTextEquals("T".repeat(60))
        composeRule.onNodeWithText("60 / 60").assertExists()
        title.performTextReplacement("T".repeat(59))
        title.assertTextEquals("T".repeat(59))
        composeRule.onNodeWithText("59 / 60").assertExists()
        Espresso.closeSoftKeyboard()
    }

    @Test
    fun notesAreLimitedTo200Characters() {
        composeRule.setContent {
            PicmorrowTheme {
                NewPhotoTaskScreen(
                    photoPath = "",
                    selectedCategory = PhotoTaskCategory.Parking,
                    onCancelClick = {},
                    onRetakeClick = {},
                    onSaveClick = {},
                )
            }
        }

        val notes = composeRule.onAllNodes(hasSetTextAction())[1]
        notes.performTextInput("N".repeat(200))
        notes.performTextInput("X")

        notes.assertTextEquals("N".repeat(200))
        composeRule.onNodeWithText("200 / 200").assertExists()
        notes.performTextReplacement("N".repeat(199))
        notes.assertTextEquals("N".repeat(199))
        composeRule.onNodeWithText("199 / 200").assertExists()
        Espresso.closeSoftKeyboard()
    }

    @Test
    fun reminderCanBeSelectedAndRemoved() {
        Espresso.closeSoftKeyboard()
        composeRule.setContent {
            PicmorrowTheme {
                NewPhotoTaskScreen(
                    photoPath = "",
                    selectedCategory = PhotoTaskCategory.Remember,
                    onCancelClick = {},
                    onRetakeClick = {},
                    onSaveClick = {},
                )
            }
        }

        composeRule.onNodeWithTag("reminder_row").performScrollTo().performClick()
        composeRule.onNodeWithText("In 1 hour").assertIsDisplayed().performClick()
        composeRule.onNodeWithTag("reminder_row").performScrollTo().performClick()
        composeRule.onNodeWithText("Remove reminder").assertIsDisplayed().performClick()
        composeRule.onNodeWithText("Set a reminder").assertIsDisplayed()
    }

    @Test
    fun savePassesPhotoCategoryTextAndReminder() {
        var savedDraft: PhotoTaskDraft? = null
        composeRule.setContent {
            PicmorrowTheme {
                NewPhotoTaskScreen(
                    photoPath = "/data/user/0/photo.jpg",
                    selectedCategory = PhotoTaskCategory.Parking,
                    onCancelClick = {},
                    onRetakeClick = {},
                    onSaveClick = { savedDraft = it },
                )
            }
        }

        composeRule.onAllNodes(hasSetTextAction())[0].performTextInput("Buy milk")
        composeRule.onAllNodes(hasSetTextAction())[1].performTextInput("Two cartons")
        Espresso.closeSoftKeyboard()
        composeRule.onNodeWithText("Buy").performClick()
        composeRule.onNodeWithTag("reminder_row").performScrollTo().performClick()
        composeRule.onNodeWithText("In 1 hour").performClick()
        composeRule.onNodeWithText("Save task").performClick()

        assertNotNull(savedDraft)
        assertEquals("/data/user/0/photo.jpg", savedDraft?.photoPath)
        assertEquals("Buy", savedDraft?.category)
        assertEquals("Buy milk", savedDraft?.title)
        assertEquals("Two cartons", savedDraft?.notes)
        assertTrue(savedDraft!!.reminderAtMillis!! > System.currentTimeMillis())
    }
}
