package com.picmorrow.feature.phototasks.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.picmorrow.core.data.local.AppDatabase
import com.picmorrow.feature.phototasks.data.repository.PhotoTaskRepositoryImpl
import com.picmorrow.feature.phototasks.presentation.TaskDetailsViewModel

@Composable
internal fun TaskDetailsRoute(
    taskId: Long,
    onBackClick: () -> Unit,
    onEditClick: (Long) -> Unit,
) {
    val context = LocalContext.current.applicationContext
    val factory = remember(context, taskId) {
        val repository = PhotoTaskRepositoryImpl(AppDatabase.getInstance(context).photoTaskDao())
        TaskDetailsViewModel.Factory(taskId, repository)
    }
    val viewModel: TaskDetailsViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TaskDetailsScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onEditClick = { onEditClick(taskId) },
        onMarkDoneClick = viewModel::complete,
    )
}
