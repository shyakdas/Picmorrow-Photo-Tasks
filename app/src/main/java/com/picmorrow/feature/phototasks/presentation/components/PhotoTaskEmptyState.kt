@file:Suppress("MagicNumber")

package com.picmorrow.feature.phototasks.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.picmorrow.feature.phototasks.presentation.model.PhotoTaskEmptyStateConfig

@Composable
internal fun PhotoTaskEmptyState(
    config: PhotoTaskEmptyStateConfig,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxWidth()) {
        EmptyIcon(
            iconRes = config.iconRes,
            backgroundColor = config.iconBackgroundColor,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = EMPTY_ICON_CENTER_OFFSET),
        )
        EmptyCopy(
            config = config,
            modifier = Modifier.align(Alignment.Center),
        )
        EmptyActionButton(
            config = config,
            onActionClick = onActionClick,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = EMPTY_BUTTON_CENTER_OFFSET),
        )
    }
}

@Composable
private fun EmptyIcon(
    iconRes: Int,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.size(EMPTY_ICON_BACKGROUND_SIZE),
        shape = CircleShape,
        color = backgroundColor,
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                modifier = Modifier.size(EMPTY_ICON_SIZE),
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
private fun EmptyCopy(
    config: PhotoTaskEmptyStateConfig,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(config.titleRes),
            color = config.primaryTextColor,
            fontSize = EMPTY_TITLE_TEXT_SIZE,
            lineHeight = EMPTY_TITLE_LINE_HEIGHT,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(EMPTY_BODY_TOP_SPACING))

        Text(
            text = stringResource(config.bodyRes),
            color = config.secondaryTextColor,
            fontSize = EMPTY_BODY_TEXT_SIZE,
            lineHeight = EMPTY_BODY_LINE_HEIGHT,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun EmptyActionButton(
    config: PhotoTaskEmptyStateConfig,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onActionClick,
        modifier = modifier.height(EMPTY_BUTTON_HEIGHT),
        shape = RoundedCornerShape(percent = BUTTON_CORNER_PERCENT),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        contentPadding = ButtonDefaults.ContentPadding,
    ) {
        Icon(
            painter = painterResource(config.iconRes),
            contentDescription = null,
            modifier = Modifier.size(BUTTON_ICON_SIZE),
        )

        Spacer(modifier = Modifier.width(BUTTON_ICON_SPACING))

        Text(
            text = stringResource(config.actionRes),
            fontSize = BUTTON_TEXT_SIZE,
            lineHeight = BUTTON_LINE_HEIGHT,
            fontWeight = FontWeight.Bold,
        )
    }
}

private val EMPTY_ICON_BACKGROUND_SIZE = 100.dp
private val EMPTY_ICON_SIZE = 36.dp
private val EMPTY_BODY_TOP_SPACING = 14.dp
private val EMPTY_ICON_CENTER_OFFSET = (-118).dp
private val EMPTY_BUTTON_CENTER_OFFSET = 116.dp
private val EMPTY_BUTTON_HEIGHT = 46.dp
private val BUTTON_ICON_SIZE = 18.dp
private val BUTTON_ICON_SPACING = 6.dp
private val EMPTY_TITLE_TEXT_SIZE = 24.sp
private val EMPTY_TITLE_LINE_HEIGHT = 29.sp
private val EMPTY_BODY_TEXT_SIZE = 15.sp
private val EMPTY_BODY_LINE_HEIGHT = 22.5.sp
private val BUTTON_TEXT_SIZE = 15.sp
private val BUTTON_LINE_HEIGHT = 20.sp
private const val BUTTON_CORNER_PERCENT = 50
