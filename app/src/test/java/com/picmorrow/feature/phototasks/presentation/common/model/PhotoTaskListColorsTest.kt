package com.picmorrow.feature.phototasks.presentation.common.model

import com.picmorrow.ui.theme.DarkBorder
import com.picmorrow.ui.theme.DarkEmptyIconBackground
import com.picmorrow.ui.theme.DarkSecondaryText
import com.picmorrow.ui.theme.DarkSurfaceRaised
import com.picmorrow.ui.theme.DarkText
import com.picmorrow.ui.theme.LightBorder
import com.picmorrow.ui.theme.LightEmptyIconBackground
import com.picmorrow.ui.theme.LightNavigationBar
import com.picmorrow.ui.theme.LightSecondaryText
import com.picmorrow.ui.theme.LightText
import org.junit.Assert.assertEquals
import org.junit.Test

class PhotoTaskListColorsTest {
    @Test
    fun lightListColorsMatchSharedTaskComponents() {
        val colors = photoTaskListColors(darkTheme = false)

        assertEquals(LightText, colors.primaryText)
        assertEquals(LightSecondaryText, colors.secondaryText)
        assertEquals(LightEmptyIconBackground, colors.emptyIconBackground)
        assertEquals(LightNavigationBar, colors.cardBackground)
        assertEquals(LightBorder, colors.cardBorder)
    }

    @Test
    fun darkListColorsMatchSharedTaskComponents() {
        val colors = photoTaskListColors(darkTheme = true)

        assertEquals(DarkText, colors.primaryText)
        assertEquals(DarkSecondaryText, colors.secondaryText)
        assertEquals(DarkEmptyIconBackground, colors.emptyIconBackground)
        assertEquals(DarkSurfaceRaised, colors.cardBackground)
        assertEquals(DarkBorder, colors.cardBorder)
    }
}
