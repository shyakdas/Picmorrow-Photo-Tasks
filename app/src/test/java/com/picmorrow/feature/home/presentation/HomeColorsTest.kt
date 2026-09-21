package com.picmorrow.feature.home.presentation

import androidx.compose.ui.graphics.Color
import com.picmorrow.feature.home.presentation.model.homeColors
import com.picmorrow.feature.phototasks.presentation.common.model.photoTaskListColors
import com.picmorrow.ui.theme.DarkBackground
import com.picmorrow.ui.theme.DarkCoral
import com.picmorrow.ui.theme.DarkNavigationBar
import com.picmorrow.ui.theme.DarkNavigationBarBorder
import com.picmorrow.ui.theme.LightBackground
import com.picmorrow.ui.theme.LightNavigationBar
import com.picmorrow.ui.theme.LightNavigationBarBorder
import com.picmorrow.ui.theme.LightSelectedChipBorder
import com.picmorrow.ui.theme.LightSelectedChipContent
import org.junit.Assert.assertEquals
import org.junit.Test

class HomeColorsTest {
    @Test
    fun lightPaletteKeepsNavigationColorsInHome() {
        val colors = homeColors(darkTheme = false)

        assertEquals(LightBackground, colors.background)
        assertEquals(photoTaskListColors(false), colors.taskList)
        assertEquals(Color.Transparent, colors.selectedChipBackground)
        assertEquals(LightSelectedChipBorder, colors.selectedChipBorder)
        assertEquals(LightSelectedChipContent, colors.selectedChipContent)
        assertEquals(LightNavigationBar, colors.bottomBarBackground)
        assertEquals(LightNavigationBarBorder, colors.bottomBarBorder)
    }

    @Test
    fun darkPaletteKeepsNavigationColorsInHome() {
        val colors = homeColors(darkTheme = true)

        assertEquals(DarkBackground, colors.background)
        assertEquals(photoTaskListColors(true), colors.taskList)
        assertEquals(Color.Transparent, colors.selectedChipBackground)
        assertEquals(DarkCoral, colors.selectedChipBorder)
        assertEquals(DarkCoral, colors.selectedChipContent)
        assertEquals(DarkNavigationBar, colors.bottomBarBackground)
        assertEquals(DarkNavigationBarBorder, colors.bottomBarBorder)
    }
}
