package com.picmorrow.feature.settings.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test

class AppThemeModeTest {
    @Test
    fun fromStorageKey_returnsSystemWhenValueIsMissing() {
        assertEquals(
            AppThemeMode.System,
            AppThemeMode.fromStorageKey(null),
        )
    }

    @Test
    fun fromStorageKey_returnsSystemWhenValueIsUnknown() {
        assertEquals(
            AppThemeMode.System,
            AppThemeMode.fromStorageKey("unexpected"),
        )
    }

    @Test
    fun fromStorageKey_returnsMatchingThemeMode() {
        assertEquals(
            AppThemeMode.Dark,
            AppThemeMode.fromStorageKey("dark"),
        )
    }
}
