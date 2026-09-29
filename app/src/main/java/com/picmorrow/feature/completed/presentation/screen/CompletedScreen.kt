@file:Suppress("MagicNumber", "LongMethod")

package com.picmorrow.feature.completed.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
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
import com.picmorrow.R
import com.picmorrow.feature.phototasks.domain.model.PhotoTask
import com.picmorrow.feature.phototasks.presentation.common.components.PhotoTaskList
import com.picmorrow.feature.phototasks.presentation.common.model.PhotoTaskListColors

@Composable
@Suppress("LongParameterList")
internal fun CompletedScreen(
    tasks: List<PhotoTask>,
    colors: PhotoTaskListColors,
    darkTheme: Boolean,
    onTaskClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (tasks.isNotEmpty()) {
        PhotoTaskList(
            tasks = tasks,
            colors = colors,
            darkTheme = darkTheme,
            onCompleteClick = null,
            onTaskClick = onTaskClick,
            modifier = modifier,
            bottomPadding = 16.dp,
        )
    } else {
        CompletedEmptyState(
            colors = colors,
            darkTheme = darkTheme,
            modifier = modifier.fillMaxSize(),
        )
    }
}

@Composable
private fun CompletedEmptyState(
    colors: PhotoTaskListColors,
    darkTheme: Boolean,
    modifier: Modifier = Modifier,
) {
    val iconBackground = if (darkTheme) DarkCompletedIconBackground else LightCompletedIconBackground
    val iconColor = if (darkTheme) DarkCompletedIcon else LightCompletedIcon

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-40).dp)
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Surface(
                modifier = Modifier.size(100.dp),
                shape = CircleShape,
                color = iconBackground,
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        painter = painterResource(R.drawable.ic_bottom_check),
                        contentDescription = null,
                        modifier = Modifier.size(42.dp),
                        tint = iconColor,
                    )
                }
            }

            Spacer(Modifier.height(28.dp))
            Text(
                text = stringResource(R.string.completed_empty_title),
                color = colors.primaryText,
                fontSize = 24.sp,
                lineHeight = 29.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(14.dp))
            Text(
                text = stringResource(R.string.completed_empty_body),
                color = colors.secondaryText,
                fontSize = 14.sp,
                lineHeight = 22.sp,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(64.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = colors.secondaryText,
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = stringResource(R.string.completed_empty_storage_note),
                    color = colors.secondaryText,
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                )
            }
        }
    }
}

private val LightCompletedIconBackground = Color(0xFFDFF4F3)
private val LightCompletedIcon = Color(0xFF0D817F)
private val DarkCompletedIconBackground = Color(0xFF224344)
private val DarkCompletedIcon = Color(0xFF83D5D3)
