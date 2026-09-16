@file:Suppress("MagicNumber")

package com.picmorrow.feature.phototasks.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.picmorrow.feature.phototasks.presentation.model.PhotoTaskEmptyStateConfig
import com.picmorrow.ui.theme.PicmorrowTheme

@Composable
internal fun EmptyActionButton(
    config: PhotoTaskEmptyStateConfig,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onActionClick,
        modifier = modifier.height(EMPTY_BUTTON_HEIGHT),
        shape = RoundedCornerShape(percent = BUTTON_CORNER_PERCENT),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        contentPadding = ButtonDefaults.ContentPadding,
    ) {
        Icon(
            painter = painterResource(config.iconRes),
            contentDescription = null,
            modifier = Modifier.size(BUTTON_ICON_SIZE),
        )

        Spacer(modifier = Modifier.width(BUTTON_ICON_SPACING))

        Text(
            text = stringResource(config.actionRes),
            fontSize = BUTTON_TEXT_SIZE,
            lineHeight = BUTTON_LINE_HEIGHT,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview(name = "Empty Action Button - Light", showBackground = true)
@Composable
@Suppress("UnusedPrivateMember")
private fun EmptyActionButtonLightPreview() {
    PicmorrowTheme(darkTheme = false) {
        EmptyActionButtonPreviewContent(darkTheme = false)
    }
}

@Preview(name = "Empty Action Button - Dark", showBackground = true)
@Composable
@Suppress("UnusedPrivateMember")
private fun EmptyActionButtonDarkPreview() {
    PicmorrowTheme(darkTheme = true) {
        EmptyActionButtonPreviewContent(darkTheme = true)
    }
}

@Composable
private fun EmptyActionButtonPreviewContent(darkTheme: Boolean) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.padding(COMPONENT_PREVIEW_PADDING),
    ) {
        EmptyActionButton(
            config = previewEmptyStateConfig(darkTheme),
            onActionClick = {},
        )
    }
}

private val EMPTY_BUTTON_HEIGHT = 46.dp
private val BUTTON_ICON_SIZE = 18.dp
private val BUTTON_ICON_SPACING = 6.dp
private val BUTTON_TEXT_SIZE = 15.sp
private val BUTTON_LINE_HEIGHT = 20.sp
private val COMPONENT_PREVIEW_PADDING = 24.dp
private const val BUTTON_CORNER_PERCENT = 50
