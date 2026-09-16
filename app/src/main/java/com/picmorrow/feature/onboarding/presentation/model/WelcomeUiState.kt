package com.picmorrow.feature.onboarding.presentation.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class WelcomeUiState(
    @param:DrawableRes val heroRes: Int,
    val secondaryTextColor: Color,
)
