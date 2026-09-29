package com.picmorrow.feature.settings.presentation.screen

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.picmorrow.MainActivity
import com.picmorrow.R
import com.picmorrow.core.common.areNotificationsAllowed
import com.picmorrow.feature.settings.data.local.ThemePreferencesDataSource
import com.picmorrow.feature.settings.data.repository.ThemePreferenceRepositoryImpl
import com.picmorrow.feature.settings.domain.usecase.ObserveThemeModeUseCase
import com.picmorrow.feature.settings.domain.usecase.SetThemeModeUseCase
import com.picmorrow.feature.settings.presentation.ThemeSettingsViewModel
import com.picmorrow.navigation.AppDestination

@Composable
internal fun SettingsRoute(onBackClick: () -> Unit) {
    val context = LocalContext.current
    val applicationContext = context.applicationContext
    val factory = remember(applicationContext) {
        val repository = ThemePreferenceRepositoryImpl(ThemePreferencesDataSource(applicationContext))
        ThemeSettingsViewModel.Factory(
            ObserveThemeModeUseCase(repository),
            SetThemeModeUseCase(repository),
        )
    }
    val viewModel: ThemeSettingsViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var notificationsAllowed by remember { mutableStateOf(applicationContext.areNotificationsAllowed()) }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        notificationsAllowed = applicationContext.areNotificationsAllowed()
    }

    SettingsScreen(
        uiState = uiState,
        notificationsAllowed = notificationsAllowed,
        onBackClick = onBackClick,
        onThemeModeSelected = viewModel::setThemeMode,
        onNotificationClick = { applicationContext.openNotificationSettings() },
        onAddCameraShortcutClick = { applicationContext.requestCameraShortcut() },
    )
}

private fun Context.openNotificationSettings() {
    startActivity(
        Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
            .putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
    )
}

private fun Context.requestCameraShortcut() {
    val shortcutManager = getSystemService(ShortcutManager::class.java)
    if (!shortcutManager.isRequestPinShortcutSupported) return

    val cameraIntent = Intent(this, MainActivity::class.java).apply {
        action = Intent.ACTION_VIEW
        putExtra(MainActivity.EXTRA_START_DESTINATION, AppDestination.Camera.route)
    }
    val shortcut = ShortcutInfo.Builder(this, CAMERA_SHORTCUT_ID)
        .setShortLabel(getString(R.string.settings_camera_shortcut_label))
        .setIcon(Icon.createWithResource(this, R.mipmap.ic_launcher))
        .setIntent(cameraIntent)
        .build()
    shortcutManager.requestPinShortcut(shortcut, null)
}

private const val CAMERA_SHORTCUT_ID = "picmorrow-camera"
