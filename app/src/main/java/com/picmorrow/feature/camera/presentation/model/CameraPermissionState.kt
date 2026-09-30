package com.picmorrow.feature.camera.presentation.model

internal enum class CameraPermissionState {
    Granted,
    Requestable,
    Rationale,
    PermanentlyDenied,
}

internal fun resolveCameraPermissionState(
    isGranted: Boolean,
    shouldShowRationale: Boolean,
    hasRequestedPermission: Boolean,
): CameraPermissionState = when {
    isGranted -> CameraPermissionState.Granted
    shouldShowRationale -> CameraPermissionState.Rationale
    hasRequestedPermission -> CameraPermissionState.PermanentlyDenied
    else -> CameraPermissionState.Requestable
}
