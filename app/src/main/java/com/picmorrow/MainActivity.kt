package com.picmorrow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.picmorrow.feature.onboarding.presentation.screen.WelcomeScreen
import com.picmorrow.ui.theme.PicmorrowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PicmorrowTheme {
                WelcomeScreen(
                    onTakeFirstPhotoClick = {},
                    onExploreFirstClick = {},
                )
            }
        }
    }
}
