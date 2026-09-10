package com.picmorrow.feature.onboarding.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.picmorrow.R
import com.picmorrow.ui.theme.PicmorrowTheme

@Composable
internal fun WelcomeHero(
    @DrawableRes heroRes: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = HERO_TOP_PADDING),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(heroRes),
            contentDescription = null,
            modifier = Modifier.size(width = HERO_WIDTH, height = HERO_HEIGHT),
            contentScale = ContentScale.Fit,
        )
    }
}

@Preview(name = "Welcome Hero - Light", showBackground = true)
@Composable
@Suppress("UnusedPrivateMember")
private fun WelcomeHeroLightPreview() {
    PicmorrowTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            WelcomeHero(heroRes = R.drawable.onboarding_hero_light)
        }
    }
}

@Preview(name = "Welcome Hero - Dark", showBackground = true)
@Composable
@Suppress("UnusedPrivateMember")
private fun WelcomeHeroDarkPreview() {
    PicmorrowTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            WelcomeHero(heroRes = R.drawable.onboarding_hero_dark)
        }
    }
}

private val HERO_TOP_PADDING = 72.dp
private val HERO_WIDTH = 260.dp
private val HERO_HEIGHT = 228.dp
