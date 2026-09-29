package com.picmorrow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.picmorrow.core.data.local.AppDatabase
import com.picmorrow.feature.phototasks.data.repository.PhotoTaskRepositoryImpl
import com.picmorrow.feature.phototasks.domain.usecase.HasPhotoTasksUseCase
import com.picmorrow.feature.settings.data.local.ThemePreferencesDataSource
import com.picmorrow.feature.settings.data.repository.ThemePreferenceRepositoryImpl
import com.picmorrow.feature.settings.domain.model.AppThemeMode
import com.picmorrow.feature.settings.domain.usecase.ObserveThemeModeUseCase
import com.picmorrow.feature.settings.domain.usecase.SetThemeModeUseCase
import com.picmorrow.feature.settings.presentation.ThemeSettingsViewModel
import com.picmorrow.navigation.AppDestination
import com.picmorrow.navigation.PicmorrowNavHost
import com.picmorrow.ui.theme.PicmorrowTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels {
        val dao = AppDatabase.getInstance(applicationContext).photoTaskDao()
        viewModelFactory {
            initializer {
                MainViewModel(HasPhotoTasksUseCase(PhotoTaskRepositoryImpl(dao)))
            }
        }
    }
    private val themeViewModel: ThemeSettingsViewModel by viewModels {
        val repository = ThemePreferenceRepositoryImpl(ThemePreferencesDataSource(applicationContext))
        ThemeSettingsViewModel.Factory(
            ObserveThemeModeUseCase(repository),
            SetThemeModeUseCase(repository),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { viewModel.uiState.value == MainUiState.Loading }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val themeUiState by themeViewModel.uiState.collectAsStateWithLifecycle()
            val darkTheme = when (themeUiState.selectedThemeMode) {
                AppThemeMode.System -> isSystemInDarkTheme()
                AppThemeMode.Light -> false
                AppThemeMode.Dark -> true
            }
            SideEffect {
                WindowCompat.getInsetsController(window, window.decorView).apply {
                    isAppearanceLightStatusBars = !darkTheme
                    isAppearanceLightNavigationBars = !darkTheme
                }
            }
            val shortcutDestination = intent.getStringExtra(EXTRA_START_DESTINATION)
            PicmorrowTheme(darkTheme = darkTheme) {
                when (uiState) {
                    MainUiState.Loading -> Unit
                    MainUiState.Introduction ->
                        PicmorrowNavHost(
                            startDestination = shortcutDestination ?: AppDestination.Welcome.route,
                        )
                    MainUiState.Home -> PicmorrowNavHost(
                        startDestination = shortcutDestination ?: AppDestination.Home.route,
                    )
                }
            }
        }
    }

    companion object {
        const val EXTRA_START_DESTINATION = "com.picmorrow.extra.START_DESTINATION"
    }
}
