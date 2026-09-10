package com.picmorrow.feature.settings.data.repository

import com.picmorrow.feature.settings.data.local.ThemePreferencesDataSource
import com.picmorrow.feature.settings.domain.model.AppThemeMode
import com.picmorrow.feature.settings.domain.repository.ThemePreferenceRepository

class ThemePreferenceRepositoryImpl(
    private val dataSource: ThemePreferencesDataSource,
) : ThemePreferenceRepository {
    override val themeMode = dataSource.themeMode

    override suspend fun setThemeMode(themeMode: AppThemeMode) {
        dataSource.setThemeMode(themeMode)
    }
}
