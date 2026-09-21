package com.picmorrow.feature.camera.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.picmorrow.feature.phototasks.presentation.common.model.PhotoTaskCategory
import com.picmorrow.ui.theme.PicmorrowTheme

@Composable
internal fun CameraCategorySelector(
    selectedCategory: PhotoTaskCategory,
    onCategorySelected: (PhotoTaskCategory) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = CATEGORY_ROW_HORIZONTAL_PADDING),
        horizontalArrangement = Arrangement.spacedBy(CATEGORY_CHIP_SPACING),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PhotoTaskCategory.entries.forEach { category ->
            CameraCategoryChip(
                category = category,
                selected = category == selectedCategory,
                onClick = { onCategorySelected(category) },
            )
        }
    }
}

@Preview(
    name = "Camera category selector",
    showBackground = true,
    backgroundColor = 0xFF101010,
    widthDp = 423,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun CameraCategorySelectorPreview() {
    PicmorrowTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(CameraBackground)
                .padding(vertical = 16.dp),
        ) {
            CameraCategorySelector(
                selectedCategory = PhotoTaskCategory.Remember,
                onCategorySelected = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

private val CATEGORY_ROW_HORIZONTAL_PADDING = 0.dp
private val CATEGORY_CHIP_SPACING = 8.dp
