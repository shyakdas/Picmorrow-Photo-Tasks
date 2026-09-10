package com.picmorrow.feature.phototasks.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color

internal data class PhotoTaskEmptyStateConfig(
    @param:StringRes val titleRes: Int,
    @param:StringRes val bodyRes: Int,
    @param:StringRes val actionRes: Int,
    @param:DrawableRes val iconRes: Int,
    val iconBackgroundColor: Color,
    val primaryTextColor: Color,
    val secondaryTextColor: Color,
)
