package com.picmorrow.feature.camera.presentation.screen

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.picmorrow.core.common.hasCameraPermission
import com.picmorrow.feature.camera.presentation.model.CameraCategory

@Composable
internal fun CameraRoute(
    onCloseClick: () -> Unit,
    onPhotoCaptured: (Uri, CameraCategory) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    var hasCameraPermission by rememberSaveable {
        mutableStateOf(context.hasCameraPermission())
    }
    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            hasCameraPermission = isGranted
        }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    if (hasCameraPermission) {
        CameraScreen(
            onCloseClick = onCloseClick,
            onPhotoCaptured = onPhotoCaptured,
            modifier = modifier,
        )
    } else {
        CameraPermissionScreen(
            onCloseClick = onCloseClick,
            onRequestPermissionClick = {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            },
            modifier = modifier,
        )
    }
}
