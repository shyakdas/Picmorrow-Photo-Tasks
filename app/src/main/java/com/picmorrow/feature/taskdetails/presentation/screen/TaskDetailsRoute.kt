package com.picmorrow.feature.taskdetails.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.picmorrow.core.data.local.AppDatabase
import com.picmorrow.feature.phototasks.data.repository.PhotoTaskRepositoryImpl
import com.picmorrow.feature.phototasks.data.reminder.AndroidTaskReminderScheduler
import com.picmorrow.feature.taskdetails.data.AndroidTaskPhotoStorage
import com.picmorrow.feature.taskdetails.presentation.TaskDetailsViewModel
import com.picmorrow.feature.taskdetails.presentation.model.TaskDetailsUiState

@Composable
internal fun TaskDetailsRoute(
    taskId: Long,
    onBackClick: () -> Unit,
) {
    val context = LocalContext.current.applicationContext
    val factory = remember(context, taskId) {
        val repository = PhotoTaskRepositoryImpl(
            AppDatabase.getInstance(context).photoTaskDao(),
            AndroidTaskReminderScheduler(context),
        )
        viewModelFactory {
            initializer { TaskDetailsViewModel(taskId, repository, AndroidTaskPhotoStorage(context)) }
        }
    }
    val viewModel: TaskDetailsViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        if (uiState === TaskDetailsUiState.Deleted) {
            onBackClick()
        }
    }

    TaskDetailsScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onSaveToGalleryClick = viewModel::saveToGalleryAndComplete,
        onDeletePhotoAndTaskClick = viewModel::deletePhotoAndTask,
        onReviewLaterClick = viewModel::completeForReview,
    )
}
