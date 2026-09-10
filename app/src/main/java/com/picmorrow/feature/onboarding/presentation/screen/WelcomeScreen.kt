package com.picmorrow.feature.onboarding.presentation.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.picmorrow.R
import com.picmorrow.ui.theme.DarkSecondaryText
import com.picmorrow.ui.theme.LightSecondaryText
import com.picmorrow.ui.theme.PicmorrowTheme

@Composable
fun WelcomeScreen(
    onTakeFirstPhotoClick: () -> Unit,
    onExploreFirstClick: () -> Unit,
    modifier: Modifier = Modifier,
    darkTheme: Boolean = isSystemInDarkTheme(),
) {
    val heroRes =
        if (darkTheme) {
            R.drawable.onboarding_hero_dark
        } else {
            R.drawable.onboarding_hero_light
        }

    WelcomeScreenContent(
        heroRes = heroRes,
        secondaryTextColor = if (darkTheme) DarkSecondaryText else LightSecondaryText,
        onTakeFirstPhotoClick = onTakeFirstPhotoClick,
        onExploreFirstClick = onExploreFirstClick,
        modifier = modifier,
    )
}

@Composable
private fun WelcomeScreenContent(
    @DrawableRes heroRes: Int,
    secondaryTextColor: Color,
    onTakeFirstPhotoClick: () -> Unit,
    onExploreFirstClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(horizontal = SCREEN_HORIZONTAL_PADDING),
        ) {
            WelcomeHero(heroRes = heroRes)

            Spacer(modifier = Modifier.height(CONTENT_TOP_SPACING))

            WelcomeCopy(
                secondaryTextColor = secondaryTextColor,
            )

            Spacer(modifier = Modifier.weight(1f))

            WelcomeActions(
                onClick = onTakeFirstPhotoClick,
                onExploreFirstClick = onExploreFirstClick,
                secondaryTextColor = secondaryTextColor,
            )
        }
    }
}

@Composable
private fun WelcomeHero(
    @DrawableRes heroRes: Int,
) {
    Box(
        modifier = Modifier
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

@Composable
private fun WelcomeCopy(
    secondaryTextColor: Color,
) {
    Text(
        text = "Photos for things\nyou need to do.",
        style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.Black,
        lineHeight = MaterialTheme.typography.headlineLarge.lineHeight,
    )

    Spacer(modifier = Modifier.height(COPY_SPACING))

    Text(
        text = "Keep everyday reminders together, then clear\n" +
            "them when you're done. No accounts, no\n" +
            "cloud, fully local.",
        style = MaterialTheme.typography.bodyLarge,
        color = secondaryTextColor,
        lineHeight = MaterialTheme.typography.bodyLarge.lineHeight,
    )
}

@Composable
private fun WelcomeActions(
    onClick: () -> Unit,
    onExploreFirstClick: () -> Unit,
    secondaryTextColor: Color,
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(PRIMARY_BUTTON_HEIGHT),
        shape = RoundedCornerShape(percent = BUTTON_CORNER_PERCENT),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
    ) {
        Text(
            text = "Take my first photo",
            style = MaterialTheme.typography.labelLarge,
        )
    }

    Spacer(modifier = Modifier.height(SECONDARY_ACTION_TOP_SPACING))

    TextButton(
        onClick = onExploreFirstClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.textButtonColors(contentColor = secondaryTextColor),
    ) {
        Text(
            text = "Explore first",
            style = MaterialTheme.typography.labelMedium,
            textDecoration = TextDecoration.Underline,
        )
    }

    Spacer(modifier = Modifier.height(BOTTOM_SPACING))
}

@Preview(showBackground = true)
@Composable
@Suppress("UnusedPrivateMember")
private fun WelcomeScreenLightPreview() {
    PicmorrowTheme(darkTheme = false) {
        WelcomeScreenContent(
            heroRes = R.drawable.onboarding_hero_light,
            secondaryTextColor = LightSecondaryText,
            onTakeFirstPhotoClick = {},
            onExploreFirstClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
@Suppress("UnusedPrivateMember")
private fun WelcomeScreenDarkPreview() {
    PicmorrowTheme(darkTheme = true) {
        WelcomeScreenContent(
            heroRes = R.drawable.onboarding_hero_dark,
            secondaryTextColor = DarkSecondaryText,
            onTakeFirstPhotoClick = {},
            onExploreFirstClick = {},
        )
    }
}

private val SCREEN_HORIZONTAL_PADDING = 32.dp
private val HERO_TOP_PADDING = 72.dp
private val HERO_WIDTH = 260.dp
private val HERO_HEIGHT = 228.dp
private val CONTENT_TOP_SPACING = 72.dp
private val COPY_SPACING = 22.dp
private val PRIMARY_BUTTON_HEIGHT = 48.dp
private val SECONDARY_ACTION_TOP_SPACING = 12.dp
private val BOTTOM_SPACING = 28.dp
private const val BUTTON_CORNER_PERCENT = 50
