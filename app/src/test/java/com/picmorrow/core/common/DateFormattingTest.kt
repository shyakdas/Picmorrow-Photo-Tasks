package com.picmorrow.core.common

import java.time.Instant
import java.time.ZoneId
import java.util.Locale
import org.junit.Assert.assertEquals
import org.junit.Test

class DateFormattingTest {
    @Test
    fun formatsDayAndMonth() {
        val timestamp = Instant.parse("2026-09-07T12:00:00Z").toEpochMilli()

        assertEquals(
            "7 Sep",
            formatDate(timestamp, AppDateFormat.DayMonth, ZoneId.of("UTC"), Locale.US),
        )
    }

    @Test
    fun formatsDayMonthAndYear() {
        val timestamp = Instant.parse("2026-09-07T12:00:00Z").toEpochMilli()

        assertEquals(
            "7 Sep 2026",
            formatDate(timestamp, AppDateFormat.DayMonthYear, ZoneId.of("UTC"), Locale.US),
        )
    }

    @Test
    fun respectsLocalDateAcrossTimeZones() {
        val timestamp = Instant.parse("2026-09-07T23:30:00Z").toEpochMilli()

        assertEquals(
            "8 Sep 2026",
            formatDate(timestamp, AppDateFormat.DayMonthYear, ZoneId.of("Asia/Kolkata"), Locale.US),
        )
    }
}
