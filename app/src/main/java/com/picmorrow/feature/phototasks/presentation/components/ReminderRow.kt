package com.picmorrow.feature.phototasks.presentation.components

import android.content.res.Configuration
import android.text.format.DateFormat
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.picmorrow.R
import com.picmorrow.feature.phototasks.presentation.screen.NewPhotoTaskColors
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
internal fun ReminderRow(
    colors: NewPhotoTaskColors,
    reminderAtMillis: Long?,
    onClick: () -> Unit,
) {
    val context = LocalContext.current
    val reminderLabel = reminderAtMillis?.let {
        val date = Date(it)
        val day = SimpleDateFormat("MMM d", Locale.getDefault()).format(date)
        val time = DateFormat.getTimeFormat(context).format(date)
        "$day, $time"
    } ?: stringResource(R.string.new_photo_task_set_reminder)

    Surface(
        onClick = onClick,
        modifier = Modifier.testTag("reminder_row"),
        shape = RoundedCornerShape(REMINDER_CORNER_RADIUS),
        color = colors.inputBackground,
        border = BorderStroke(REMINDER_BORDER_WIDTH, colors.border),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(REMINDER_HEIGHT)
                .padding(horizontal = REMINDER_HORIZONTAL_PADDING),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_alarm_clock),
                contentDescription = null,
                modifier = Modifier.size(REMINDER_ICON_SIZE),
                tint = Color.Unspecified,
            )

            Spacer(modifier = Modifier.width(REMINDER_TEXT_SPACING))

            Text(
                text = reminderLabel,
                color = colors.primaryText,
                fontSize = REMINDER_TEXT_SIZE,
                lineHeight = REMINDER_LINE_HEIGHT,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Text(
                text = "›",
                color = colors.secondaryText,
                fontSize = CHEVRON_TEXT_SIZE,
                lineHeight = CHEVRON_LINE_HEIGHT,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Preview(
    name = "Reminder row - Light",
    showBackground = true,
    widthDp = 393,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Reminder row - Dark",
    showBackground = true,
    widthDp = 393,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun ReminderRowPreview() {
    NewPhotoTaskPreviewSurface { colors ->
        ReminderRow(colors = colors, reminderAtMillis = null, onClick = {})
    }
}

private val REMINDER_HEIGHT = 56.dp
private val REMINDER_CORNER_RADIUS = 10.dp
private val REMINDER_BORDER_WIDTH = 1.dp
private val REMINDER_HORIZONTAL_PADDING = 16.dp
private val REMINDER_ICON_SIZE = 20.dp
private val REMINDER_TEXT_SPACING = 12.dp
private val REMINDER_TEXT_SIZE = 16.sp
private val REMINDER_LINE_HEIGHT = 20.sp
private val CHEVRON_TEXT_SIZE = 28.sp
private val CHEVRON_LINE_HEIGHT = 28.sp
