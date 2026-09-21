package com.picmorrow.feature.phototasks.presentation.model

import androidx.annotation.StringRes

internal data class PhotoTaskSaveUiState(
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    @param:StringRes val errorMessageRes: Int? = null,
)
