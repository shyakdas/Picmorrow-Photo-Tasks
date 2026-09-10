@file:Suppress("MagicNumber")

package com.picmorrow.feature.phototasks.presentation.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.picmorrow.R
import com.picmorrow.feature.phototasks.presentation.model.HomeContentState
import com.picmorrow.feature.phototasks.presentation.model.HomeTab
import com.picmorrow.ui.theme.PicmorrowTheme

@Composable
internal fun HomeScreen(
    onTakePhotoClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
    darkTheme: Boolean = isSystemInDarkTheme(),
    contentState: HomeContentState = HomeContentState(),
) {
    var selectedTab by rememberSaveable { mutableStateOf(HomeTab.Active) }
    val colors = homeColors(darkTheme)

    Surface(
        modifier = modifier.fillMaxSize(),
        color = colors.background,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            HomeContent(
                selectedTab = selectedTab,
                colors = colors,
                contentState = contentState,
                onTakePhotoClick = onTakePhotoClick,
                onSettingsClick = onSettingsClick,
            )

            HomeCameraFab(
                onTakePhotoClick = onTakePhotoClick,
                modifier = Modifier.align(Alignment.BottomEnd),
            )

            HomeBottomBar(
                selectedTab = selectedTab,
                colors = colors,
                onTabSelected = { selectedTab = it },
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }
}

@Composable
private fun HomeContent(
    selectedTab: HomeTab,
    colors: HomeColors,
    contentState: HomeContentState,
    onTakePhotoClick: () -> Unit,
    onSettingsClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = SCREEN_HORIZONTAL_PADDING),
    ) {
        HomeTopBar(
            iconTint = colors.primaryText,
            onSettingsClick = onSettingsClick,
        )

        Spacer(modifier = Modifier.height(FILTER_TOP_SPACING))

        ActiveFilters(colors = colors)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(bottom = BOTTOM_BAR_HEIGHT),
        ) {
            when (selectedTab) {
                HomeTab.Active -> {
                    ActiveScreen(
                        hasPhotoTasks = contentState.hasActivePhotoTasks,
                        colors = colors,
                        onTakePhotoClick = onTakePhotoClick,
                        modifier = Modifier.fillMaxSize(),
                    )
                }

                HomeTab.Completed -> {
                    CompletedScreen(
                        hasCompletedPhotoTasks = contentState.hasCompletedPhotoTasks,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeTopBar(
    iconTint: Color,
    onSettingsClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = TOP_BAR_TOP_PADDING),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.active_title),
            color = iconTint,
            fontSize = SCREEN_TITLE_TEXT_SIZE,
            lineHeight = SCREEN_TITLE_LINE_HEIGHT,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = onSettingsClick, modifier = Modifier.size(SETTINGS_BUTTON_SIZE)) {
            Icon(
                painter = painterResource(R.drawable.ic_settings_gear),
                contentDescription = stringResource(R.string.active_settings_content_description),
                modifier = Modifier.size(SETTINGS_ICON_SIZE),
                tint = iconTint,
            )
        }
    }
}

@Composable
private fun ActiveFilters(colors: HomeColors) {
    Row(horizontalArrangement = Arrangement.spacedBy(FILTER_SPACING)) {
        FilterChip(
            label = stringResource(R.string.filter_all),
            iconRes = R.drawable.ic_filter_grid,
            selected = true,
            colors = colors,
        )
        FilterChip(
            label = stringResource(R.string.filter_parking),
            iconRes = R.drawable.ic_filter_car,
            selected = false,
            colors = colors,
        )
        FilterChip(
            label = stringResource(R.string.filter_buy),
            iconRes = R.drawable.ic_filter_buy,
            selected = false,
            colors = colors,
        )
    }
}

@Composable
private fun FilterChip(
    label: String,
    @DrawableRes iconRes: Int,
    selected: Boolean,
    colors: HomeColors,
) {
    val contentColor = if (selected) colors.selectedChipContent else colors.unselectedChipContent

    Surface(
        shape = RoundedCornerShape(CHIP_CORNER_RADIUS),
        color = if (selected) colors.selectedChipBackground else colors.unselectedChipBackground,
        border = BorderStroke(CHIP_BORDER_WIDTH, if (selected) colors.selectedChipBorder else colors.chipBorder),
    ) {
        Row(
            modifier = Modifier
                .height(CHIP_HEIGHT)
                .padding(horizontal = CHIP_HORIZONTAL_PADDING),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                modifier = Modifier.size(CHIP_ICON_SIZE),
                tint = contentColor,
            )

            Spacer(modifier = Modifier.width(CHIP_ICON_SPACING))

            Text(
                text = label,
                color = contentColor,
                fontSize = CHIP_TEXT_SIZE,
                lineHeight = CHIP_LINE_HEIGHT,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Composable
private fun HomeCameraFab(
    onTakePhotoClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FloatingActionButton(
        onClick = onTakePhotoClick,
        modifier = modifier
            .navigationBarsPadding()
            .padding(
                end = FAB_END_PADDING,
                bottom = BOTTOM_BAR_HEIGHT + FAB_BOTTOM_SPACING,
            )
            .size(FAB_SIZE),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        shape = RoundedCornerShape(FAB_CORNER_RADIUS),
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_fab_aperture),
            contentDescription = stringResource(R.string.active_camera_content_description),
            modifier = Modifier.size(FAB_ICON_SIZE),
        )
    }
}

@Composable
private fun HomeBottomBar(
    selectedTab: HomeTab,
    colors: HomeColors,
    onTabSelected: (HomeTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = colors.bottomBarBackground,
        border = BorderStroke(BOTTOM_BAR_BORDER_WIDTH, colors.bottomBarBorder),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .height(BOTTOM_BAR_HEIGHT),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BottomBarItem(
                item = HomeBottomBarItem(
                    iconRes = R.drawable.ic_filter_grid,
                    label = stringResource(R.string.active_title),
                ),
                selected = selectedTab == HomeTab.Active,
                onClick = { onTabSelected(HomeTab.Active) },
                colors = colors,
                modifier = Modifier.weight(1f),
            )
            BottomBarItem(
                item = HomeBottomBarItem(
                    iconRes = R.drawable.ic_bottom_check,
                    label = stringResource(R.string.active_completed_tab),
                ),
                selected = selectedTab == HomeTab.Completed,
                onClick = { onTabSelected(HomeTab.Completed) },
                colors = colors,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun BottomBarItem(
    item: HomeBottomBarItem,
    selected: Boolean,
    onClick: () -> Unit,
    colors: HomeColors,
    modifier: Modifier = Modifier,
) {
    val contentColor = if (selected) MaterialTheme.colorScheme.primary else colors.inactiveBottomItem
    val labelWeight = if (selected) FontWeight.Bold else FontWeight.Medium

    Surface(
        modifier = modifier.height(BOTTOM_BAR_HEIGHT),
        color = Color.Transparent,
        onClick = onClick,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                painter = painterResource(item.iconRes),
                contentDescription = null,
                modifier = Modifier.size(BOTTOM_BAR_ICON_SIZE),
                tint = contentColor,
            )

            Spacer(modifier = Modifier.height(BOTTOM_BAR_LABEL_TOP_SPACING))

            Text(
                text = item.label,
                color = contentColor,
                fontSize = BOTTOM_BAR_LABEL_TEXT_SIZE,
                lineHeight = BOTTOM_BAR_LABEL_LINE_HEIGHT,
                fontWeight = labelWeight,
            )
        }
    }
}

private data class HomeBottomBarItem(
    @param:DrawableRes val iconRes: Int,
    val label: String,
)

@Preview(
    name = "Home Active Empty - Light",
    showBackground = true,
    showSystemUi = true,
    widthDp = PHONE_PREVIEW_WIDTH,
    heightDp = PHONE_PREVIEW_HEIGHT,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun HomeActiveEmptyLightPreview() {
    PicmorrowTheme(darkTheme = false) {
        HomeScreen(
            darkTheme = false,
            onTakePhotoClick = {},
            onSettingsClick = {},
        )
    }
}

@Preview(
    name = "Home Active Empty - Dark",
    showBackground = true,
    showSystemUi = true,
    widthDp = PHONE_PREVIEW_WIDTH,
    heightDp = PHONE_PREVIEW_HEIGHT,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun HomeActiveEmptyDarkPreview() {
    PicmorrowTheme(darkTheme = true) {
        HomeScreen(
            darkTheme = true,
            onTakePhotoClick = {},
            onSettingsClick = {},
        )
    }
}

private val SCREEN_HORIZONTAL_PADDING = 20.dp
private val TOP_BAR_TOP_PADDING = 20.dp
private val FILTER_TOP_SPACING = 20.dp
private val FILTER_SPACING = 8.dp
private val CHIP_HEIGHT = 40.dp
private val CHIP_CORNER_RADIUS = 20.dp
private val CHIP_BORDER_WIDTH = 1.dp
private val CHIP_HORIZONTAL_PADDING = 14.dp
private val CHIP_ICON_SIZE = 14.dp
private val CHIP_ICON_SPACING = 6.dp
private val BOTTOM_BAR_HEIGHT = 72.dp
private val BOTTOM_BAR_BORDER_WIDTH = 1.dp
private val BOTTOM_BAR_ICON_SIZE = 22.dp
private val BOTTOM_BAR_LABEL_TOP_SPACING = 3.dp
private val FAB_SIZE = 56.dp
private val FAB_ICON_SIZE = 28.dp
private val FAB_CORNER_RADIUS = 16.dp
private val FAB_END_PADDING = 22.dp
private val FAB_BOTTOM_SPACING = 20.dp
private val SETTINGS_BUTTON_SIZE = 44.dp
private val SETTINGS_ICON_SIZE = 24.dp
private val SCREEN_TITLE_TEXT_SIZE = 28.sp
private val SCREEN_TITLE_LINE_HEIGHT = 34.sp
private val CHIP_TEXT_SIZE = 14.sp
private val CHIP_LINE_HEIGHT = 20.sp
private val BOTTOM_BAR_LABEL_TEXT_SIZE = 11.sp
private val BOTTOM_BAR_LABEL_LINE_HEIGHT = 16.sp
private const val PHONE_PREVIEW_WIDTH = 411
private const val PHONE_PREVIEW_HEIGHT = 891
