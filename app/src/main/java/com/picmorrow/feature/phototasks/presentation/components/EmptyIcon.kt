@file:Suppress("MagicNumber")

package com.picmorrow.feature.phototasks.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.picmorrow.R
import com.picmorrow.ui.theme.DarkEmptyIconBackground
import com.picmorrow.ui.theme.LightEmptyIconBackground
import com.picmorrow.ui.theme.PicmorrowTheme

@Composable
internal fun EmptyIcon(
    iconRes: Int,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.size(EMPTY_ICON_BACKGROUND_SIZE),
        shape = CircleShape,
        color = backgroundColor,
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                modifier = Modifier.size(EMPTY_ICON_SIZE),
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Preview(name = "Empty Icon - Light", showBackground = true)
@Composable
@Suppress("UnusedPrivateMember")
private fun EmptyIconLightPreview() {
    PicmorrowTheme(darkTheme = false) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(COMPONENT_PREVIEW_PADDING),
        ) {
            EmptyIcon(
                iconRes = R.drawable.ic_empty_camera,
                backgroundColor = LightEmptyIconBackground,
            )
        }
    }
}

@Preview(name = "Empty Icon - Dark", showBackground = true)
@Composable
@Suppress("UnusedPrivateMember")
private fun EmptyIconDarkPreview() {
    PicmorrowTheme(darkTheme = true) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(COMPONENT_PREVIEW_PADDING),
        ) {
            EmptyIcon(
                iconRes = R.drawable.ic_empty_camera,
                backgroundColor = DarkEmptyIconBackground,
            )
        }
    }
}

private val EMPTY_ICON_BACKGROUND_SIZE = 100.dp
private val EMPTY_ICON_SIZE = 36.dp
private val COMPONENT_PREVIEW_PADDING = 24.dp
