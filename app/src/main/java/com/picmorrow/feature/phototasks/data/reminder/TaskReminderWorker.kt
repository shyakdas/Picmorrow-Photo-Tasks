package com.picmorrow.feature.phototasks.data.reminder

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class TaskReminderWorker(
    appContext: Context,
    workerParameters: WorkerParameters,
    private val reminderDelivery: TaskReminderDelivery,
) : CoroutineWorker(appContext, workerParameters) {
    override suspend fun doWork(): Result {
        val taskId = inputData.getLong(TaskIdKey, MissingValue)
        val scheduledReminderAt = inputData.getLong(ReminderAtKey, MissingValue)
        return if (taskId == MissingValue || scheduledReminderAt == MissingValue) {
            Result.failure()
        } else {
            reminderDelivery.deliver(taskId, scheduledReminderAt)
            Result.success()
        }
    }

    companion object {
        const val TaskIdKey = "task_id"
        const val ReminderAtKey = "reminder_at"
        private const val MissingValue = Long.MIN_VALUE
    }
}
