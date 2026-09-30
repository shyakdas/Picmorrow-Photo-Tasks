package com.picmorrow.feature.phototasks.data.reminder

import com.picmorrow.feature.phototasks.data.local.PhotoTaskDao
import com.picmorrow.feature.phototasks.data.local.PhotoTaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class TaskReminderDeliveryTest {
    @Test
    fun matchingActiveTaskPublishesNotification() = runBlocking {
        val publisher = RecordingPublisher()
        val delivery = TaskReminderDelivery(FakeDao(activeTask), publisher)

        delivery.deliver(TaskId, ReminderAt)

        assertEquals(TaskId to "Buy milk", publisher.published)
    }

    @Test
    fun missingCompletedOrRescheduledTaskDoesNotPublish() = runBlocking {
        val tasks = listOf(
            null,
            activeTask.copy(completedAtMillis = 2000L),
            activeTask.copy(reminderAtMillis = ReminderAt + 1),
        )

        tasks.forEach { task ->
            val publisher = RecordingPublisher()
            TaskReminderDelivery(FakeDao(task), publisher).deliver(TaskId, ReminderAt)
            assertEquals(null, publisher.published)
        }
    }

    private class RecordingPublisher : TaskReminderNotificationPublisher {
        var published: Pair<Long, String>? = null

        override fun show(taskId: Long, taskTitle: String) {
            published = taskId to taskTitle
        }
    }

    private class FakeDao(private val task: PhotoTaskEntity?) : PhotoTaskDao {
        override suspend fun insert(task: PhotoTaskEntity): Long = error("Not used")

        override suspend fun findById(id: Long): PhotoTaskEntity? = task

        override suspend fun hasTasks(): Boolean = error("Not used")

        override fun observeActive(): Flow<List<PhotoTaskEntity>> = emptyFlow()

        override fun observeCompleted(): Flow<List<PhotoTaskEntity>> = emptyFlow()

        override suspend fun complete(id: Long, completedAtMillis: Long, isSavedToGallery: Boolean): Int =
            error("Not used")

        override suspend fun delete(id: Long): Int = error("Not used")
    }

    private companion object {
        const val TaskId = 42L
        const val ReminderAt = 1000L
        val activeTask = PhotoTaskEntity(
            id = TaskId,
            photoPath = "/photos/milk.jpg",
            category = "Buy",
            title = "Buy milk",
            notes = "",
            reminderAtMillis = ReminderAt,
        )
    }
}
