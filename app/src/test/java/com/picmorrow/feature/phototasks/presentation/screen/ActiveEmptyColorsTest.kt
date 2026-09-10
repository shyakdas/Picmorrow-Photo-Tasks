package com.picmorrow.feature.phototasks.presentation.screen

import androidx.compose.ui.graphics.Color
import org.junit.Assert.assertEquals
import org.junit.Test
import com.picmorrow.ui.theme.DarkSecondaryText
import com.picmorrow.ui.theme.LightSecondaryText

class ActiveEmptyColorsTest {
    @Test
    fun activeEmptyColors_returnsLightThemeColors() {
        val colors = activeEmptyColors(darkTheme = false)

        assertEquals(Color(0xFFFAF8F5), colors.background)
        assertEquals(Color(0xFF1C1B1A), colors.primaryText)
        assertEquals(LightSecondaryText, colors.secondaryText)
        assertEquals(Color(0xFFFFE7E1), colors.emptyIconBackground)
        assertEquals(Color.White, colors.unselectedChipBackground)
        assertEquals(Color(0xFFE2DFDC), colors.bottomBarBorder)
    }

    @Test
    fun activeEmptyColors_returnsDarkThemeColors() {
        val colors = activeEmptyColors(darkTheme = true)

        assertEquals(Color(0xFF191919), colors.background)
        assertEquals(Color(0xFFF8F7F5), colors.primaryText)
        assertEquals(DarkSecondaryText, colors.secondaryText)
        assertEquals(Color(0xFF39211D), colors.emptyIconBackground)
        assertEquals(Color(0xFF252525), colors.unselectedChipBackground)
        assertEquals(Color(0xFF343332), colors.bottomBarBorder)
    }

    @Test
    fun activeEmptyColors_usesPrimaryColorForSelectedDarkChip() {
        val colors = activeEmptyColors(darkTheme = true)

        assertEquals(Color.Transparent, colors.selectedChipBackground)
        assertEquals(Color(0xFFFF7056), colors.selectedChipBorder)
        assertEquals(Color(0xFFFF7056), colors.selectedChipContent)
    }
}
