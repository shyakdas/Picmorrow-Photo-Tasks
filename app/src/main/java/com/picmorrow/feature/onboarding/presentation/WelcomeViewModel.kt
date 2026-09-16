package com.picmorrow.feature.onboarding.presentation

import androidx.lifecycle.ViewModel
import com.picmorrow.R
import com.picmorrow.feature.onboarding.presentation.model.WelcomeUiState
import com.picmorrow.ui.theme.DarkSecondaryText
import com.picmorrow.ui.theme.LightSecondaryText

class WelcomeViewModel : ViewModel() {
    fun uiState(darkTheme: Boolean): WelcomeUiState =
        if (darkTheme) {
            WelcomeUiState(
                heroRes = R.drawable.onboarding_hero_dark,
                secondaryTextColor = DarkSecondaryText,
            )
        } else {
            WelcomeUiState(
                heroRes = R.drawable.onboarding_hero_light,
                secondaryTextColor = LightSecondaryText,
            )
        }
}
