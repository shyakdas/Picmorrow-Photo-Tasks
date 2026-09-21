package com.picmorrow.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.picmorrow.feature.camera.presentation.model.CameraCategory
import com.picmorrow.feature.camera.presentation.screen.CameraRoute
import com.picmorrow.feature.onboarding.presentation.screen.WelcomeRoute
import com.picmorrow.feature.phototasks.presentation.screen.HomeScreen
import com.picmorrow.feature.phototasks.presentation.screen.NewPhotoTaskRoute

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
                onPhotoCaptured = { photoUri, category ->
                    navController.navigate(
                        AppDestination.NewPhotoTask.route(
                            photoPath = photoUri.path.orEmpty(),
                            categoryName = category.name,
                        ),
                    )
                },
            )
        }

        newPhotoTaskDestination(navController)
    }
}

private fun NavGraphBuilder.newPhotoTaskDestination(navController: NavHostController) {
    composable(
        route = AppDestination.NewPhotoTask.route,
        arguments = listOf(
            navArgument(AppDestination.NewPhotoTask.PhotoPathArg) {
                type = NavType.StringType
            },
            navArgument(AppDestination.NewPhotoTask.CategoryArg) {
                type = NavType.StringType
            },
        ),
    ) { backStackEntry ->
        val photoPath =
            Uri.decode(
                backStackEntry.arguments
                    ?.getString(AppDestination.NewPhotoTask.PhotoPathArg)
                    .orEmpty(),
            )
        val category =
            backStackEntry.arguments
                ?.getString(AppDestination.NewPhotoTask.CategoryArg)
                ?.let { runCatching { CameraCategory.valueOf(it) }.getOrNull() }
                ?: CameraCategory.Parking

        NewPhotoTaskRoute(
            photoPath = photoPath,
            selectedCategory = category,
            onCancelClick = {
                navController.navigateUp()
            },
            onRetakeClick = {
                navController.navigateUp()
            },
            onSaved = {
                navController.navigate(AppDestination.Home.route) {
                    popUpTo(navController.graph.id)
                    launchSingleTop = true
                }
            },
        )
    }
}
