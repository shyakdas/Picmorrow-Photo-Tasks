package com.picmorrow.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.picmorrow.feature.camera.presentation.screen.CameraRoute
import com.picmorrow.feature.onboarding.presentation.screen.WelcomeRoute
import com.picmorrow.feature.phototasks.presentation.screen.HomeScreen

@Composable
fun PicmorrowNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppDestination.Welcome.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(AppDestination.Welcome.route) {
            WelcomeRoute(
                onTakeFirstPhotoClick = {
                    navController.navigate(AppDestination.Camera.route)
                },
                onExploreFirstClick = {
                    navController.navigate(AppDestination.Home.route) {
                        popUpTo(AppDestination.Welcome.route) {
                            inclusive = true
                        }
                    }
                },
            )
        }

        composable(AppDestination.Home.route) {
            HomeScreen(
                onTakePhotoClick = {
                    navController.navigate(AppDestination.Camera.route)
                },
                onSettingsClick = {},
            )
        }

        composable(AppDestination.Camera.route) {
            CameraRoute(
                onCloseClick = {
                    navController.navigateUp()
                },
            )
        }
    }
}
