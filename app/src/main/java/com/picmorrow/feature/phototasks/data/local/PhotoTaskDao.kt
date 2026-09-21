package com.picmorrow.feature.phototasks.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PhotoTaskDao {
    @Insert
    suspend fun insert(task: PhotoTaskEntity): Long

    @Query("SELECT * FROM photo_tasks WHERE id = :id")
    suspend fun findById(id: Long): PhotoTaskEntity?
}
