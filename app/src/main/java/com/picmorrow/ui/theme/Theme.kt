package com.picmorrow.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

private val LocalPicmorrowDarkTheme = staticCompositionLocalOf { false }

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

    CompositionLocalProvider(LocalPicmorrowDarkTheme provides darkTheme) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content,
        )
    }
}

@Composable
fun isPicmorrowDarkTheme(): Boolean = LocalPicmorrowDarkTheme.current
