package com.picmorrow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picmorrow.feature.phototasks.domain.usecase.HasPhotoTasksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val hasPhotoTasks: HasPhotoTasksUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value =
                runCatching { hasPhotoTasks() }
                    .fold(
                        onSuccess = { hasTasks ->
                            if (hasTasks) MainUiState.Home else MainUiState.Introduction
                        },
                        onFailure = { MainUiState.Introduction },
                    )
        }
    }
}

sealed interface MainUiState {
    data object Loading : MainUiState

    data object Introduction : MainUiState

    data object Home : MainUiState
}
