package com.picmorrow.feature.phototasks.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.maxTextLength
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.picmorrow.R
import com.picmorrow.feature.phototasks.presentation.screen.NewPhotoTaskColors
import com.picmorrow.ui.theme.Coral

@Suppress("LongParameterList")
@Composable
internal fun LabeledInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    minHeight: androidx.compose.ui.unit.Dp,
    maxLength: Int,
    colors: NewPhotoTaskColors,
    singleLine: Boolean = true,
) {
    val count = value.codePointCount(0, value.length)
    val atLimit = count == maxLength

    FieldLabel(text = label, colors = colors)

    Spacer(modifier = Modifier.height(LABEL_BOTTOM_SPACING))

    BasicTextField(
        value = value,
        onValueChange = { updatedValue ->
            if (updatedValue.codePointCount(0, updatedValue.length) <= maxLength) {
                onValueChange(updatedValue)
            }
        },
        singleLine = singleLine,
        cursorBrush = SolidColor(Coral),
        textStyle = TextStyle(
            color = colors.primaryText,
            fontSize = INPUT_TEXT_SIZE,
            lineHeight = INPUT_LINE_HEIGHT,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .semantics { maxTextLength = maxLength }
            .height(minHeight)
            .clip(RoundedCornerShape(INPUT_CORNER_RADIUS))
            .background(colors.inputBackground)
            .border(
                width = INPUT_BORDER_WIDTH,
                color = if (atLimit) colors.limitReached else colors.border,
                shape = RoundedCornerShape(INPUT_CORNER_RADIUS),
            )
            .padding(
                horizontal = INPUT_HORIZONTAL_PADDING,
                vertical = INPUT_VERTICAL_PADDING,
            ),
        decorationBox = { innerTextField ->
            Box(modifier = Modifier.fillMaxSize()) {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = colors.placeholderText,
                        fontSize = INPUT_TEXT_SIZE,
                        lineHeight = INPUT_LINE_HEIGHT,
                    )
                }
                innerTextField()
            }
        },
    )

    CharacterCounter(
        count = count,
        maxLength = maxLength,
        colors = colors,
        atLimit = atLimit,
    )
}

@Composable
private fun CharacterCounter(count: Int, maxLength: Int, colors: NewPhotoTaskColors, atLimit: Boolean) {
    Text(
        text = stringResource(R.string.new_photo_task_character_count, count, maxLength),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = COUNTER_TOP_SPACING),
        color = if (atLimit) colors.limitReached else colors.placeholderText,
        fontSize = COUNTER_TEXT_SIZE,
        lineHeight = COUNTER_LINE_HEIGHT,
        textAlign = TextAlign.End,
    )
}

@Preview(
    name = "Labeled input - Light",
    showBackground = true,
    widthDp = 393,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Labeled input - Dark",
    showBackground = true,
    widthDp = 393,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun LabeledInputPreview() {
    NewPhotoTaskPreviewSurface { colors ->
        Column {
            LabeledInput(
                label = stringResource(R.string.new_photo_task_title_label),
                value = "Measure kitchen drawer",
                onValueChange = {},
                placeholder = stringResource(R.string.new_photo_task_title_placeholder),
                minHeight = 52.dp,
                maxLength = 60,
                colors = colors,
            )
            Spacer(modifier = Modifier.height(12.dp))
            LabeledInput(
                label = stringResource(R.string.new_photo_task_notes_label),
                value = "",
                onValueChange = {},
                placeholder = stringResource(R.string.new_photo_task_notes_placeholder),
                minHeight = 80.dp,
                maxLength = 200,
                colors = colors,
                singleLine = false,
            )
        }
    }
}

private val LABEL_BOTTOM_SPACING = 8.dp
private val INPUT_CORNER_RADIUS = 10.dp
private val INPUT_BORDER_WIDTH = 1.dp
private val INPUT_HORIZONTAL_PADDING = 16.dp
private val INPUT_VERTICAL_PADDING = 15.dp
private val INPUT_TEXT_SIZE = 16.sp
private val INPUT_LINE_HEIGHT = 22.sp
private val COUNTER_TOP_SPACING = 4.dp
private val COUNTER_TEXT_SIZE = 12.sp
private val COUNTER_LINE_HEIGHT = 16.sp
