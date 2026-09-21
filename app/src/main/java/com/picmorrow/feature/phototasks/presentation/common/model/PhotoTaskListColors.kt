package com.picmorrow.feature.phototasks.presentation.common.model

import androidx.compose.ui.graphics.Color
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

internal data class PhotoTaskListColors(
    val primaryText: Color,
    val secondaryText: Color,
    val emptyIconBackground: Color,
    val cardBackground: Color,
    val cardBorder: Color,
)

internal fun photoTaskListColors(darkTheme: Boolean): PhotoTaskListColors =
    if (darkTheme) {
        PhotoTaskListColors(
            primaryText = DarkText,
            secondaryText = DarkSecondaryText,
            emptyIconBackground = DarkEmptyIconBackground,
            cardBackground = DarkSurfaceRaised,
            cardBorder = DarkBorder,
        )
    } else {
        PhotoTaskListColors(
            primaryText = LightText,
            secondaryText = LightSecondaryText,
            emptyIconBackground = LightEmptyIconBackground,
            cardBackground = LightNavigationBar,
            cardBorder = LightBorder,
        )
    }
