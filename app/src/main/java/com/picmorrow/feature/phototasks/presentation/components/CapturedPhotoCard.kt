@file:Suppress("MagicNumber")

package com.picmorrow.feature.phototasks.presentation.components

import com.picmorrow.feature.phototasks.presentation.common.components.CapturedPhotoImage

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.picmorrow.R
import com.picmorrow.feature.phototasks.presentation.screen.NewPhotoTaskColors

@Composable
internal fun CapturedPhotoCard(
    photoPath: String,
    colors: NewPhotoTaskColors,
    onRetakeClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(PHOTO_ASPECT_RATIO)
            .clip(RoundedCornerShape(PHOTO_CORNER_RADIUS))
            .background(colors.inputBackground),
    ) {
        CapturedPhotoImage(
            photoPath = photoPath,
            modifier = Modifier.fillMaxSize(),
        )

        Surface(
            onClick = onRetakeClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(RETAKE_PADDING),
            shape = RoundedCornerShape(RETAKE_CORNER_RADIUS),
            color = RetakeBackground,
        ) {
            Row(
                modifier = Modifier.padding(
                    horizontal = RETAKE_HORIZONTAL_PADDING,
                    vertical = RETAKE_VERTICAL_PADDING,
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_camera_off),
                    contentDescription = null,
                    modifier = Modifier.size(RETAKE_ICON_SIZE),
                    tint = Color.White,
                )

                Spacer(modifier = Modifier.width(RETAKE_ICON_SPACING))

                Text(
                    text = stringResource(R.string.new_photo_task_retake),
                    color = Color.White,
                    fontSize = RETAKE_TEXT_SIZE,
                    lineHeight = RETAKE_LINE_HEIGHT,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Preview(
    name = "Captured photo card - Light",
    showBackground = true,
    widthDp = 393,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Captured photo card - Dark",
    showBackground = true,
    widthDp = 393,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun CapturedPhotoCardPreview() {
    NewPhotoTaskPreviewSurface { colors ->
        CapturedPhotoCard(photoPath = "", colors = colors, onRetakeClick = {})
    }
}

private val PHOTO_CORNER_RADIUS = 14.dp
private const val PHOTO_ASPECT_RATIO = 1.33f
private val RETAKE_PADDING = 12.dp
private val RETAKE_CORNER_RADIUS = 20.dp
private val RETAKE_HORIZONTAL_PADDING = 10.dp
private val RETAKE_VERTICAL_PADDING = 7.dp
private val RETAKE_ICON_SIZE = 14.dp
private val RETAKE_ICON_SPACING = 5.dp
private val RETAKE_TEXT_SIZE = 13.sp
private val RETAKE_LINE_HEIGHT = 16.sp
private val RetakeBackground = Color(0xCC171717)
