package com.picmorrow.feature.phototasks.presentation.screen

import java.time.Instant
import java.time.ZoneId
import java.util.Locale
import org.junit.Assert.assertEquals
import org.junit.Test

class TaskDetailsDateTest {
    @Test
    fun formatsDateInRequestedTimeZone() {
        val timestamp = Instant.parse("2026-09-07T23:30:00Z").toEpochMilli()

        assertEquals("8 Sep 2026", formatCapturedDate(timestamp, ZoneId.of("Asia/Kolkata"), Locale.US))
    }
}
