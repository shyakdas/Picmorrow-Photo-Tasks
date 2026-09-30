package com.picmorrow.feature.phototasks.data.reminder

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.picmorrow.feature.phototasks.domain.reminder.TaskReminderScheduler
import java.util.concurrent.TimeUnit

class AndroidTaskReminderScheduler(context: Context) : TaskReminderScheduler {
    private val workManager = WorkManager.getInstance(context.applicationContext)

    override fun schedule(taskId: Long, reminderAtMillis: Long) {
        val delayMillis = (reminderAtMillis - System.currentTimeMillis()).coerceAtLeast(0L)
        val request = OneTimeWorkRequestBuilder<TaskReminderWorker>()
            .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS)
            .setInputData(
                workDataOf(
                    TaskReminderWorker.TaskIdKey to taskId,
                    TaskReminderWorker.ReminderAtKey to reminderAtMillis,
                ),
            )
            .build()

        workManager.enqueueUniqueWork(
            uniqueWorkName(taskId),
            ExistingWorkPolicy.REPLACE,
            request,
        )
    }

    override fun cancel(taskId: Long) {
        workManager.cancelUniqueWork(uniqueWorkName(taskId))
    }

    private fun uniqueWorkName(taskId: Long): String = "photo-task-reminder-$taskId"
}
