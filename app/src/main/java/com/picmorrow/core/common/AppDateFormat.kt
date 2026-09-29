package com.picmorrow.core.common

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

enum class AppDateFormat(internal val pattern: String) {
    DayMonth("d MMM"),
    DayMonthYear("d MMM yyyy"),
}

fun formatDate(
    timestampMillis: Long,
    format: AppDateFormat,
    zoneId: ZoneId = ZoneId.systemDefault(),
    locale: Locale = Locale.getDefault(),
): String = DateTimeFormatter.ofPattern(format.pattern, locale)
    .format(Instant.ofEpochMilli(timestampMillis).atZone(zoneId))
