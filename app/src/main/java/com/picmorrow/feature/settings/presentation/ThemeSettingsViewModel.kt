package com.picmorrow.feature.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.picmorrow.feature.settings.domain.model.AppThemeMode
import com.picmorrow.feature.settings.domain.usecase.ObserveThemeModeUseCase
import com.picmorrow.feature.settings.domain.usecase.SetThemeModeUseCase
import com.picmorrow.feature.settings.presentation.model.ThemeSettingsUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ThemeSettingsViewModel(
    observeThemeModeUseCase: ObserveThemeModeUseCase,
    private val setThemeModeUseCase: SetThemeModeUseCase,
) : ViewModel() {
    val uiState =
        observeThemeModeUseCase()
            .map { themeMode -> ThemeSettingsUiState(selectedThemeMode = themeMode) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(StopTimeoutMillis),
                initialValue = ThemeSettingsUiState(),
            )

    fun setThemeMode(themeMode: AppThemeMode) {
        viewModelScope.launch {
            setThemeModeUseCase(themeMode)
        }
    }

    class Factory(
        private val observeThemeModeUseCase: ObserveThemeModeUseCase,
        private val setThemeModeUseCase: SetThemeModeUseCase,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            ThemeSettingsViewModel(
                observeThemeModeUseCase = observeThemeModeUseCase,
                setThemeModeUseCase = setThemeModeUseCase,
            ) as T
    }

    private companion object {
        const val StopTimeoutMillis = 5_000L
    }
}
