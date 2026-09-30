package com.picmorrow.feature.phototasks.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.picmorrow.feature.phototasks.presentation.NewPhotoTaskViewModel
import com.picmorrow.feature.phototasks.presentation.common.model.PhotoTaskCategory
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun NewPhotoTaskRoute(
    photoPath: String,
    selectedCategory: PhotoTaskCategory,
    onCancelClick: () -> Unit,
    onRetakeClick: () -> Unit,
    onSaved: () -> Unit,
) {
    val viewModel: NewPhotoTaskViewModel = koinViewModel()
    val saveState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(saveState.isSaved) {
        if (saveState.isSaved) onSaved()
    }

    NewPhotoTaskScreen(
        photoPath = photoPath,
        selectedCategory = selectedCategory,
        onCancelClick = onCancelClick,
        onRetakeClick = onRetakeClick,
        onSaveClick = viewModel::save,
        onFormChanged = viewModel::clearError,
        saveState = saveState,
    )
}
