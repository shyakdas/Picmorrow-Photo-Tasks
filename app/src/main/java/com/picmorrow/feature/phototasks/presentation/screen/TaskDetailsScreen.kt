@file:Suppress("MagicNumber", "LongMethod")

package com.picmorrow.feature.phototasks.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.picmorrow.R
import com.picmorrow.feature.phototasks.domain.model.PhotoTask
import com.picmorrow.feature.phototasks.presentation.common.components.CapturedPhotoImage
import com.picmorrow.feature.phototasks.presentation.common.components.CategoryBadge
import com.picmorrow.feature.phototasks.presentation.common.model.PhotoTaskCategory
import com.picmorrow.feature.phototasks.presentation.model.TaskDetailsUiState
import com.picmorrow.ui.theme.DarkBorder
import com.picmorrow.ui.theme.DarkSecondaryText
import com.picmorrow.ui.theme.DarkSurfaceRaised
import com.picmorrow.ui.theme.LightBorder
import com.picmorrow.ui.theme.LightSecondaryText
import com.picmorrow.ui.theme.LightNavigationBar
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
@Suppress("LongParameterList")
internal fun TaskDetailsScreen(
    uiState: TaskDetailsUiState,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onMarkDoneClick: () -> Unit,
    modifier: Modifier = Modifier,
    darkTheme: Boolean = isSystemInDarkTheme(),
) {
    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        when (uiState) {
            TaskDetailsUiState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            TaskDetailsUiState.NotFound -> TaskNotFound(onBackClick)
            is TaskDetailsUiState.Content -> TaskDetailsContent(
                task = uiState.task,
                isCompleting = uiState.isCompleting,
                onBackClick = onBackClick,
                onEditClick = onEditClick,
                onMarkDoneClick = onMarkDoneClick,
                darkTheme = darkTheme,
            )
        }
    }
}

@Composable
@Suppress("LongParameterList")
private fun TaskDetailsContent(
    task: PhotoTask,
    isCompleting: Boolean,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onMarkDoneClick: () -> Unit,
    darkTheme: Boolean,
) {
    val active = task.completedAtMillis == null
    val secondary = if (darkTheme) DarkSecondaryText else LightSecondaryText
    val cardBackground = if (darkTheme) DarkSurfaceRaised else LightNavigationBar
    val border = if (darkTheme) DarkBorder else LightBorder

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp),
    ) {
        TaskDetailsTopBar(active, onBackClick, onEditClick)
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
        ) {
            CapturedPhotoImage(
                photoPath = task.photoPath,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.1f)
                    .clip(RoundedCornerShape(16.dp)),
            )
            Spacer(Modifier.height(20.dp))
            PhotoTaskCategory.entries.firstOrNull { it.name == task.category }?.let {
                CategoryBadge(category = it, darkTheme = darkTheme)
                Spacer(Modifier.height(18.dp))
            }
            Text(task.title, fontSize = 28.sp, lineHeight = 34.sp, fontWeight = FontWeight.Bold)
            if (task.capturedAtMillis > 0) {
                Spacer(Modifier.height(8.dp))
                Text(
                    stringResource(R.string.task_details_captured, formatCapturedDate(task.capturedAtMillis)),
                    color = secondary,
                    fontSize = 15.sp,
                )
            }
            if (task.notes.isNotBlank()) {
                Spacer(Modifier.height(20.dp))
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    color = cardBackground,
                    border = BorderStroke(1.dp, border),
                ) {
                    Column(Modifier.padding(18.dp)) {
                        Text(
                            stringResource(R.string.task_details_notes),
                            color = secondary,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(task.notes, fontSize = 17.sp, lineHeight = 24.sp)
                    }
                }
            }
            task.completedAtMillis?.let {
                Spacer(Modifier.height(16.dp))
                Text(
                    stringResource(R.string.task_details_completed, formatCapturedDate(it)),
                    color = secondary,
                    fontSize = 15.sp,
                )
            }
            Spacer(Modifier.height(24.dp))
        }
        if (active) {
            Button(
                onClick = onMarkDoneClick,
                enabled = !isCompleting,
                modifier = Modifier.fillMaxWidth().height(58.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            ) {
                Text(
                    if (isCompleting) stringResource(R.string.task_details_completing)
                    else stringResource(R.string.task_details_mark_done),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(Modifier.height(18.dp))
        }
    }
}

@Composable
private fun TaskDetailsTopBar(active: Boolean, onBackClick: () -> Unit, onEditClick: () -> Unit) {
    var menuExpanded by rememberSaveable { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth().height(72.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        IconButton(onClick = onBackClick) {
            Icon(painterResource(R.drawable.ic_arrow_back), stringResource(R.string.task_details_back))
        }
        Text(stringResource(R.string.task_details_title), fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Box {
            if (active) {
                IconButton(onClick = { menuExpanded = true }) {
                    Icon(painterResource(R.drawable.ic_more_horizontal), stringResource(R.string.task_details_more))
                }
                DropdownMenu(expanded = menuExpanded, onDismissRequest = { menuExpanded = false }) {
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.task_details_edit)) },
                        leadingIcon = { Icon(painterResource(R.drawable.ic_edit), contentDescription = null) },
                        onClick = {
                            menuExpanded = false
                            onEditClick()
                        },
                    )
                }
            } else {
                Spacer(Modifier.size(48.dp))
            }
        }
    }
}

@Composable
private fun TaskNotFound(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().statusBarsPadding().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TaskDetailsTopBar(active = false, onBackClick = onBackClick, onEditClick = {})
        Spacer(Modifier.weight(1f))
        Text(stringResource(R.string.task_details_not_found), fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.weight(1f))
    }
}

internal fun formatCapturedDate(
    timestamp: Long,
    zoneId: ZoneId = ZoneId.systemDefault(),
    locale: Locale = Locale.getDefault(),
): String = DateTimeFormatter.ofPattern("d MMM yyyy", locale)
    .format(Instant.ofEpochMilli(timestamp).atZone(zoneId))
