package com.picmorrow.feature.camera.presentation.model

import org.junit.Assert.assertEquals
import org.junit.Test

class CameraPermissionStateTest {
    @Test
    fun grantedPermissionTakesPrecedence() {
        assertEquals(
            CameraPermissionState.Granted,
            resolveCameraPermissionState(
                isGranted = true,
                shouldShowRationale = true,
                hasRequestedPermission = true,
            ),
        )
    }

    @Test
    fun denialStatesAreDistinguished() {
        assertEquals(
            CameraPermissionState.Requestable,
            resolveCameraPermissionState(false, false, false),
        )
        assertEquals(
            CameraPermissionState.Rationale,
            resolveCameraPermissionState(false, true, false),
        )
        assertEquals(
            CameraPermissionState.PermanentlyDenied,
            resolveCameraPermissionState(false, false, true),
        )
    }
}
