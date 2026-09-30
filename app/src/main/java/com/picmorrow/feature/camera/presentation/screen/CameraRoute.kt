package com.picmorrow.feature.camera.presentation.screen

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.picmorrow.core.common.hasCameraPermission
import com.picmorrow.feature.camera.data.CameraPermissionRequestTracker
import com.picmorrow.feature.camera.presentation.model.CameraPermissionState
import com.picmorrow.feature.camera.presentation.model.resolveCameraPermissionState
import com.picmorrow.feature.phototasks.presentation.common.model.PhotoTaskCategory
import org.koin.compose.koinInject

@Composable
internal fun CameraRoute(
    onCloseClick: () -> Unit,
    onPhotoCaptured: (Uri, PhotoTaskCategory) -> Unit,
    modifier: Modifier = Modifier,
    permissionRequestTracker: CameraPermissionRequestTracker = koinInject(),
) {
    val context = LocalContext.current
    val activity = LocalActivity.current
    fun currentPermissionState(): CameraPermissionState =
        resolveCameraPermissionState(
            isGranted = context.hasCameraPermission(),
            shouldShowRationale = activity?.let {
                ActivityCompat.shouldShowRequestPermissionRationale(it, Manifest.permission.CAMERA)
            } == true,
            hasRequestedPermission = permissionRequestTracker.hasRequestedPermission(),
        )

    var permissionState by rememberSaveable {
        mutableStateOf(currentPermissionState())
    }
    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            permissionState = if (isGranted) {
                CameraPermissionState.Granted
            } else {
                permissionRequestTracker.markPermissionRequested()
                currentPermissionState()
            }
        }

    LaunchedEffect(Unit) {
        if (permissionState == CameraPermissionState.Requestable) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        permissionState = currentPermissionState()
    }

    if (permissionState == CameraPermissionState.Granted) {
        CameraScreen(
            onCloseClick = onCloseClick,
            onPhotoCaptured = onPhotoCaptured,
            modifier = modifier,
        )
    } else {
        CameraPermissionScreen(
            onCloseClick = onCloseClick,
            showRationale = permissionState == CameraPermissionState.Rationale,
            isPermanentlyDenied = permissionState == CameraPermissionState.PermanentlyDenied,
            onActionClick = {
                if (permissionState == CameraPermissionState.PermanentlyDenied) {
                    context.openAppSettings()
                } else {
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }
            },
            modifier = modifier,
        )
    }
}

private fun android.content.Context.openAppSettings() {
    startActivity(
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null),
        ),
    )
}
