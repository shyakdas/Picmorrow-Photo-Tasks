package com.picmorrow.feature.phototasks

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.picmorrow.core.data.local.AppDatabase
import com.picmorrow.feature.phototasks.data.local.PhotoTaskEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PhotoTaskDatabaseTest {
    @Test
    fun photoTaskSurvivesDatabaseReopen() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val databaseName = "photo-task-test-${System.nanoTime()}.db"
        val task = PhotoTaskEntity(
            photoPath = "${context.filesDir}/photos/test.jpg",
            category = "Parking",
            title = "Find car",
            notes = "Level 2, section B",
            reminderAtMillis = 1234L,
        )
        val noReminder = task.copy(category = "Buy", title = "Buy milk", reminderAtMillis = null)
        var database = Room.databaseBuilder(context, AppDatabase::class.java, databaseName).build()

        try {
            val id = database.photoTaskDao().insert(task)
            val noReminderId = database.photoTaskDao().insert(noReminder)
            database.close()

            database = Room.databaseBuilder(context, AppDatabase::class.java, databaseName).build()
            assertEquals(task.copy(id = id), database.photoTaskDao().findById(id))
            assertEquals(noReminder.copy(id = noReminderId), database.photoTaskDao().findById(noReminderId))
            assertNull(database.photoTaskDao().findById(noReminderId + 1))
        } finally {
            database.close()
            context.deleteDatabase(databaseName)
        }
    }
}
