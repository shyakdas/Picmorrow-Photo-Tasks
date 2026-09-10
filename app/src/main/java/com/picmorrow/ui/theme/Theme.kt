package com.picmorrow.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = DarkCoral,
    background = DarkBackground,
    surface = DarkBackground,
    onPrimary = DarkText,
    onBackground = DarkText,
    onSurface = DarkText,
)

private val LightColorScheme = lightColorScheme(
    primary = Coral,
    background = LightBackground,
    surface = LightBackground,
    onPrimary = DarkText,
    onBackground = LightText,
    onSurface = LightText,
)

@Composable
fun PicmorrowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
