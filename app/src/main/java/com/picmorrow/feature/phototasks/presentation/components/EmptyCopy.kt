@file:Suppress("MagicNumber")

package com.picmorrow.feature.phototasks.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.picmorrow.R
import com.picmorrow.feature.phototasks.presentation.model.PhotoTaskEmptyStateConfig
import com.picmorrow.ui.theme.DarkEmptyIconBackground
import com.picmorrow.ui.theme.DarkSecondaryText
import com.picmorrow.ui.theme.DarkText
import com.picmorrow.ui.theme.LightEmptyIconBackground
import com.picmorrow.ui.theme.LightSecondaryText
import com.picmorrow.ui.theme.LightText
import com.picmorrow.ui.theme.PicmorrowTheme

@Composable
internal fun EmptyCopy(
    config: PhotoTaskEmptyStateConfig,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(config.titleRes),
            color = config.primaryTextColor,
            fontSize = EMPTY_TITLE_TEXT_SIZE,
            lineHeight = EMPTY_TITLE_LINE_HEIGHT,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(EMPTY_BODY_TOP_SPACING))

        Text(
            text = stringResource(config.bodyRes),
            color = config.secondaryTextColor,
            fontSize = EMPTY_BODY_TEXT_SIZE,
            lineHeight = EMPTY_BODY_LINE_HEIGHT,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(
    name = "Empty Copy - Light",
    showBackground = true,
    widthDp = COMPONENT_PREVIEW_WIDTH,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun EmptyCopyLightPreview() {
    PicmorrowTheme(darkTheme = false) {
        EmptyCopyPreviewContent(darkTheme = false)
    }
}

@Preview(
    name = "Empty Copy - Dark",
    showBackground = true,
    widthDp = COMPONENT_PREVIEW_WIDTH,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun EmptyCopyDarkPreview() {
    PicmorrowTheme(darkTheme = true) {
        EmptyCopyPreviewContent(darkTheme = true)
    }
}

@Composable
private fun EmptyCopyPreviewContent(darkTheme: Boolean) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.padding(COMPONENT_PREVIEW_PADDING),
    ) {
        EmptyCopy(
            config = previewEmptyStateConfig(darkTheme),
        )
    }
}

internal fun previewEmptyStateConfig(darkTheme: Boolean): PhotoTaskEmptyStateConfig =
    PhotoTaskEmptyStateConfig(
        titleRes = R.string.active_empty_title,
        bodyRes = R.string.active_empty_body,
        actionRes = R.string.active_take_photo,
        iconRes = R.drawable.ic_empty_camera,
        iconBackgroundColor = if (darkTheme) DarkEmptyIconBackground else LightEmptyIconBackground,
        primaryTextColor = if (darkTheme) DarkText else LightText,
        secondaryTextColor = if (darkTheme) DarkSecondaryText else LightSecondaryText,
    )

private val EMPTY_BODY_TOP_SPACING = 14.dp
private val EMPTY_TITLE_TEXT_SIZE = 24.sp
private val EMPTY_TITLE_LINE_HEIGHT = 29.sp
private val EMPTY_BODY_TEXT_SIZE = 15.sp
private val EMPTY_BODY_LINE_HEIGHT = 22.5.sp
private val COMPONENT_PREVIEW_PADDING = 24.dp
private const val COMPONENT_PREVIEW_WIDTH = 411
