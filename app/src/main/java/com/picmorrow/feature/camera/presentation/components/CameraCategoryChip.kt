@file:Suppress("MagicNumber")

package com.picmorrow.feature.camera.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.picmorrow.feature.phototasks.presentation.common.model.PhotoTaskCategory
import com.picmorrow.ui.theme.PicmorrowTheme

@Composable
internal fun CameraCategoryChip(
    category: PhotoTaskCategory,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val contentColor = if (selected) CategorySelectedContent else CategoryUnselectedContent

    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(CATEGORY_CHIP_CORNER_RADIUS),
        color = if (selected) CategorySelectedBackground else CategoryUnselectedBackground,
        border = BorderStroke(
            width = CATEGORY_CHIP_BORDER_WIDTH,
            color = if (selected) CategorySelectedBorder else CategoryUnselectedBorder,
        ),
    ) {
        Row(
            modifier = Modifier
                .width(category.chipWidth)
                .padding(
                    horizontal = CATEGORY_CHIP_HORIZONTAL_PADDING,
                    vertical = CATEGORY_CHIP_VERTICAL_PADDING,
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(category.iconRes),
                contentDescription = null,
                modifier = Modifier.size(CATEGORY_ICON_SIZE),
                tint = contentColor,
            )
            Spacer(modifier = Modifier.size(CATEGORY_ICON_SPACING))
            Text(
                text = stringResource(category.labelRes),
                color = contentColor,
                fontSize = CATEGORY_TEXT_SIZE,
                lineHeight = CATEGORY_TEXT_LINE_HEIGHT,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
            )
        }
    }
}

@Preview(
    name = "Camera category chip",
    showBackground = true,
    backgroundColor = 0xFF101010,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun CameraCategoryChipPreview() {
    PicmorrowTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .background(CameraBackground)
                .padding(16.dp),
        ) {
            CameraCategoryChip(
                category = PhotoTaskCategory.Remember,
                selected = true,
                onClick = {},
            )
        }
    }
}

private val CategorySelectedBackground = Color(0xFFE8FFFD)
private val CategorySelectedBorder = Color(0xFF00A7A1)
private val CategorySelectedContent = Color(0xFF087A77)
private val CategoryUnselectedBackground = Color.White
private val CategoryUnselectedBorder = Color(0xFFE8E5E1)
private val CategoryUnselectedContent = Color(0xFF615E5B)
private val CATEGORY_CHIP_CORNER_RADIUS = 20.dp
private val CATEGORY_CHIP_BORDER_WIDTH = 1.dp
private val CATEGORY_CHIP_HORIZONTAL_PADDING = 12.dp
private val CATEGORY_CHIP_VERTICAL_PADDING = 10.dp
private val CATEGORY_ICON_SIZE = 16.dp
private val CATEGORY_ICON_SPACING = 6.dp
private val CATEGORY_TEXT_SIZE = 14.sp
private val CATEGORY_TEXT_LINE_HEIGHT = 18.sp
