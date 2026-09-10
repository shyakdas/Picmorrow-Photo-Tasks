@file:Suppress("MagicNumber")

package com.picmorrow.feature.phototasks.presentation.screen

import androidx.compose.ui.graphics.Color
import com.picmorrow.ui.theme.DarkSecondaryText
import com.picmorrow.ui.theme.LightSecondaryText

internal data class ActiveEmptyColors(
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

internal fun activeEmptyColors(darkTheme: Boolean): ActiveEmptyColors =
    if (darkTheme) {
        ActiveEmptyColors(
            background = Color(0xFF191919),
            primaryText = Color(0xFFF8F7F5),
            secondaryText = DarkSecondaryText,
            emptyIconBackground = Color(0xFF39211D),
            selectedChipBackground = Color.Transparent,
            selectedChipBorder = Color(0xFFFF7056),
            selectedChipContent = Color(0xFFFF7056),
            unselectedChipBackground = Color(0xFF252525),
            unselectedChipContent = Color(0xFFA9A6A2),
            chipBorder = Color(0xFF383838),
            bottomBarBackground = Color(0xFF242423),
            bottomBarBorder = Color(0xFF343332),
            inactiveBottomItem = Color(0xFFA09D99),
        )
    } else {
        ActiveEmptyColors(
            background = Color(0xFFFAF8F5),
            primaryText = Color(0xFF1C1B1A),
            secondaryText = LightSecondaryText,
            emptyIconBackground = Color(0xFFFFE7E1),
            selectedChipBackground = Color.Transparent,
            selectedChipBorder = Color(0xFF6C6763),
            selectedChipContent = Color(0xFF5F5B57),
            unselectedChipBackground = Color.White,
            unselectedChipContent = Color(0xFF6E6A67),
            chipBorder = Color(0xFFE4E0DC),
            bottomBarBackground = Color.White,
            bottomBarBorder = Color(0xFFE2DFDC),
            inactiveBottomItem = Color(0xFF96928E),
        )
    }
