package com.picmorrow.navigation

import android.net.Uri

sealed class AppDestination(val route: String) {
    data object Welcome : AppDestination("welcome")
    data object Home : AppDestination("home")
    data object Camera : AppDestination("camera")
    data object TaskDetails : AppDestination("task-detail/{taskId}") {
        const val TaskIdArg = "taskId"

        fun route(taskId: Long): String = "task-detail/$taskId"
    }
    data object NewPhotoTask : AppDestination("new-photo-task?photoPath={photoPath}&category={category}") {
        const val PhotoPathArg = "photoPath"
        const val CategoryArg = "category"

        fun route(photoPath: String, categoryName: String): String =
            "new-photo-task?$PhotoPathArg=${Uri.encode(photoPath)}&$CategoryArg=${Uri.encode(categoryName)}"
    }
}
