package com.picmorrow.feature.phototasks.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoTaskDao {
    @Insert
    suspend fun insert(task: PhotoTaskEntity): Long

    @Query("SELECT * FROM photo_tasks WHERE id = :id")
    suspend fun findById(id: Long): PhotoTaskEntity?

    @Query("SELECT * FROM photo_tasks WHERE completedAtMillis IS NULL ORDER BY id DESC")
    fun observeActive(): Flow<List<PhotoTaskEntity>>

    @Query("SELECT * FROM photo_tasks WHERE completedAtMillis IS NOT NULL ORDER BY completedAtMillis DESC")
    fun observeCompleted(): Flow<List<PhotoTaskEntity>>

    @Query("UPDATE photo_tasks SET completedAtMillis = :completedAtMillis WHERE id = :id AND completedAtMillis IS NULL")
    suspend fun complete(id: Long, completedAtMillis: Long): Int
}
