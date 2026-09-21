package com.picmorrow.feature.phototasks.domain.model

data class PhotoTaskDraft(
    val photoPath: String,
    val category: String,
    val title: String,
    val notes: String,
    val reminderAtMillis: Long?,
)
