package com.picmorrow.feature.settings.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.picmorrow.feature.settings.domain.model.AppThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.settingsDataStore by preferencesDataStore(name = "settings")

class ThemePreferencesDataSource(
    context: Context,
) {
    private val dataStore = context.applicationContext.settingsDataStore

    val themeMode: Flow<AppThemeMode> =
        dataStore.data.map { preferences ->
            AppThemeMode.fromStorageKey(preferences[ThemeModeKey])
        }

    suspend fun setThemeMode(themeMode: AppThemeMode) {
        dataStore.edit { preferences ->
            preferences[ThemeModeKey] = themeMode.storageKey
        }
    }

    private companion object {
        val ThemeModeKey = stringPreferencesKey("theme_mode")
    }
}
