package com.picmorrow.core.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import org.junit.Assert.assertSame
import org.junit.Test
import org.mockito.Mockito

class AppDatabaseTest {
    @Test
    fun getInstanceBuildsOneDatabaseWithApplicationContext() {
        val context = Mockito.mock(Context::class.java)
        val applicationContext = Mockito.mock(Context::class.java)
        val database = Mockito.mock(AppDatabase::class.java, Mockito.withSettings().useConstructor())
        @Suppress("UNCHECKED_CAST")
        val builder = Mockito.mock(RoomDatabase.Builder::class.java) as RoomDatabase.Builder<AppDatabase>
        Mockito.`when`(context.applicationContext).thenReturn(applicationContext)
        Mockito.`when`(builder.build()).thenReturn(database)

        Mockito.mockStatic(Room::class.java).use { room ->
            room.`when`<RoomDatabase.Builder<AppDatabase>> {
                Room.databaseBuilder(applicationContext, AppDatabase::class.java, "picmorrow.db")
            }.thenReturn(builder)

            assertSame(database, AppDatabase.getInstance(context))
            assertSame(database, AppDatabase.getInstance(context))
            room.verify {
                Room.databaseBuilder(applicationContext, AppDatabase::class.java, "picmorrow.db")
            }
        }
    }
}
