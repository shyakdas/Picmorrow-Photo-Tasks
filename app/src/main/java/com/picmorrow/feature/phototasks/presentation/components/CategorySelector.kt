package com.picmorrow.feature.phototasks.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.picmorrow.feature.phototasks.presentation.common.model.PhotoTaskCategory
import com.picmorrow.feature.phototasks.presentation.screen.NewPhotoTaskColors

@Composable
internal fun CategorySelector(
    selectedCategory: PhotoTaskCategory,
    colors: NewPhotoTaskColors,
    onCategorySelected: (PhotoTaskCategory) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(CATEGORY_SPACING),
    ) {
        PhotoTaskCategory.entries.forEach { category ->
            CategoryChip(
                category = category,
                selected = category == selectedCategory,
                colors = colors,
                onClick = { onCategorySelected(category) },
            )
        }
    }
}

@Preview(
    name = "Category selector - Light",
    showBackground = true,
    widthDp = 393,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Category selector - Dark",
    showBackground = true,
    widthDp = 393,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun CategorySelectorPreview() {
    NewPhotoTaskPreviewSurface { colors ->
        CategorySelector(
            selectedCategory = PhotoTaskCategory.Buy,
            colors = colors,
            onCategorySelected = {},
        )
    }
}

private val CATEGORY_SPACING = 10.dp
