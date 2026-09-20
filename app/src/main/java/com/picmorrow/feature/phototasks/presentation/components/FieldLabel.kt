package com.picmorrow.feature.phototasks.presentation.components

import android.content.res.Configuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.picmorrow.R
import com.picmorrow.feature.phototasks.presentation.screen.NewPhotoTaskColors

@Composable
internal fun FieldLabel(
    text: String,
    colors: NewPhotoTaskColors,
) {
    Text(
        text = text,
        color = colors.secondaryText,
        fontSize = LABEL_TEXT_SIZE,
        lineHeight = LABEL_LINE_HEIGHT,
        fontWeight = FontWeight.Bold,
    )
}

@Preview(
    name = "Field label - Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Field label - Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun FieldLabelPreview() {
    NewPhotoTaskPreviewSurface { colors ->
        FieldLabel(text = stringResource(R.string.new_photo_task_category_label), colors = colors)
    }
}

private val LABEL_TEXT_SIZE = 14.sp
private val LABEL_LINE_HEIGHT = 18.sp
