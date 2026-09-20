@file:Suppress("MagicNumber")

package com.picmorrow.feature.phototasks.presentation.screen

import androidx.compose.ui.graphics.Color
import com.picmorrow.ui.theme.DarkBackground
import com.picmorrow.ui.theme.DarkBorder
import com.picmorrow.ui.theme.DarkSecondaryText
import com.picmorrow.ui.theme.DarkSurfaceRaised
import com.picmorrow.ui.theme.DarkText
import com.picmorrow.ui.theme.LightBackground
import com.picmorrow.ui.theme.LightBorder
import com.picmorrow.ui.theme.LightSecondaryText
import com.picmorrow.ui.theme.LightText

internal data class NewPhotoTaskColors(
    val background: Color,
    val primaryText: Color,
    val secondaryText: Color,
    val placeholderText: Color,
    val inputBackground: Color,
    val border: Color,
    val limitReached: Color,
    val selectedChipBackground: Color,
    val selectedChipBorder: Color,
    val selectedChipContent: Color,
)

internal fun newPhotoTaskColors(darkTheme: Boolean): NewPhotoTaskColors =
    if (darkTheme) {
        NewPhotoTaskColors(
            background = DarkBackground,
            primaryText = DarkText,
            secondaryText = DarkSecondaryText,
            placeholderText = Color(0xFF9C9995),
            inputBackground = DarkSurfaceRaised,
            border = DarkBorder,
            limitReached = Color(0xFFFF6B61),
            selectedChipBackground = Color.Transparent,
            selectedChipBorder = Color(0xFF00A7A1),
            selectedChipContent = Color(0xFF22B7B2),
        )
    } else {
        NewPhotoTaskColors(
            background = LightBackground,
            primaryText = LightText,
            secondaryText = LightSecondaryText,
            placeholderText = Color(0xFF9B9792),
            inputBackground = Color.White,
            border = LightBorder,
            limitReached = Color(0xFFBE3E37),
            selectedChipBackground = Color(0xFFE8FFFD),
            selectedChipBorder = Color(0xFF00A7A1),
            selectedChipContent = Color(0xFF087A77),
        )
    }
