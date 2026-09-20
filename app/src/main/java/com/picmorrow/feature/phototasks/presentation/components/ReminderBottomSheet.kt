@file:Suppress("MagicNumber")

package com.picmorrow.feature.phototasks.presentation.components

import android.text.format.DateFormat
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.picmorrow.R
import com.picmorrow.feature.phototasks.presentation.model.ReminderPreset
import com.picmorrow.feature.phototasks.presentation.model.reminderTime
import com.picmorrow.ui.theme.PicmorrowTheme
import java.time.ZonedDateTime
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ReminderBottomSheet(
    darkTheme: Boolean,
    onDismiss: () -> Unit,
    onPresetSelected: (ZonedDateTime) -> Unit,
    onChooseCustom: () -> Unit,
    onRemove: () -> Unit,
) {
    val openedAt = remember { ZonedDateTime.now() }
    val sheetColor = if (darkTheme) DarkSheetBackground else LightSheetBackground

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        shape = RoundedCornerShape(topStart = SHEET_CORNER_RADIUS, topEnd = SHEET_CORNER_RADIUS),
        containerColor = sheetColor,
        scrimColor = Color.Black.copy(alpha = 0.4f),
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 22.dp)
                    .size(width = 36.dp, height = 4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(if (darkTheme) DarkHandle else LightHandle),
            )
        },
    ) {
        ReminderSheetContent(
            darkTheme = darkTheme,
            now = openedAt,
            onPresetSelected = { preset -> onPresetSelected(reminderTime(ZonedDateTime.now(), preset)) },
            onChooseCustom = onChooseCustom,
            onRemove = onRemove,
        )
    }
}

@Composable
internal fun ReminderSheetContent(
    darkTheme: Boolean,
    now: ZonedDateTime,
    onPresetSelected: (ReminderPreset) -> Unit,
    onChooseCustom: () -> Unit,
    onRemove: () -> Unit,
) {
    val context = LocalContext.current
    val textColor = if (darkTheme) Color.White else Color(0xFF222222)
    val secondaryColor = if (darkTheme) Color(0xFFA9A9A9) else Color(0xFF626262)
    val optionColor = if (darkTheme) DarkOptionBackground else Color.White
    val borderColor = if (darkTheme) DarkOptionBorder else LightOptionBorder
    val eveningTime = reminderTime(now, ReminderPreset.ThisEvening)
    val eveningLabel =
        if (eveningTime.toLocalDate() == now.toLocalDate()) {
            R.string.reminder_this_evening
        } else {
            R.string.reminder_tomorrow_evening
        }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 23.dp),
    ) {
        Text(
            text = stringResource(R.string.reminder_sheet_title),
            color = textColor,
            fontSize = 20.sp,
            lineHeight = 26.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(18.dp))

        val options = listOf(
            ReminderPreset.InOneHour to R.string.reminder_in_one_hour,
            ReminderPreset.ThisEvening to eveningLabel,
            ReminderPreset.TomorrowMorning to R.string.reminder_tomorrow_morning,
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            options.forEach { (preset, labelRes) ->
                val time = reminderTime(now, preset)
                ReminderOptionRow(
                    label = stringResource(labelRes),
                    time = DateFormat.getTimeFormat(context).format(Date.from(time.toInstant())),
                    textColor = textColor,
                    secondaryColor = secondaryColor,
                    background = optionColor,
                    border = borderColor,
                    onClick = { onPresetSelected(preset) },
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        ChooseDateTimeRow(
            textColor = textColor,
            secondaryColor = secondaryColor,
            background = optionColor,
            border = borderColor,
            onClick = onChooseCustom,
        )

        Spacer(modifier = Modifier.height(19.dp))

        RemoveReminderButton(darkTheme = darkTheme, onClick = onRemove)

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun ChooseDateTimeRow(
    textColor: Color,
    secondaryColor: Color,
    background: Color,
    border: Color,
    onClick: () -> Unit,
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(11.dp),
        color = background,
        border = BorderStroke(1.dp, border),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_calendar),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(20.dp),
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = stringResource(R.string.reminder_choose_date_time),
                color = textColor,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "›", color = secondaryColor, fontSize = 26.sp)
        }
    }
}

@Composable
private fun RemoveReminderButton(darkTheme: Boolean, onClick: () -> Unit) {
    Surface(onClick = onClick, color = Color.Transparent) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(R.string.reminder_remove),
                color = if (darkTheme) DarkRemoveText else LightRemoveText,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Suppress("LongParameterList")
@Composable
private fun ReminderOptionRow(
    label: String,
    time: String,
    textColor: Color,
    secondaryColor: Color,
    background: Color,
    border: Color,
    onClick: () -> Unit,
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(11.dp),
        color = background,
        border = BorderStroke(1.dp, border),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = label, color = textColor, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = time, color = secondaryColor, fontSize = 14.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
@Suppress("UnusedPrivateMember")
private fun ReminderSheetLightPreview() {
    PicmorrowTheme(darkTheme = false) {
        Surface(color = LightSheetBackground) {
            ReminderSheetContent(false, ZonedDateTime.now(), {}, {}, {})
        }
    }
}

@Preview(showBackground = true)
@Composable
@Suppress("UnusedPrivateMember")
private fun ReminderSheetDarkPreview() {
    PicmorrowTheme(darkTheme = true) {
        Surface(color = DarkSheetBackground) {
            ReminderSheetContent(true, ZonedDateTime.now(), {}, {}, {})
        }
    }
}

private val LightSheetBackground = Color(0xFFFEFCFA)
private val DarkSheetBackground = Color(0xFF272727)
private val LightOptionBorder = Color(0xFFE7E2DD)
private val DarkOptionBorder = Color(0xFF393939)
private val DarkOptionBackground = Color(0xFF1E1E1E)
private val LightHandle = Color(0xFFE5E3E0)
private val DarkHandle = Color(0xFF3B3B3B)
private val LightRemoveText = Color(0xFFBE3E37)
private val DarkRemoveText = Color(0xFFFF6B61)
private val SHEET_CORNER_RADIUS = 22.dp
