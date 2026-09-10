package com.picmorrow.feature.onboarding.presentation.screen

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.picmorrow.R
import com.picmorrow.ui.theme.DarkSecondaryText
import com.picmorrow.ui.theme.LightSecondaryText

internal object WelcomeThemeAssets {
    @DrawableRes
    fun heroRes(darkTheme: Boolean): Int =
        if (darkTheme) {
            R.drawable.onboarding_hero_dark
        } else {
            R.drawable.onboarding_hero_light
        }

    fun secondaryTextColor(darkTheme: Boolean): Color =
        if (darkTheme) {
            DarkSecondaryText
        } else {
            LightSecondaryText
        }
}
