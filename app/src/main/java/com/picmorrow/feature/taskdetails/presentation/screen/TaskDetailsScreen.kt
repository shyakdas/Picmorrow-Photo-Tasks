@file:Suppress("MagicNumber", "LongMethod")

package com.picmorrow.feature.taskdetails.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.picmorrow.R
import com.picmorrow.core.common.AppDateFormat
import com.picmorrow.core.common.formatDate
import com.picmorrow.feature.phototasks.domain.model.PhotoTask
import com.picmorrow.feature.phototasks.presentation.common.components.CapturedPhotoImage
import com.picmorrow.feature.phototasks.presentation.common.components.CategoryBadge
import com.picmorrow.feature.phototasks.presentation.common.model.PhotoTaskCategory
import com.picmorrow.feature.taskdetails.presentation.components.TaskCompletionBottomSheet
import com.picmorrow.feature.taskdetails.presentation.components.TaskDetailsTopBar
import com.picmorrow.feature.taskdetails.presentation.components.TaskNotFound
import com.picmorrow.feature.taskdetails.presentation.model.TaskDetailsUiState
import com.picmorrow.ui.theme.DarkBorder
import com.picmorrow.ui.theme.DarkSecondaryText
import com.picmorrow.ui.theme.DarkSurfaceRaised
import com.picmorrow.ui.theme.LightBorder
import com.picmorrow.ui.theme.LightNavigationBar
import com.picmorrow.ui.theme.LightSecondaryText
import com.picmorrow.ui.theme.PicmorrowTheme
import com.picmorrow.ui.theme.isPicmorrowDarkTheme

@Composable
@Suppress("LongParameterList")
internal fun TaskDetailsScreen(
    uiState: TaskDetailsUiState,
    onBackClick: () -> Unit,
    onSaveToGalleryClick: () -> Unit,
    onDeletePhotoAndTaskClick: () -> Unit,
    onReviewLaterClick: () -> Unit,
    modifier: Modifier = Modifier,
    darkTheme: Boolean = isPicmorrowDarkTheme(),
) {
    var showCompletionSheet by rememberSaveable { mutableStateOf(false) }

    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        when (uiState) {
            TaskDetailsUiState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            TaskDetailsUiState.NotFound -> TaskNotFound(onBackClick)
            TaskDetailsUiState.Deleted -> Unit
            is TaskDetailsUiState.Content -> TaskDetailsContent(
                task = uiState.task,
                isCompleting = uiState.isCompleting,
                onBackClick = onBackClick,
                onDeleteClick = onDeletePhotoAndTaskClick,
                onMarkDoneClick = { showCompletionSheet = true },
                darkTheme = darkTheme,
            )
        }
    }

    val content = uiState as? TaskDetailsUiState.Content
    if (showCompletionSheet && content != null && content.task.completedAtMillis == null) {
        TaskCompletionBottomSheet(
            isProcessing = content.isCompleting,
            darkTheme = darkTheme,
            onDismiss = { showCompletionSheet = false },
            onSaveToGallery = onSaveToGalleryClick,
            onDeletePhotoAndTask = onDeletePhotoAndTaskClick,
            onReviewLater = onReviewLaterClick,
        )
    }
}

@Preview(
    name = "Task Details - Light",
    showBackground = true,
    showSystemUi = true,
    widthDp = TASK_DETAILS_PREVIEW_WIDTH,
    heightDp = TASK_DETAILS_PREVIEW_HEIGHT,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun TaskDetailsScreenLightPreview() {
    PicmorrowTheme(darkTheme = false) {
        TaskDetailsScreen(
            uiState = TaskDetailsUiState.Content(previewTask),
            onBackClick = {},
            onSaveToGalleryClick = {},
            onDeletePhotoAndTaskClick = {},
            onReviewLaterClick = {},
            darkTheme = false,
        )
    }
}

@Preview(
    name = "Task Details - Dark",
    showBackground = true,
    showSystemUi = true,
    widthDp = TASK_DETAILS_PREVIEW_WIDTH,
    heightDp = TASK_DETAILS_PREVIEW_HEIGHT,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun TaskDetailsScreenDarkPreview() {
    PicmorrowTheme(darkTheme = true) {
        TaskDetailsScreen(
            uiState = TaskDetailsUiState.Content(
                previewTask.copy(completedAtMillis = PREVIEW_CAPTURED_AT + ONE_DAY_MILLIS),
            ),
            onBackClick = {},
            onSaveToGalleryClick = {},
            onDeletePhotoAndTaskClick = {},
            onReviewLaterClick = {},
            darkTheme = true,
        )
    }
}

private const val TASK_DETAILS_PREVIEW_WIDTH = 393
private const val TASK_DETAILS_PREVIEW_HEIGHT = 852
private const val ONE_DAY_MILLIS = 86_400_000L
private const val PREVIEW_CAPTURED_AT = 1_757_324_800_000L

private val previewTask = PhotoTask(
    id = 1,
    photoPath = "",
    category = "Parking",
    title = "Car - B2, pillar C14",
    notes = "Near the lift",
    reminderAtMillis = null,
    completedAtMillis = null,
    capturedAtMillis = PREVIEW_CAPTURED_AT,
)

@Composable
@Suppress("LongParameterList")
private fun TaskDetailsContent(
    task: PhotoTask,
    isCompleting: Boolean,
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit,
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
            .navigationBarsPadding(),
    ) {
        TaskDetailsTopBar(
            onBackClick = onBackClick,
            showDeleteAction = !active,
            deleteActionEnabled = !isCompleting,
            onDeleteClick = onDeleteClick,
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
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
                    stringResource(
                        R.string.task_details_captured,
                        formatDate(task.capturedAtMillis, AppDateFormat.DayMonthYear),
                    ),
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
                    stringResource(
                        R.string.task_details_completed,
                        formatDate(it, AppDateFormat.DayMonthYear),
                    ),
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
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
                    .height(48.dp),
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
