package com.picmorrow.feature.home.presentation.screen

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.picmorrow.core.common.hasNotificationPermission
import com.picmorrow.core.data.local.AppDatabase
import com.picmorrow.feature.home.presentation.HomeViewModel
import com.picmorrow.feature.phototasks.data.repository.PhotoTaskRepositoryImpl
import com.picmorrow.feature.phototasks.data.reminder.AndroidTaskReminderScheduler

@Composable
internal fun HomeRoute(
    onTakePhotoClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onTaskClick: (Long) -> Unit,
) {
    val context = LocalContext.current
    val applicationContext = context.applicationContext
    val factory = remember(applicationContext) {
        HomeViewModel.Factory(
            PhotoTaskRepositoryImpl(
                AppDatabase.getInstance(applicationContext).photoTaskDao(),
                AndroidTaskReminderScheduler(applicationContext),
            ),
        )
    }
    val viewModel: HomeViewModel = viewModel(factory = factory)
    val contentState by viewModel.contentState.collectAsStateWithLifecycle()
    var permissionRequested by rememberSaveable { mutableStateOf(false) }
    val notificationPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {}

    LaunchedEffect(Unit) {
        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            !context.hasNotificationPermission() &&
            !permissionRequested
        ) {
            permissionRequested = true
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    HomeScreen(
        onTakePhotoClick = onTakePhotoClick,
        onSettingsClick = onSettingsClick,
        onCompleteClick = viewModel::complete,
        onTaskClick = onTaskClick,
        contentState = contentState,
    )
}
