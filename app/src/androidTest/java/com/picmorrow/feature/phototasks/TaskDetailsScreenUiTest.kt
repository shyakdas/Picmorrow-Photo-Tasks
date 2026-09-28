package com.picmorrow.feature.phototasks

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.picmorrow.feature.phototasks.domain.model.PhotoTask
import com.picmorrow.feature.phototasks.presentation.model.TaskDetailsUiState
import com.picmorrow.feature.phototasks.presentation.screen.TaskDetailsScreen
import com.picmorrow.ui.theme.PicmorrowTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TaskDetailsScreenUiTest {
    @get:Rule val composeRule = createComposeRule()

    @Test
    fun activeTaskShowsDetailsAndActions() {
        var backClicked = false
        var editClicked = false
        var doneClicked = false
        showTask(onBack = { backClicked = true }, onEdit = { editClicked = true }, onDone = { doneClicked = true })

        composeRule.onNodeWithText("Task details").assertIsDisplayed()
        composeRule.onNodeWithText("Car - B2, pillar C14").assertIsDisplayed()
        composeRule.onNodeWithText("Near the lift").assertIsDisplayed()
        composeRule.onNodeWithText("Captured 8 Sep 2025").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("More options").performClick()
        composeRule.onNodeWithText("Edit task").performClick()
        composeRule.onNodeWithText("Mark done").performClick()
        composeRule.onNodeWithContentDescription("Back").performClick()

        assertTrue(editClicked)
        assertTrue(doneClicked)
        assertTrue(backClicked)
    }

    @Test
    fun completedTaskIsReadOnly() {
        showTask(task = task().copy(completedAtMillis = CAPTURED_AT + 1_000L))

        composeRule.onNodeWithContentDescription("More options").assertDoesNotExist()
        composeRule.onNodeWithText("Mark done").assertDoesNotExist()
        composeRule.onNodeWithText("Completed 8 Sep 2025").assertIsDisplayed()
    }

    @Test
    fun notFoundStateCanNavigateBack() {
        var backClicked = false
        composeRule.setContent {
            PicmorrowTheme { TaskDetailsScreen(TaskDetailsUiState.NotFound, { backClicked = true }, {}, {}) }
        }

        composeRule.onNodeWithText("Task not found").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Back").performClick()
        assertTrue(backClicked)
    }

    private fun showTask(
        task: PhotoTask = task(),
        onBack: () -> Unit = {},
        onEdit: () -> Unit = {},
        onDone: () -> Unit = {},
    ) {
        composeRule.setContent {
            PicmorrowTheme {
                TaskDetailsScreen(TaskDetailsUiState.Content(task), onBack, onEdit, onDone, darkTheme = false)
            }
        }
    }

    private fun task() = PhotoTask(1, "", "Parking", "Car - B2, pillar C14", "Near the lift", null, null, CAPTURED_AT)

    private companion object {
        val CAPTURED_AT = java.time.Instant.parse("2025-09-08T10:00:00Z").toEpochMilli()
    }
}
