package com.picmorrow.feature.phototasks.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.picmorrow.R
import com.picmorrow.feature.phototasks.presentation.model.PhotoTaskEmptyStateConfig
import com.picmorrow.feature.phototasks.presentation.screen.homeColors
import com.picmorrow.ui.theme.PicmorrowTheme

@Preview(
    name = "Photo Task Empty State - Light",
    showBackground = true,
    widthDp = COMPONENT_PREVIEW_WIDTH,
    heightDp = COMPONENT_PREVIEW_HEIGHT,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun PhotoTaskEmptyStateLightPreview() {
    PicmorrowTheme(darkTheme = false) {
        PhotoTaskEmptyStatePreviewContent(darkTheme = false)
    }
}

@Preview(
    name = "Photo Task Empty State - Dark",
    showBackground = true,
    widthDp = COMPONENT_PREVIEW_WIDTH,
    heightDp = COMPONENT_PREVIEW_HEIGHT,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun PhotoTaskEmptyStateDarkPreview() {
    PicmorrowTheme(darkTheme = true) {
        PhotoTaskEmptyStatePreviewContent(darkTheme = true)
    }
}

@Composable
private fun PhotoTaskEmptyStatePreviewContent(darkTheme: Boolean) {
    val colors = homeColors(darkTheme)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colors.background,
    ) {
        PhotoTaskEmptyState(
            config = PhotoTaskEmptyStateConfig(
                titleRes = R.string.active_empty_title,
                bodyRes = R.string.active_empty_body,
                actionRes = R.string.active_take_photo,
                iconRes = R.drawable.ic_empty_camera,
                iconBackgroundColor = colors.emptyIconBackground,
                primaryTextColor = colors.primaryText,
                secondaryTextColor = colors.secondaryText,
            ),
            onActionClick = {},
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
        )
    }
}

private const val COMPONENT_PREVIEW_WIDTH = 411
private const val COMPONENT_PREVIEW_HEIGHT = 520
