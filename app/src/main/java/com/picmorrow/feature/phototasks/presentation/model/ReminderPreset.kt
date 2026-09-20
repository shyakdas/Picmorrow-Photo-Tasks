package com.picmorrow.feature.phototasks.presentation.model

import java.time.ZonedDateTime

internal enum class ReminderPreset {
    InOneHour,
    ThisEvening,
    TomorrowMorning,
}

internal fun reminderTime(now: ZonedDateTime, preset: ReminderPreset): ZonedDateTime =
    when (preset) {
        ReminderPreset.InOneHour -> now.plusHours(1)
        ReminderPreset.ThisEvening -> {
            val evening = now.toLocalDate().atTime(EVENING_HOUR, 0).atZone(now.zone)
            if (evening.isAfter(now)) {
                evening
            } else {
                now.toLocalDate().plusDays(1).atTime(EVENING_HOUR, 0).atZone(now.zone)
            }
        }
        ReminderPreset.TomorrowMorning -> now.toLocalDate().plusDays(1).atTime(MORNING_HOUR, 0).atZone(now.zone)
    }

private const val EVENING_HOUR = 19
private const val MORNING_HOUR = 9
