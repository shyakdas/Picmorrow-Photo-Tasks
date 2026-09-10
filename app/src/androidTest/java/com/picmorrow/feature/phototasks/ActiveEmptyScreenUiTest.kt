package com.picmorrow.feature.phototasks

import androidx.annotation.StringRes
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.picmorrow.MainActivity
import com.picmorrow.R

@RunWith(AndroidJUnit4::class)
class ActiveEmptyScreenUiTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun openActiveScreen() {
        composeRule.onNodeWithText(string(R.string.welcome_explore_first)).performClick()
        composeRule.waitForIdle()
    }

    @Test
    fun activeEmptyScreen_showsHeaderFiltersAndEmptyState() {
        composeRule.onNodeWithText(string(R.string.active_title)).assertIsDisplayed()
        composeRule.onNodeWithText(string(R.string.filter_all)).assertIsDisplayed()
        composeRule.onNodeWithText(string(R.string.filter_parking)).assertIsDisplayed()
        composeRule.onNodeWithText(string(R.string.filter_buy)).assertIsDisplayed()
        composeRule.onNodeWithText(string(R.string.active_empty_title)).assertIsDisplayed()
        composeRule.onNodeWithText(string(R.string.active_empty_body)).assertIsDisplayed()
    }

    @Test
    fun activeEmptyScreen_showsBottomNavigationItems() {
        composeRule.onNodeWithText(string(R.string.active_title)).assertIsDisplayed()
        composeRule.onNodeWithText(string(R.string.active_completed_tab)).assertIsDisplayed()
    }

    @Test
    fun activeEmptyScreen_actionsAreClickable() {
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

    private fun string(@StringRes resId: Int): String = composeRule.activity.getString(resId)
}
