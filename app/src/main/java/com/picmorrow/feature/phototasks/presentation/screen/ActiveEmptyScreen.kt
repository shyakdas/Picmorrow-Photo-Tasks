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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.picmorrow.R

@Composable
fun ActiveEmptyScreen(
    onTakePhotoClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
    darkTheme: Boolean = isSystemInDarkTheme(),
) {
    val colors = activeEmptyColors(darkTheme)

    Surface(
        modifier = modifier.fillMaxSize(),
        color = colors.background,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(horizontal = SCREEN_HORIZONTAL_PADDING),
            ) {
                ActiveTopBar(
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
                    ActiveEmptyState(
                        colors = colors,
                        onTakePhotoClick = onTakePhotoClick,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }

            FloatingActionButton(
                onClick = onTakePhotoClick,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
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

            ActiveBottomBar(
                colors = colors,
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }
}

@Composable
private fun ActiveTopBar(
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
private fun ActiveFilters(colors: ActiveEmptyColors) {
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
    colors: ActiveEmptyColors,
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
private fun ActiveEmptyState(
    colors: ActiveEmptyColors,
    onTakePhotoClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxWidth()) {
        EmptyCameraIcon(
            colors = colors,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = EMPTY_CAMERA_CENTER_OFFSET),
        )
        EmptyCopy(
            colors = colors,
            modifier = Modifier.align(Alignment.Center),
        )
        EmptyTakePhotoButton(
            onTakePhotoClick = onTakePhotoClick,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = EMPTY_BUTTON_CENTER_OFFSET),
        )
    }
}

@Composable
private fun EmptyCameraIcon(
    colors: ActiveEmptyColors,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.size(EMPTY_ICON_BACKGROUND_SIZE),
        shape = CircleShape,
        color = colors.emptyIconBackground,
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                painter = painterResource(R.drawable.ic_empty_camera),
                contentDescription = null,
                modifier = Modifier.size(EMPTY_CAMERA_ICON_SIZE),
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
private fun EmptyCopy(
    colors: ActiveEmptyColors,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.active_empty_title),
            color = colors.primaryText,
            fontSize = EMPTY_TITLE_TEXT_SIZE,
            lineHeight = EMPTY_TITLE_LINE_HEIGHT,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(EMPTY_BODY_TOP_SPACING))

        Text(
            text = stringResource(R.string.active_empty_body),
            color = colors.secondaryText,
            fontSize = EMPTY_BODY_TEXT_SIZE,
            lineHeight = EMPTY_BODY_LINE_HEIGHT,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun EmptyTakePhotoButton(
    onTakePhotoClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onTakePhotoClick,
        modifier = modifier.height(EMPTY_BUTTON_HEIGHT),
        shape = RoundedCornerShape(percent = BUTTON_CORNER_PERCENT),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        contentPadding = ButtonDefaults.ContentPadding,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_empty_camera),
            contentDescription = null,
            modifier = Modifier.size(BUTTON_ICON_SIZE),
        )

        Spacer(modifier = Modifier.width(BUTTON_ICON_SPACING))

        Text(
            text = stringResource(R.string.active_take_photo),
            fontSize = BUTTON_TEXT_SIZE,
            lineHeight = BUTTON_LINE_HEIGHT,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun ActiveBottomBar(
    colors: ActiveEmptyColors,
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
                iconRes = R.drawable.ic_filter_grid,
                label = stringResource(R.string.active_title),
                selected = true,
                colors = colors,
                modifier = Modifier.weight(1f),
            )
            BottomBarItem(
                iconRes = R.drawable.ic_bottom_check,
                label = stringResource(R.string.active_completed_tab),
                selected = false,
                colors = colors,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun BottomBarItem(
    @DrawableRes iconRes: Int,
    label: String,
    selected: Boolean,
    colors: ActiveEmptyColors,
    modifier: Modifier = Modifier,
) {
    val contentColor = if (selected) MaterialTheme.colorScheme.primary else colors.inactiveBottomItem
    val labelWeight = if (selected) FontWeight.Bold else FontWeight.Medium

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier.size(BOTTOM_BAR_ICON_SIZE),
            tint = contentColor,
        )

        Spacer(modifier = Modifier.height(BOTTOM_BAR_LABEL_TOP_SPACING))

        Text(
            text = label,
            color = contentColor,
            fontSize = BOTTOM_BAR_LABEL_TEXT_SIZE,
            lineHeight = BOTTOM_BAR_LABEL_LINE_HEIGHT,
            fontWeight = labelWeight,
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
private val EMPTY_ICON_BACKGROUND_SIZE = 100.dp
private val EMPTY_CAMERA_ICON_SIZE = 36.dp
private val EMPTY_BODY_TOP_SPACING = 14.dp
private val EMPTY_CAMERA_CENTER_OFFSET = (-118).dp
private val EMPTY_BUTTON_CENTER_OFFSET = 116.dp
private val EMPTY_BUTTON_HEIGHT = 46.dp
private val BUTTON_ICON_SIZE = 18.dp
private val BUTTON_ICON_SPACING = 6.dp
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
private val EMPTY_TITLE_TEXT_SIZE = 24.sp
private val EMPTY_TITLE_LINE_HEIGHT = 29.sp
private val EMPTY_BODY_TEXT_SIZE = 15.sp
private val EMPTY_BODY_LINE_HEIGHT = 22.5.sp
private val BUTTON_TEXT_SIZE = 15.sp
private val BUTTON_LINE_HEIGHT = 20.sp
private val BOTTOM_BAR_LABEL_TEXT_SIZE = 11.sp
private val BOTTOM_BAR_LABEL_LINE_HEIGHT = 16.sp
private const val BUTTON_CORNER_PERCENT = 50
