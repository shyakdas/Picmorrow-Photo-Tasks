package com.picmorrow.feature.phototasks.presentation.common.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

internal data class PhotoTaskEmptyStateConfig(
    @param:StringRes val titleRes: Int,
    @param:StringRes val bodyRes: Int,
    @param:StringRes val actionRes: Int,
    @param:DrawableRes val iconRes: Int,
    @param:DrawableRes val actionIconRes: Int = iconRes,
)
