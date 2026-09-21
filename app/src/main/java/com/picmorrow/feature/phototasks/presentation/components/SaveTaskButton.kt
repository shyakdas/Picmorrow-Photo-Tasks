package com.picmorrow.feature.phototasks.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.picmorrow.R
import com.picmorrow.ui.theme.Coral

@Composable
internal fun SaveTaskButton(
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSaving: Boolean = false,
) {
    Surface(
        onClick = onSaveClick,
        enabled = !isSaving,
        modifier = modifier.height(SAVE_BUTTON_HEIGHT),
        shape = RoundedCornerShape(SAVE_BUTTON_CORNER_RADIUS),
        color = Coral,
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = stringResource(if (isSaving) R.string.new_photo_task_saving else R.string.new_photo_task_save),
                color = Color.White,
                fontSize = SAVE_BUTTON_TEXT_SIZE,
                lineHeight = SAVE_BUTTON_LINE_HEIGHT,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Preview(
    name = "Save task button - Light",
    showBackground = true,
    widthDp = 393,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Save task button - Dark",
    showBackground = true,
    widthDp = 393,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun SaveTaskButtonPreview() {
    NewPhotoTaskPreviewSurface {
        SaveTaskButton(onSaveClick = {}, modifier = Modifier.fillMaxWidth())
    }
}

private val SAVE_BUTTON_HEIGHT = 48.dp
private val SAVE_BUTTON_CORNER_RADIUS = 24.dp
private val SAVE_BUTTON_TEXT_SIZE = 16.sp
private val SAVE_BUTTON_LINE_HEIGHT = 20.sp
