package com.picmorrow.feature.phototasks

import com.picmorrow.feature.phototasks.presentation.model.ReminderPreset
import com.picmorrow.feature.phototasks.presentation.model.reminderTime
import java.time.ZoneId
import java.time.ZonedDateTime
import org.junit.Assert.assertEquals
import org.junit.Test

class ReminderPresetTest {
    private val zone = ZoneId.of("UTC")

    @Test
    fun oneHourIsRelativeToSelectionTime() {
        val now = ZonedDateTime.of(2026, 9, 18, 9, 41, 32, 0, zone)

        assertEquals(
            ZonedDateTime.of(2026, 9, 18, 10, 41, 32, 0, zone),
            reminderTime(now, ReminderPreset.InOneHour),
        )
    }

    @Test
    fun eveningRollsToTomorrowWhenAlreadyPast() {
        val now = ZonedDateTime.of(2026, 9, 18, 20, 0, 0, 0, zone)

        assertEquals(
            ZonedDateTime.of(2026, 9, 19, 19, 0, 0, 0, zone),
            reminderTime(now, ReminderPreset.ThisEvening),
        )
    }

    @Test
    fun tomorrowMorningUsesNineAm() {
        val now = ZonedDateTime.of(2026, 9, 18, 9, 41, 0, 0, zone)

        assertEquals(
            ZonedDateTime.of(2026, 9, 19, 9, 0, 0, 0, zone),
            reminderTime(now, ReminderPreset.TomorrowMorning),
        )
    }
}
