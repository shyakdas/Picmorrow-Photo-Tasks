package com.picmorrow.feature.camera.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.picmorrow.R

internal enum class CameraCategory(
    @param:StringRes val labelRes: Int,
    @param:DrawableRes val iconRes: Int,
    val chipWidth: Dp,
) {
    Parking(R.string.camera_category_parking, R.drawable.ic_camera_car, 100.dp),
    Buy(R.string.camera_category_buy, R.drawable.ic_camera_shopping_bag, 78.dp),
    Collect(R.string.camera_category_collect, R.drawable.ic_camera_package, 96.dp),
    Remember(R.string.camera_category_remember, R.drawable.ic_camera_bookmark, 124.dp),
}
