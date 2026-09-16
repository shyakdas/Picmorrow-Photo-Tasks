package com.picmorrow.feature.onboarding.presentation

import org.junit.Assert.assertEquals
import org.junit.Test
import com.picmorrow.R
import com.picmorrow.ui.theme.DarkSecondaryText
import com.picmorrow.ui.theme.LightSecondaryText

class WelcomeViewModelTest {
    private val viewModel = WelcomeViewModel()

    @Test
    fun uiState_returnsLightThemeState() {
        val uiState = viewModel.uiState(darkTheme = false)

        assertEquals(R.drawable.onboarding_hero_light, uiState.heroRes)
        assertEquals(LightSecondaryText, uiState.secondaryTextColor)
    }

    @Test
    fun uiState_returnsDarkThemeState() {
        val uiState = viewModel.uiState(darkTheme = true)

        assertEquals(R.drawable.onboarding_hero_dark, uiState.heroRes)
        assertEquals(DarkSecondaryText, uiState.secondaryTextColor)
    }
}
