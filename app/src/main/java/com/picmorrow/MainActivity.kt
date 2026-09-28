package com.picmorrow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.picmorrow.core.data.local.AppDatabase
import com.picmorrow.feature.phototasks.data.repository.PhotoTaskRepositoryImpl
import com.picmorrow.feature.phototasks.domain.usecase.HasPhotoTasksUseCase
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

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { viewModel.uiState.value == MainUiState.Loading }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            PicmorrowTheme {
                when (uiState) {
                    MainUiState.Loading -> Unit
                    MainUiState.Introduction ->
                        PicmorrowNavHost(startDestination = AppDestination.Welcome.route)
                    MainUiState.Home -> PicmorrowNavHost(startDestination = AppDestination.Home.route)
                }
            }
        }
    }
}
