package com.picmorrow.feature.settings.domain.model

enum class AppThemeMode(val storageKey: String) {
    System("system"),
    Light("light"),
    Dark("dark"),
    ;

    companion object {
        fun fromStorageKey(storageKey: String?): AppThemeMode =
            entries.firstOrNull { it.storageKey == storageKey } ?: System
    }
}
