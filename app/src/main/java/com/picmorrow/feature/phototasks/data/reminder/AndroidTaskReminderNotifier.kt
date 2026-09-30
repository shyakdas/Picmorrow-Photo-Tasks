package com.picmorrow.feature.phototasks.data.reminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.picmorrow.MainActivity
import com.picmorrow.R
import com.picmorrow.core.common.areNotificationsAllowed
import com.picmorrow.navigation.AppDestination

internal class AndroidTaskReminderNotifier(private val context: Context) : TaskReminderNotificationPublisher {
    override fun show(taskId: Long, taskTitle: String) {
        createChannel()
        if (!context.areNotificationsAllowed()) return

        val notification = NotificationCompat.Builder(context, ChannelId)
            .setSmallIcon(R.drawable.ic_notification_reminder)
            .setContentTitle(context.getString(R.string.reminder_notification_title))
            .setContentText(taskTitle)
            .setContentIntent(taskDetailsPendingIntent(taskId))
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setAutoCancel(true)
            .build()

        try {
            NotificationManagerCompat.from(context).notify(notificationId(taskId), notification)
        } catch (_: SecurityException) {
            // Permission can change between the check and notify call.
        }
    }

    private fun createChannel() {
        val channel = NotificationChannel(
            ChannelId,
            context.getString(R.string.reminder_channel_name),
            NotificationManager.IMPORTANCE_DEFAULT,
        ).apply {
            description = context.getString(R.string.reminder_channel_description)
        }
        context.getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
    }

    private fun taskDetailsPendingIntent(taskId: Long): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(MainActivity.EXTRA_START_DESTINATION, AppDestination.TaskDetails.route(taskId))
        }
        return PendingIntent.getActivity(
            context,
            notificationId(taskId),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
    }

    private fun notificationId(taskId: Long): Int = (taskId xor (taskId ushr Int.SIZE_BITS)).toInt()

    private companion object {
        const val ChannelId = "task_reminders"
    }
}
