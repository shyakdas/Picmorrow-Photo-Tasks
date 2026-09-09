package com.picmorrow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.picmorrow.feature.settings.data.local.ThemePreferencesDataSource
import com.picmorrow.feature.settings.data.repository.ThemePreferenceRepositoryImpl
import com.picmorrow.feature.settings.domain.model.AppThemeMode
import com.picmorrow.feature.settings.domain.usecase.ObserveThemeModeUseCase
import com.picmorrow.feature.settings.domain.usecase.SetThemeModeUseCase
import com.picmorrow.feature.settings.presentation.ThemeSettingsViewModel
import com.picmorrow.feature.settings.presentation.model.ThemeSettingsUiState
import com.picmorrow.feature.settings.presentation.screen.ThemeSettingsScreen
import com.picmorrow.ui.theme.PicmorrowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val themePreferencesDataSource = ThemePreferencesDataSource(applicationContext)
        val themePreferenceRepository = ThemePreferenceRepositoryImpl(themePreferencesDataSource)
        val themeSettingsViewModelFactory =
            ThemeSettingsViewModel.Factory(
                observeThemeModeUseCase = ObserveThemeModeUseCase(themePreferenceRepository),
                setThemeModeUseCase = SetThemeModeUseCase(themePreferenceRepository),
            )

        setContent {
            PicmorrowApp(themeSettingsViewModelFactory)
        }
    }
}

@Composable
private fun PicmorrowApp(
    themeSettingsViewModelFactory: ThemeSettingsViewModel.Factory,
) {
    val themeSettingsViewModel: ThemeSettingsViewModel =
        viewModel(factory = themeSettingsViewModelFactory)
    val uiState by themeSettingsViewModel.uiState.collectAsState()

    PicmorrowAppContent(
        uiState = uiState,
        onThemeModeSelected = themeSettingsViewModel::setThemeMode,
    )
}

@Composable
private fun PicmorrowAppContent(
    uiState: ThemeSettingsUiState,
    onThemeModeSelected: (AppThemeMode) -> Unit,
) {
    val useDarkTheme =
        when (uiState.selectedThemeMode) {
            AppThemeMode.System -> isSystemInDarkTheme()
            AppThemeMode.Light -> false
            AppThemeMode.Dark -> true
        }

    PicmorrowTheme(darkTheme = useDarkTheme) {
        ThemeSettingsScreen(
            uiState = uiState,
            onThemeModeSelected = onThemeModeSelected,
        )
    }
}

@Preview(showBackground = true)
@Composable
@Suppress("UnusedPrivateMember")
private fun PicmorrowAppContentPreview() {
    PicmorrowAppContent(
        uiState = ThemeSettingsUiState(),
        onThemeModeSelected = {},
    )
}
