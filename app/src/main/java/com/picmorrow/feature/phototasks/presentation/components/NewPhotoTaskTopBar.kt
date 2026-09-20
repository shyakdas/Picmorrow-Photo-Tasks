package com.picmorrow.feature.phototasks.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.picmorrow.R
import com.picmorrow.feature.phototasks.presentation.screen.NewPhotoTaskColors
import com.picmorrow.ui.theme.Coral

@Composable
internal fun NewPhotoTaskTopBar(
    colors: NewPhotoTaskColors,
    onCancelClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.new_photo_task_title),
            color = colors.primaryText,
            fontSize = SCREEN_TITLE_TEXT_SIZE,
            lineHeight = SCREEN_TITLE_LINE_HEIGHT,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.weight(1f))

        Surface(
            onClick = onCancelClick,
            color = Color.Transparent,
        ) {
            Text(
                text = stringResource(R.string.new_photo_task_cancel),
                color = Coral,
                fontSize = CANCEL_TEXT_SIZE,
                lineHeight = CANCEL_LINE_HEIGHT,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Preview(
    name = "New photo task top bar - Light",
    showBackground = true,
    widthDp = 393,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "New photo task top bar - Dark",
    showBackground = true,
    widthDp = 393,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun NewPhotoTaskTopBarPreview() {
    NewPhotoTaskPreviewSurface { colors ->
        NewPhotoTaskTopBar(colors = colors, onCancelClick = {})
    }
}

private val SCREEN_TITLE_TEXT_SIZE = 24.sp
private val SCREEN_TITLE_LINE_HEIGHT = 30.sp
private val CANCEL_TEXT_SIZE = 16.sp
private val CANCEL_LINE_HEIGHT = 20.sp
