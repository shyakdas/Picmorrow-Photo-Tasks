package com.picmorrow.feature.phototasks.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo_tasks")
data class PhotoTaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val photoPath: String,
    val category: String,
    val title: String,
    val notes: String,
    val reminderAtMillis: Long?,
    val completedAtMillis: Long? = null,
    @ColumnInfo(defaultValue = "0") val capturedAtMillis: Long = System.currentTimeMillis(),
)
