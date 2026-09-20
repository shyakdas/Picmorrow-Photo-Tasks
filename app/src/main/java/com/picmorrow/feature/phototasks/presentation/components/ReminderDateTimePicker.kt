package com.picmorrow.feature.phototasks.presentation.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.text.format.DateFormat
import android.widget.Toast
import com.picmorrow.R
import java.time.Instant
import java.time.LocalDate
import java.time.ZonedDateTime

internal fun showReminderDateTimePicker(
    context: Context,
    darkTheme: Boolean,
    currentReminderMillis: Long?,
    onSelected: (Long) -> Unit,
) {
    val pickerTheme =
        if (darkTheme) R.style.Theme_Picmorrow_ReminderPicker_Dark else R.style.Theme_Picmorrow_ReminderPicker_Light
    val now = ZonedDateTime.now()
    val initial = currentReminderMillis
        ?.let { Instant.ofEpochMilli(it).atZone(now.zone) }
        ?.takeIf { it.isAfter(now) }
        ?: now.plusHours(1)

    DatePickerDialog(
        context,
        pickerTheme,
        { _, year, month, day ->
            val date = LocalDate.of(year, month + 1, day)
            TimePickerDialog(
                context,
                pickerTheme,
                { _, hour, minute ->
                    val selected = date.atTime(hour, minute).atZone(now.zone)
                    if (selected.isAfter(ZonedDateTime.now())) {
                        onSelected(selected.toInstant().toEpochMilli())
                    } else {
                        Toast.makeText(context, R.string.reminder_choose_future_time, Toast.LENGTH_SHORT).show()
                    }
                },
                initial.hour,
                initial.minute,
                DateFormat.is24HourFormat(context),
            ).show()
        },
        initial.year,
        initial.monthValue - 1,
        initial.dayOfMonth,
    ).apply {
        datePicker.minDate = now.toLocalDate().atStartOfDay(now.zone).toInstant().toEpochMilli()
    }.show()
}
