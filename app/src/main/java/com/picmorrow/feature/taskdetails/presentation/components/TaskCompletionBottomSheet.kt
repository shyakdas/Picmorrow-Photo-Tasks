@file:Suppress("MagicNumber", "LongMethod", "LongParameterList")

package com.picmorrow.feature.taskdetails.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.picmorrow.R
import com.picmorrow.ui.theme.Coral
import com.picmorrow.ui.theme.DarkBorder
import com.picmorrow.ui.theme.DarkSecondaryText
import com.picmorrow.ui.theme.DarkSurfaceRaised
import com.picmorrow.ui.theme.LightBorder
import com.picmorrow.ui.theme.LightSecondaryText
import com.picmorrow.ui.theme.PicmorrowTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TaskCompletionBottomSheet(
    isProcessing: Boolean,
    darkTheme: Boolean,
    onDismiss: () -> Unit,
    onSaveToGallery: () -> Unit,
    onDeletePhotoAndTask: () -> Unit,
    onReviewLater: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = { if (!isProcessing) onDismiss() },
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        containerColor = if (darkTheme) DarkSheetBackground else LightSheetBackground,
        scrimColor = Color.Black.copy(alpha = 0.36f),
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 18.dp, bottom = 20.dp)
                    .size(width = 36.dp, height = 4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(if (darkTheme) DarkHandle else LightHandle),
            )
        },
    ) {
        TaskCompletionSheetContent(
            isProcessing = isProcessing,
            darkTheme = darkTheme,
            onSaveToGallery = onSaveToGallery,
            onDeletePhotoAndTask = onDeletePhotoAndTask,
            onReviewLater = onReviewLater,
        )
    }
}

@Composable
private fun TaskCompletionSheetContent(
    isProcessing: Boolean,
    darkTheme: Boolean,
    onSaveToGallery: () -> Unit,
    onDeletePhotoAndTask: () -> Unit,
    onReviewLater: () -> Unit,
) {
    val primaryText = MaterialTheme.colorScheme.onBackground
    val secondaryText = if (darkTheme) DarkSecondaryText else LightSecondaryText
    val optionBackground = if (darkTheme) DarkSurfaceRaised else Color.White
    val optionBorder = if (darkTheme) DarkBorder else LightBorder

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp),
    ) {
        Text(
            text = stringResource(R.string.task_completion_title),
            color = primaryText,
            fontSize = 24.sp,
            lineHeight = 30.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.task_completion_subtitle),
            color = secondaryText,
            fontSize = 16.sp,
            lineHeight = 22.sp,
        )
        Spacer(Modifier.height(20.dp))
        CompletionActionRow(
            label = stringResource(R.string.task_completion_save_to_gallery),
            iconRes = R.drawable.ic_save_to_gallery,
            iconTint = Coral,
            textColor = primaryText,
            background = optionBackground,
            border = optionBorder,
            chevronTint = secondaryText,
            enabled = !isProcessing,
            onClick = onSaveToGallery,
        )
        Spacer(Modifier.height(12.dp))
        CompletionActionRow(
            label = stringResource(R.string.task_completion_delete_photo_and_task),
            iconRes = R.drawable.ic_delete_outline,
            iconTint = DeleteAction,
            textColor = DeleteAction,
            background = if (darkTheme) DarkDeleteBackground else LightDeleteBackground,
            border = DeleteAction,
            chevronTint = DeleteAction,
            enabled = !isProcessing,
            onClick = onDeletePhotoAndTask,
        )
        Spacer(Modifier.height(14.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .alpha(if (isProcessing) DISABLED_ALPHA else 1f)
                .clickable(enabled = !isProcessing, onClick = onReviewLater),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(R.string.task_completion_review_later),
                color = secondaryText,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun CompletionActionRow(
    label: String,
    iconRes: Int,
    iconTint: Color,
    textColor: Color,
    background: Color,
    border: Color,
    chevronTint: Color,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .alpha(if (enabled) 1f else DISABLED_ALPHA)
            .clickable(enabled = enabled, onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        color = background,
        border = BorderStroke(1.dp, border),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                modifier = Modifier.size(22.dp),
                tint = iconTint,
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = label,
                color = textColor,
                modifier = Modifier.weight(1f),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Icon(
                painter = painterResource(R.drawable.ic_chevron_right),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = chevronTint,
            )
        }
    }
}

@Preview(name = "Task completion - Light", widthDp = 393, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Task completion - Dark", widthDp = 393, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
@Suppress("UnusedPrivateMember")
private fun TaskCompletionSheetContentPreview() {
    val darkTheme = androidx.compose.foundation.isSystemInDarkTheme()
    PicmorrowTheme(darkTheme) {
        Surface(color = if (darkTheme) DarkSheetBackground else LightSheetBackground) {
            TaskCompletionSheetContent(false, darkTheme, {}, {}, {})
        }
    }
}

private const val DISABLED_ALPHA = 0.55f
private val DeleteAction = Color(0xFFD42D2D)
private val LightDeleteBackground = Color(0xFFFFF7F7)
private val DarkDeleteBackground = Color(0xFF351F1F)
private val LightSheetBackground = Color(0xFFFAF9F7)
private val DarkSheetBackground = Color(0xFF1F1F1F)
private val LightHandle = Color(0xFFE4E1DD)
private val DarkHandle = Color(0xFF4A4846)
