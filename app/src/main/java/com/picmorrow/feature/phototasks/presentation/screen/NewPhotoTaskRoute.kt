package com.picmorrow.feature.phototasks.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.picmorrow.core.data.local.AppDatabase
import com.picmorrow.feature.phototasks.presentation.common.model.PhotoTaskCategory
import com.picmorrow.feature.phototasks.data.repository.PhotoTaskRepositoryImpl
import com.picmorrow.feature.phototasks.domain.usecase.SavePhotoTaskUseCase
import com.picmorrow.feature.phototasks.presentation.NewPhotoTaskViewModel

@Composable
internal fun NewPhotoTaskRoute(
    photoPath: String,
    selectedCategory: PhotoTaskCategory,
    onCancelClick: () -> Unit,
    onRetakeClick: () -> Unit,
    onSaved: () -> Unit,
) {
    val context = LocalContext.current.applicationContext
    val factory = remember(context) {
        val dao = AppDatabase.getInstance(context).photoTaskDao()
        NewPhotoTaskViewModel.Factory(SavePhotoTaskUseCase(PhotoTaskRepositoryImpl(dao)))
    }
    val viewModel: NewPhotoTaskViewModel = viewModel(factory = factory)
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
