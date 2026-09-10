package com.picmorrow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.picmorrow.feature.onboarding.presentation.screen.WelcomeScreen
import com.picmorrow.feature.phototasks.presentation.screen.ActiveEmptyScreen
import com.picmorrow.ui.theme.PicmorrowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PicmorrowTheme {
                var currentScreen by rememberSaveable { mutableStateOf(AppScreen.Welcome) }

                when (currentScreen) {
                    AppScreen.Welcome -> {
                        WelcomeScreen(
                            onTakeFirstPhotoClick = {},
                            onExploreFirstClick = { currentScreen = AppScreen.Active },
                        )
                    }

                    AppScreen.Active -> {
                        ActiveEmptyScreen(
                            onTakePhotoClick = {},
                            onSettingsClick = {},
                        )
                    }
                }
            }
        }
    }
}

private enum class AppScreen {
    Welcome,
    Active,
}
