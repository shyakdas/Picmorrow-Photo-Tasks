package com.picmorrow.feature.onboarding

import androidx.annotation.StringRes
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.picmorrow.MainActivity
import com.picmorrow.R

@RunWith(AndroidJUnit4::class)
class WelcomeScreenUiTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun welcomeScreen_showsContentAfterLaunch() {
        composeRule.onNodeWithText(string(R.string.welcome_title)).assertIsDisplayed()
        composeRule.onNodeWithText(string(R.string.welcome_body)).assertIsDisplayed()
        composeRule.onNodeWithText(string(R.string.welcome_take_first_photo)).assertIsDisplayed()
        composeRule.onNodeWithText(string(R.string.welcome_explore_first)).assertIsDisplayed()
    }

    @Test
    fun welcomeScreen_actionsAreClickable() {
        composeRule
            .onNodeWithText(string(R.string.welcome_take_first_photo))
            .assertHasClickAction()
        composeRule
            .onNodeWithText(string(R.string.welcome_explore_first))
            .assertHasClickAction()
    }

    @Test
    fun clickingExploreFirst_opensActiveScreen() {
        composeRule.onNodeWithText(string(R.string.welcome_explore_first)).performClick()
        composeRule.waitForIdle()

        composeRule.onNodeWithText(string(R.string.active_title)).assertIsDisplayed()
        composeRule.onNodeWithText(string(R.string.active_empty_title)).assertIsDisplayed()
        composeRule.onNodeWithText(string(R.string.active_take_photo)).assertIsDisplayed()
    }

    private fun string(@StringRes resId: Int): String = composeRule.activity.getString(resId)
}
