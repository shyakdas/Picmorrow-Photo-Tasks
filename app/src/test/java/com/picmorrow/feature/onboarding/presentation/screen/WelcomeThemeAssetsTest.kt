package com.picmorrow.feature.onboarding.presentation.screen

import org.junit.Assert.assertEquals
import org.junit.Test
import com.picmorrow.R
import com.picmorrow.ui.theme.DarkSecondaryText
import com.picmorrow.ui.theme.LightSecondaryText

class WelcomeThemeAssetsTest {
    @Test
    fun heroRes_returnsLightHeroWhenThemeIsLight() {
        assertEquals(
            R.drawable.onboarding_hero_light,
            WelcomeThemeAssets.heroRes(darkTheme = false),
        )
    }

    @Test
    fun heroRes_returnsDarkHeroWhenThemeIsDark() {
        assertEquals(
            R.drawable.onboarding_hero_dark,
            WelcomeThemeAssets.heroRes(darkTheme = true),
        )
    }

    @Test
    fun secondaryTextColor_returnsMatchingThemeColor() {
        assertEquals(
            LightSecondaryText,
            WelcomeThemeAssets.secondaryTextColor(darkTheme = false),
        )
        assertEquals(
            DarkSecondaryText,
            WelcomeThemeAssets.secondaryTextColor(darkTheme = true),
        )
    }
}
