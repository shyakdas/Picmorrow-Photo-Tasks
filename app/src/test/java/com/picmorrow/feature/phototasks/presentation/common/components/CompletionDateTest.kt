package com.picmorrow.feature.phototasks.presentation.common.components

import java.time.Instant
import java.time.ZoneId
import java.util.Locale
import org.junit.Assert.assertEquals
import org.junit.Test

class CompletionDateTest {
    @Test
    fun formatsCompletionDayAndMonth() {
        val timestamp = Instant.parse("2026-09-07T12:00:00Z").toEpochMilli()

        assertEquals("7 Sep", formatCompletionDate(timestamp, ZoneId.of("UTC"), Locale.US))
    }

    @Test
    fun respectsLocalDateAcrossTimeZones() {
        val timestamp = Instant.parse("2026-09-07T23:30:00Z").toEpochMilli()

        assertEquals("8 Sep", formatCompletionDate(timestamp, ZoneId.of("Asia/Kolkata"), Locale.US))
    }
}
