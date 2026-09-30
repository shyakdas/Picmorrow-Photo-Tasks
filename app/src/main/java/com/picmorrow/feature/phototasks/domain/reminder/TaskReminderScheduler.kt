package com.picmorrow.feature.phototasks.domain.reminder

interface TaskReminderScheduler {
    fun schedule(taskId: Long, reminderAtMillis: Long)

    fun cancel(taskId: Long)
}
