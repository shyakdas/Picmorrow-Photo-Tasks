@file:Suppress("MagicNumber")

package com.picmorrow.feature.phototasks.presentation.common.components

import android.content.res.Configuration
import android.graphics.ImageDecoder
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.picmorrow.R
import com.picmorrow.ui.theme.PicmorrowTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

@Composable
internal fun CapturedPhotoImage(
    photoPath: String,
    modifier: Modifier = Modifier,
    maxPreviewSize: Int = PHOTO_PREVIEW_MAX_SIZE,
) {
    val imageBitmap by produceState<ImageBitmap?>(initialValue = null, photoPath, maxPreviewSize) {
        value = withContext(Dispatchers.IO) {
            runCatching {
                val photoFile = File(photoPath)
                if (!photoFile.isFile) return@runCatching null
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(photoFile)) { decoder, info, _ ->
                    val largestDimension = maxOf(info.size.width, info.size.height)
                    decoder.setTargetSampleSize(maxOf(1, largestDimension / maxPreviewSize))
                }.asImageBitmap()
            }.getOrNull()
        }
    }

    val loadedBitmap = imageBitmap
    if (loadedBitmap != null) {
        Image(
            bitmap = loadedBitmap,
            contentDescription = stringResource(R.string.new_photo_task_photo_content_description),
            modifier = modifier,
            contentScale = ContentScale.Crop,
        )
    } else {
        Box(
            modifier = modifier.background(PhotoPlaceholderBackground),
        )
    }
}

@Preview(
    name = "Captured photo image - Light",
    showBackground = true,
    widthDp = 393,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Captured photo image - Dark",
    showBackground = true,
    widthDp = 393,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun CapturedPhotoImagePreview() {
    PicmorrowTheme(darkTheme = isSystemInDarkTheme()) {
        Surface(color = MaterialTheme.colorScheme.background) {
            CapturedPhotoImage(
                photoPath = "",
                modifier = Modifier.size(width = 340.dp, height = 255.dp),
            )
        }
    }
}

private const val PHOTO_PREVIEW_MAX_SIZE = 1200
private val PhotoPlaceholderBackground = Color(0xFFB8A58C)
