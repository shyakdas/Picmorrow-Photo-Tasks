package com.picmorrow.feature.phototasks.domain.model

data class PhotoTask(
    val id: Long,
    val photoPath: String,
    val category: String,
    val title: String,
    val notes: String,
    val reminderAtMillis: Long?,
    val completedAtMillis: Long?,
)
