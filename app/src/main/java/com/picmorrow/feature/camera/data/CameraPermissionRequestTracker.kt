package com.picmorrow.feature.camera.data

import android.content.Context
import androidx.core.content.edit

internal class CameraPermissionRequestTracker(context: Context) {
    private val preferences = context.applicationContext.getSharedPreferences(
        PreferencesName,
        Context.MODE_PRIVATE,
    )

    fun hasRequestedPermission(): Boolean = preferences.getBoolean(HasRequestedKey, false)

    fun markPermissionRequested() {
        preferences.edit { putBoolean(HasRequestedKey, true) }
    }

    private companion object {
        const val PreferencesName = "camera_permission"
        const val HasRequestedKey = "has_requested"
    }
}
