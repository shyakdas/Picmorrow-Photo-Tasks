package com.picmorrow.feature.home

import androidx.annotation.StringRes
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.picmorrow.feature.phototasks.domain.model.PhotoTask
import com.picmorrow.feature.home.presentation.model.HomeContentState
import com.picmorrow.feature.home.presentation.screen.HomeScreen
import com.picmorrow.feature.phototasks.presentation.common.components.formatCompletionDate
import com.picmorrow.ui.theme.PicmorrowTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals
import com.picmorrow.R
import java.time.Instant

@RunWith(AndroidJUnit4::class)
class HomeScreenUiTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun homeScreen_showsHeaderFiltersAndActiveEmptyState() {
        showHome()
        composeRule.onAllNodesWithText(string(R.string.active_title)).onFirst().assertIsDisplayed()
        composeRule.onNodeWithText(string(R.string.filter_all)).assertIsDisplayed()
        composeRule.onNodeWithText(string(R.string.filter_parking)).assertIsDisplayed()
        composeRule.onNodeWithText(string(R.string.filter_buy)).assertIsDisplayed()
        composeRule.onNodeWithText(string(R.string.active_empty_title)).assertIsDisplayed()
        composeRule.onNodeWithText(string(R.string.active_empty_body)).assertIsDisplayed()
    }

    @Test
    fun homeScreen_showsBottomNavigationItems() {
        showHome()
        composeRule.onAllNodesWithText(string(R.string.active_title)).onFirst().assertIsDisplayed()
        composeRule.onNodeWithText(string(R.string.active_completed_tab)).assertIsDisplayed()
    }

    @Test
    fun homeScreen_actionsAreClickable() {
        showHome()
        composeRule
            .onNodeWithText(string(R.string.active_take_photo))
            .assertHasClickAction()
        composeRule
            .onNodeWithContentDescription(string(R.string.active_camera_content_description))
            .assertHasClickAction()
        composeRule
            .onNodeWithContentDescription(string(R.string.active_settings_content_description))
            .assertHasClickAction()
    }

    @Test
    fun homeScreen_showsSavedCardsAndCompletesTask() {
        var completedId: Long? = null
        showHome(
            HomeContentState(activeTasks = listOf(task(7, "Parking", "Car - B2, pillar C14"), task(8, "Buy", "Check this bulb size"))),
            onCompleteClick = { completedId = it },
        )

        composeRule.onNodeWithText("Car - B2, pillar C14").assertIsDisplayed()
        composeRule.onNodeWithText("Check this bulb size").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Complete Car - B2, pillar C14").performClick()
        assertEquals(7L, completedId)
    }

    @Test
    fun homeScreen_filtersByCategoryAndShowsCompletedTasks() {
        val completedAt = Instant.parse("2026-09-07T12:00:00Z").toEpochMilli()
        showHome(
            HomeContentState(
                activeTasks = listOf(task(7, "Parking", "Find car"), task(8, "Buy", "Buy milk")),
                completedTasks = listOf(task(9, "Collect", "Pick up parcel").copy(completedAtMillis = completedAt)),
            ),
        )

        composeRule.onAllNodesWithText(string(R.string.filter_buy)).onFirst().performClick()
        composeRule.onNodeWithText("Buy milk").assertIsDisplayed()
        composeRule.onNodeWithText("Find car").assertDoesNotExist()
        composeRule.onNodeWithText(string(R.string.filter_all)).performClick()
        composeRule.onNodeWithText(string(R.string.active_completed_tab)).performClick()
        composeRule.onNodeWithText("Pick up parcel").assertIsDisplayed()
        composeRule.onNodeWithText("Completed ${formatCompletionDate(completedAt)}").assertIsDisplayed()
        composeRule.onNodeWithText(string(R.string.filter_all)).assertDoesNotExist()
        composeRule.onNodeWithContentDescription(string(R.string.active_camera_content_description)).assertDoesNotExist()
    }

    @Test
    fun homeScreen_showsCompletedEmptyState() {
        showHome()

        composeRule.onNodeWithText(string(R.string.active_completed_tab)).performClick()

        composeRule.onNodeWithText(string(R.string.completed_empty_title)).assertIsDisplayed()
        composeRule.onNodeWithText(string(R.string.completed_empty_body)).assertIsDisplayed()
        composeRule.onNodeWithText(string(R.string.active_take_photo)).assertHasClickAction()
    }

    private fun showHome(state: HomeContentState = HomeContentState(), onCompleteClick: (Long) -> Unit = {}) {
        composeRule.setContent {
            PicmorrowTheme {
                HomeScreen(
                    onTakePhotoClick = {},
                    onSettingsClick = {},
                    onCompleteClick = onCompleteClick,
                    contentState = state,
                )
            }
        }
    }

    private fun task(id: Long, category: String, title: String) =
        PhotoTask(id, "", category, title, "", null, null)

    private fun string(@StringRes resId: Int): String =
        InstrumentationRegistry.getInstrumentation().targetContext.getString(resId)
}
