package com.picmorrow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.picmorrow.feature.settings.domain.model.AppThemeMode
import com.picmorrow.feature.settings.presentation.ThemeSettingsViewModel
import com.picmorrow.navigation.AppDestination
import com.picmorrow.navigation.PicmorrowNavHost
import com.picmorrow.ui.adaptive.ProvideAdaptiveLayoutInfo
import com.picmorrow.ui.theme.PicmorrowTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModel()
    private val themeViewModel: ThemeSettingsViewModel by viewModel()

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
                ProvideAdaptiveLayoutInfo {
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
    }

    companion object {
        const val EXTRA_START_DESTINATION = "com.picmorrow.extra.START_DESTINATION"
    }
}
