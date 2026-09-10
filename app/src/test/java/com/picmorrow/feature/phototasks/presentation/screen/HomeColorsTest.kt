package com.picmorrow.feature.phototasks.presentation.screen

import org.junit.Assert.assertEquals
import org.junit.Test
import com.picmorrow.ui.theme.DarkBackground
import com.picmorrow.ui.theme.DarkCoral
import com.picmorrow.ui.theme.DarkEmptyIconBackground
import com.picmorrow.ui.theme.DarkNavigationBarBorder
import com.picmorrow.ui.theme.DarkSecondaryText
import com.picmorrow.ui.theme.DarkSurfaceRaised
import com.picmorrow.ui.theme.DarkText
import com.picmorrow.ui.theme.LightBackground
import com.picmorrow.ui.theme.LightEmptyIconBackground
import com.picmorrow.ui.theme.LightNavigationBar
import com.picmorrow.ui.theme.LightNavigationBarBorder
import com.picmorrow.ui.theme.LightSecondaryText
import com.picmorrow.ui.theme.LightText

class HomeColorsTest {
    @Test
    fun homeColors_returnsLightThemeColors() {
        val colors = homeColors(darkTheme = false)

        assertEquals(LightBackground, colors.background)
        assertEquals(LightText, colors.primaryText)
        assertEquals(LightSecondaryText, colors.secondaryText)
        assertEquals(LightEmptyIconBackground, colors.emptyIconBackground)
        assertEquals(LightNavigationBar, colors.unselectedChipBackground)
        assertEquals(LightNavigationBarBorder, colors.bottomBarBorder)
    }

    @Test
    fun homeColors_returnsDarkThemeColors() {
        val colors = homeColors(darkTheme = true)

        assertEquals(DarkBackground, colors.background)
        assertEquals(DarkText, colors.primaryText)
        assertEquals(DarkSecondaryText, colors.secondaryText)
        assertEquals(DarkEmptyIconBackground, colors.emptyIconBackground)
        assertEquals(DarkSurfaceRaised, colors.unselectedChipBackground)
        assertEquals(DarkNavigationBarBorder, colors.bottomBarBorder)
    }

    @Test
    fun homeColors_usesPrimaryColorForSelectedDarkChip() {
        val colors = homeColors(darkTheme = true)

        assertEquals(androidx.compose.ui.graphics.Color.Transparent, colors.selectedChipBackground)
        assertEquals(DarkCoral, colors.selectedChipBorder)
        assertEquals(DarkCoral, colors.selectedChipContent)
    }
}
