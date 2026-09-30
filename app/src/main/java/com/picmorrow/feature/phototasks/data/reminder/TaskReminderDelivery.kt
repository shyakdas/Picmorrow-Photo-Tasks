package com.picmorrow.feature.phototasks.data.reminder

import com.picmorrow.feature.phototasks.data.local.PhotoTaskDao

internal fun interface TaskReminderNotificationPublisher {
    fun show(taskId: Long, taskTitle: String)
}

internal class TaskReminderDelivery(
    private val dao: PhotoTaskDao,
    private val notificationPublisher: TaskReminderNotificationPublisher,
) {
    suspend fun deliver(taskId: Long, scheduledReminderAt: Long) {
        val task = dao.findById(taskId)
        if (task != null && task.completedAtMillis == null && task.reminderAtMillis == scheduledReminderAt) {
            notificationPublisher.show(task.id, task.title)
        }
    }
}
