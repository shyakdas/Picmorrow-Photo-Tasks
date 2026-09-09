package com.picmorrow.feature.settings.domain.usecase

import com.picmorrow.feature.settings.domain.repository.ThemePreferenceRepository

class ObserveThemeModeUseCase(
    private val repository: ThemePreferenceRepository,
) {
    operator fun invoke() = repository.themeMode
}
