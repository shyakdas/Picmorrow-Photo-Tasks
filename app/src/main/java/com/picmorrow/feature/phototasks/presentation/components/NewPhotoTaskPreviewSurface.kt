package com.picmorrow.feature.phototasks.presentation.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.picmorrow.feature.phototasks.presentation.screen.NewPhotoTaskColors
import com.picmorrow.feature.phototasks.presentation.screen.newPhotoTaskColors
import com.picmorrow.ui.theme.PicmorrowTheme

@Composable
internal fun NewPhotoTaskPreviewSurface(content: @Composable (NewPhotoTaskColors) -> Unit) {
    val darkTheme = isSystemInDarkTheme()
    val colors = newPhotoTaskColors(darkTheme)
    PicmorrowTheme(darkTheme = darkTheme) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = colors.background,
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                content(colors)
            }
        }
    }
}
