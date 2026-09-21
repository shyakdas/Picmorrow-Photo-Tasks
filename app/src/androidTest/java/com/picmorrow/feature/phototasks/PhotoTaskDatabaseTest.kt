package com.picmorrow.feature.phototasks

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.picmorrow.core.data.local.AppDatabase
import com.picmorrow.feature.phototasks.data.local.PhotoTaskEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import kotlinx.coroutines.flow.first
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

    @Test
    fun completeMovesTaskBetweenObservableLists() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        try {
            val dao = database.photoTaskDao()
            val id = dao.insert(PhotoTaskEntity(photoPath = "/photo.jpg", category = "Parking", title = "Find car", notes = "", reminderAtMillis = null))
            assertEquals(listOf(id), dao.observeActive().first().map { it.id })
            assertEquals(emptyList<PhotoTaskEntity>(), dao.observeCompleted().first())

            assertEquals(1, dao.complete(id, 1234L))
            assertEquals(emptyList<PhotoTaskEntity>(), dao.observeActive().first())
            assertEquals(listOf(id), dao.observeCompleted().first().map { it.id })
            assertEquals(0, dao.complete(id, 5678L))
            assertEquals(1234L, dao.findById(id)?.completedAtMillis)
        } finally {
            database.close()
        }
    }

    @Test
    fun migrationKeepsExistingPhotoTasksActive() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val databaseName = "photo-task-migration-${System.nanoTime()}.db"
        context.openOrCreateDatabase(databaseName, 0, null).use { legacy ->
            legacy.execSQL(
                "CREATE TABLE photo_tasks (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "photoPath TEXT NOT NULL, category TEXT NOT NULL, title TEXT NOT NULL, " +
                    "notes TEXT NOT NULL, reminderAtMillis INTEGER)",
            )
            legacy.execSQL(
                "INSERT INTO photo_tasks (photoPath, category, title, notes, reminderAtMillis) " +
                    "VALUES ('/old-photo.jpg', 'Parking', 'Find car', 'Level 2', 1234)",
            )
            legacy.version = 1
        }

        val database = Room.databaseBuilder(context, AppDatabase::class.java, databaseName).build()
        try {
            val saved = database.photoTaskDao().observeActive().first().single()
            assertEquals("/old-photo.jpg", saved.photoPath)
            assertEquals("Find car", saved.title)
            assertNull(saved.completedAtMillis)
        } finally {
            database.close()
            context.deleteDatabase(databaseName)
        }
    }
}
