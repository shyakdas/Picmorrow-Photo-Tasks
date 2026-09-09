package com.picmorrow.feature.settings.presentation.model

import com.picmorrow.feature.settings.domain.model.AppThemeMode

data class ThemeSettingsUiState(
    val selectedThemeMode: AppThemeMode = AppThemeMode.System,
)
