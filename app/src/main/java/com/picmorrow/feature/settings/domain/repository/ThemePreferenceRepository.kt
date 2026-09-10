package com.picmorrow.feature.settings.domain.repository

import com.picmorrow.feature.settings.domain.model.AppThemeMode
import kotlinx.coroutines.flow.Flow

interface ThemePreferenceRepository {
    val themeMode: Flow<AppThemeMode>

    suspend fun setThemeMode(themeMode: AppThemeMode)
}
