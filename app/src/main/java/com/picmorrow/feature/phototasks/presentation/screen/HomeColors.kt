package com.picmorrow.feature.phototasks.presentation.screen

import androidx.compose.ui.graphics.Color
import com.picmorrow.ui.theme.DarkBackground
import com.picmorrow.ui.theme.DarkBorder
import com.picmorrow.ui.theme.DarkCoral
import com.picmorrow.ui.theme.DarkEmptyIconBackground
import com.picmorrow.ui.theme.DarkInactiveNavigationItem
import com.picmorrow.ui.theme.DarkNavigationBar
import com.picmorrow.ui.theme.DarkNavigationBarBorder
import com.picmorrow.ui.theme.DarkSecondaryText
import com.picmorrow.ui.theme.DarkSurfaceRaised
import com.picmorrow.ui.theme.DarkText
import com.picmorrow.ui.theme.LightBackground
import com.picmorrow.ui.theme.LightBorder
import com.picmorrow.ui.theme.LightEmptyIconBackground
import com.picmorrow.ui.theme.LightInactiveNavigationItem
import com.picmorrow.ui.theme.LightNavigationBar
import com.picmorrow.ui.theme.LightNavigationBarBorder
import com.picmorrow.ui.theme.LightSecondaryText
import com.picmorrow.ui.theme.LightSelectedChipBorder
import com.picmorrow.ui.theme.LightSelectedChipContent
import com.picmorrow.ui.theme.LightText

internal data class HomeColors(
    val background: Color,
    val primaryText: Color,
    val secondaryText: Color,
    val emptyIconBackground: Color,
    val selectedChipBackground: Color,
    val selectedChipBorder: Color,
    val selectedChipContent: Color,
    val unselectedChipBackground: Color,
    val unselectedChipContent: Color,
    val chipBorder: Color,
    val bottomBarBackground: Color,
    val bottomBarBorder: Color,
    val inactiveBottomItem: Color,
)

internal fun homeColors(darkTheme: Boolean): HomeColors =
    if (darkTheme) {
        HomeColors(
            background = DarkBackground,
            primaryText = DarkText,
            secondaryText = DarkSecondaryText,
            emptyIconBackground = DarkEmptyIconBackground,
            selectedChipBackground = Color.Transparent,
            selectedChipBorder = DarkCoral,
            selectedChipContent = DarkCoral,
            unselectedChipBackground = DarkSurfaceRaised,
            unselectedChipContent = DarkSecondaryText,
            chipBorder = DarkBorder,
            bottomBarBackground = DarkNavigationBar,
            bottomBarBorder = DarkNavigationBarBorder,
            inactiveBottomItem = DarkInactiveNavigationItem,
        )
    } else {
        HomeColors(
            background = LightBackground,
            primaryText = LightText,
            secondaryText = LightSecondaryText,
            emptyIconBackground = LightEmptyIconBackground,
            selectedChipBackground = Color.Transparent,
            selectedChipBorder = LightSelectedChipBorder,
            selectedChipContent = LightSelectedChipContent,
            unselectedChipBackground = LightNavigationBar,
            unselectedChipContent = LightSecondaryText,
            chipBorder = LightBorder,
            bottomBarBackground = LightNavigationBar,
            bottomBarBorder = LightNavigationBarBorder,
            inactiveBottomItem = LightInactiveNavigationItem,
        )
    }
