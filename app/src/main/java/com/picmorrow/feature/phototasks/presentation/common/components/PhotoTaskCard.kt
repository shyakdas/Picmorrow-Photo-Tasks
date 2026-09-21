@file:Suppress("MagicNumber")

package com.picmorrow.feature.phototasks.presentation.common.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.picmorrow.R
import com.picmorrow.feature.phototasks.presentation.common.model.PhotoTaskCategory
import com.picmorrow.feature.phototasks.domain.model.PhotoTask
import com.picmorrow.feature.phototasks.presentation.common.model.PhotoTaskListColors
import com.picmorrow.feature.phototasks.presentation.common.model.photoTaskListColors
import com.picmorrow.ui.theme.PicmorrowTheme
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
internal fun PhotoTaskCard(
    task: PhotoTask,
    colors: PhotoTaskListColors,
    darkTheme: Boolean,
    onCompleteClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
) {
    val category = PhotoTaskCategory.entries.firstOrNull { it.name == task.category }
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = colors.cardBackground,
        border = BorderStroke(1.dp, colors.cardBorder),
        shadowElevation = if (darkTheme) 0.dp else 2.dp,
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CapturedPhotoImage(
                photoPath = task.photoPath,
                modifier = Modifier.size(88.dp).clip(RoundedCornerShape(12.dp)),
                maxPreviewSize = 256,
            )
            Spacer(Modifier.width(16.dp))
            PhotoTaskCardDetails(
                task = task,
                category = category,
                colors = colors,
                darkTheme = darkTheme,
                modifier = Modifier.weight(1f),
            )
            if (onCompleteClick != null) {
                Spacer(Modifier.width(8.dp))
                Surface(
                    onClick = onCompleteClick,
                    modifier = Modifier.size(40.dp),
                    shape = CircleShape,
                    color = Color.Transparent,
                    border = BorderStroke(1.5.dp, colors.cardBorder),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_task_check),
                        contentDescription = stringResource(R.string.task_complete_content_description, task.title),
                        modifier = Modifier.padding(7.dp),
                        tint = Color.Unspecified,
                    )
                }
            }
        }
    }
}

@Composable
private fun PhotoTaskCardDetails(
    task: PhotoTask,
    category: PhotoTaskCategory?,
    colors: PhotoTaskListColors,
    darkTheme: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = task.title,
            color = colors.primaryText,
            fontSize = 18.sp,
            lineHeight = 22.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
        if (category != null || task.completedAtMillis != null) {
            FlowRow(
                modifier = Modifier.padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                if (category != null) CategoryBadge(category = category, darkTheme = darkTheme)
                task.completedAtMillis?.let { completedAt ->
                    Text(
                        text = stringResource(R.string.task_completed_on, formatCompletionDate(completedAt)),
                        color = colors.secondaryText,
                        modifier = Modifier.padding(top = 4.dp),
                        fontSize = 13.sp,
                        lineHeight = 16.sp,
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryBadge(category: PhotoTaskCategory, darkTheme: Boolean) {
    val (background, foreground) = when (category) {
        PhotoTaskCategory.Parking ->
            if (darkTheme) Color(0xFF263A4A) to Color(0xFF8EC3F4) else Color(0xFFFFE7E1) to Color(0xFF25558E)
        PhotoTaskCategory.Buy ->
            if (darkTheme) Color(0xFF493B24) to Color(0xFFF6C982) else Color(0xFFFFF8E9) to Color(0xFF95600C)
        PhotoTaskCategory.Collect ->
            if (darkTheme) Color(0xFF26433B) to Color(0xFF8ED7B9) else Color(0xFFE8F5ED) to Color(0xFF237454)
        PhotoTaskCategory.Remember ->
            if (darkTheme) Color(0xFF224344) to Color(0xFF83D5D3) else Color(0xFFE7F5F3) to Color(0xFF167B7E)
    }
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = background,
    ) {
        Box(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
            CategoryLabel(category = category, color = foreground, compact = true)
        }
    }
}

internal fun formatCompletionDate(
    completedAtMillis: Long,
    zoneId: ZoneId = ZoneId.systemDefault(),
    locale: Locale = Locale.getDefault(),
): String = DateTimeFormatter.ofPattern("d MMM", locale)
    .format(Instant.ofEpochMilli(completedAtMillis).atZone(zoneId))

@Preview(name = "Photo task card - Light", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "Photo task card - Dark", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
@Suppress("UnusedPrivateMember")
private fun PhotoTaskCardPreview() {
    val darkTheme = isSystemInDarkTheme()
    PicmorrowTheme(darkTheme = darkTheme) {
        PhotoTaskCard(
            task = PhotoTask(1, "", "Parking", "Car - B2, pillar C14", "", null, null),
            colors = photoTaskListColors(darkTheme),
            darkTheme = darkTheme,
            onCompleteClick = {},
        )
    }
}
