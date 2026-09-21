package com.picmorrow.feature.phototasks.presentation.components

import com.picmorrow.feature.phototasks.presentation.common.components.CategoryLabel

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.picmorrow.feature.phototasks.presentation.common.model.PhotoTaskCategory
import com.picmorrow.feature.phototasks.presentation.screen.NewPhotoTaskColors

@Composable
internal fun CategoryChip(
    category: PhotoTaskCategory,
    selected: Boolean,
    colors: NewPhotoTaskColors,
    onClick: () -> Unit,
) {
    val contentColor = if (selected) colors.selectedChipContent else colors.secondaryText

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(CHIP_CORNER_RADIUS),
        color = if (selected) colors.selectedChipBackground else colors.inputBackground,
        border = BorderStroke(
            width = CHIP_BORDER_WIDTH,
            color = if (selected) colors.selectedChipBorder else colors.border,
        ),
    ) {
        Row(
            modifier = Modifier
                .height(CHIP_HEIGHT)
                .padding(horizontal = CHIP_HORIZONTAL_PADDING),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CategoryLabel(category = category, color = contentColor)
        }
    }
}

@Preview(
    name = "Category chip - Light",
    showBackground = true,
    widthDp = 393,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Category chip - Dark",
    showBackground = true,
    widthDp = 393,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun CategoryChipPreview() {
    NewPhotoTaskPreviewSurface { colors ->
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CategoryChip(PhotoTaskCategory.Remember, selected = true, colors = colors, onClick = {})
            CategoryChip(PhotoTaskCategory.Buy, selected = false, colors = colors, onClick = {})
        }
    }
}

private val CHIP_HEIGHT = 40.dp
private val CHIP_CORNER_RADIUS = 20.dp
private val CHIP_BORDER_WIDTH = 1.dp
private val CHIP_HORIZONTAL_PADDING = 14.dp
