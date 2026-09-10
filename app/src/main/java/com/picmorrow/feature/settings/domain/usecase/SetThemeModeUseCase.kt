package com.picmorrow.feature.settings.domain.usecase

import com.picmorrow.feature.settings.domain.model.AppThemeMode
import com.picmorrow.feature.settings.domain.repository.ThemePreferenceRepository

class SetThemeModeUseCase(
    private val repository: ThemePreferenceRepository,
) {
    suspend operator fun invoke(themeMode: AppThemeMode) {
        repository.setThemeMode(themeMode)
    }
}
