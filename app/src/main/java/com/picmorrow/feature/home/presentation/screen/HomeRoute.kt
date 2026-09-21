package com.picmorrow.feature.home.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.picmorrow.core.data.local.AppDatabase
import com.picmorrow.feature.phototasks.data.repository.PhotoTaskRepositoryImpl
import com.picmorrow.feature.home.presentation.HomeViewModel

@Composable
internal fun HomeRoute(onTakePhotoClick: () -> Unit, onSettingsClick: () -> Unit) {
    val context = LocalContext.current.applicationContext
    val factory = remember(context) {
        HomeViewModel.Factory(PhotoTaskRepositoryImpl(AppDatabase.getInstance(context).photoTaskDao()))
    }
    val viewModel: HomeViewModel = viewModel(factory = factory)
    val contentState by viewModel.contentState.collectAsStateWithLifecycle()

    HomeScreen(
        onTakePhotoClick = onTakePhotoClick,
        onSettingsClick = onSettingsClick,
        onCompleteClick = viewModel::complete,
        contentState = contentState,
    )
}
