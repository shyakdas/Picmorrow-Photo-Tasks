package com.picmorrow.core.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.picmorrow.feature.phototasks.data.local.PhotoTaskDao
import com.picmorrow.feature.phototasks.data.local.PhotoTaskEntity

@Database(entities = [PhotoTaskEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photoTaskDao(): PhotoTaskDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase =
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "picmorrow.db",
            ).build().also { instance = it }
    }
}
