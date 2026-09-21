package com.picmorrow.feature.home.presentation.model

import androidx.compose.ui.graphics.Color
import com.picmorrow.feature.phototasks.presentation.common.model.PhotoTaskListColors
import com.picmorrow.feature.phototasks.presentation.common.model.photoTaskListColors
import com.picmorrow.ui.theme.DarkBackground
import com.picmorrow.ui.theme.DarkCoral
import com.picmorrow.ui.theme.DarkInactiveNavigationItem
import com.picmorrow.ui.theme.DarkNavigationBar
import com.picmorrow.ui.theme.DarkNavigationBarBorder
import com.picmorrow.ui.theme.LightBackground
import com.picmorrow.ui.theme.LightInactiveNavigationItem
import com.picmorrow.ui.theme.LightNavigationBar
import com.picmorrow.ui.theme.LightNavigationBarBorder
import com.picmorrow.ui.theme.LightSelectedChipBorder
import com.picmorrow.ui.theme.LightSelectedChipContent

internal data class HomeColors(
    val background: Color,
    val taskList: PhotoTaskListColors,
    val selectedChipBackground: Color,
    val selectedChipBorder: Color,
    val selectedChipContent: Color,
    val bottomBarBackground: Color,
    val bottomBarBorder: Color,
    val inactiveBottomItem: Color,
)

internal fun homeColors(darkTheme: Boolean): HomeColors =
    if (darkTheme) {
        HomeColors(
            background = DarkBackground,
            taskList = photoTaskListColors(darkTheme = true),
            selectedChipBackground = Color.Transparent,
            selectedChipBorder = DarkCoral,
            selectedChipContent = DarkCoral,
            bottomBarBackground = DarkNavigationBar,
            bottomBarBorder = DarkNavigationBarBorder,
            inactiveBottomItem = DarkInactiveNavigationItem,
        )
    } else {
        HomeColors(
            background = LightBackground,
            taskList = photoTaskListColors(darkTheme = false),
            selectedChipBackground = Color.Transparent,
            selectedChipBorder = LightSelectedChipBorder,
            selectedChipContent = LightSelectedChipContent,
            bottomBarBackground = LightNavigationBar,
            bottomBarBorder = LightNavigationBarBorder,
            inactiveBottomItem = LightInactiveNavigationItem,
        )
    }
