package com.picmorrow.feature.onboarding.presentation.screen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.picmorrow.R
import com.picmorrow.feature.onboarding.presentation.WelcomeViewModel
import com.picmorrow.feature.onboarding.presentation.components.WelcomeActions
import com.picmorrow.feature.onboarding.presentation.components.WelcomeCopy
import com.picmorrow.feature.onboarding.presentation.components.WelcomeHero
import com.picmorrow.feature.onboarding.presentation.model.WelcomeUiState
import com.picmorrow.ui.theme.PicmorrowTheme

@Composable
fun WelcomeRoute(
    onTakeFirstPhotoClick: () -> Unit,
    onExploreFirstClick: () -> Unit,
    modifier: Modifier = Modifier,
    darkTheme: Boolean = isSystemInDarkTheme(),
    viewModel: WelcomeViewModel = viewModel(),
) {
    WelcomeScreen(
        uiState = viewModel.uiState(darkTheme),
        onTakeFirstPhotoClick = onTakeFirstPhotoClick,
        onExploreFirstClick = onExploreFirstClick,
        modifier = modifier,
    )
}

@Composable
fun WelcomeScreen(
    uiState: WelcomeUiState,
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
            WelcomeHero(heroRes = uiState.heroRes)

            Spacer(modifier = Modifier.height(CONTENT_TOP_SPACING))

            WelcomeCopy(
                secondaryTextColor = uiState.secondaryTextColor,
            )

            Spacer(modifier = Modifier.weight(1f))

            WelcomeActions(
                onTakeFirstPhotoClick = onTakeFirstPhotoClick,
                onExploreFirstClick = onExploreFirstClick,
                secondaryTextColor = uiState.secondaryTextColor,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
@Suppress("UnusedPrivateMember")
private fun WelcomeScreenLightPreview() {
    PicmorrowTheme(darkTheme = false) {
        WelcomeScreen(
            uiState = WelcomeUiState(
                heroRes = R.drawable.onboarding_hero_light,
                secondaryTextColor = com.picmorrow.ui.theme.LightSecondaryText,
            ),
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
        WelcomeScreen(
            uiState = WelcomeUiState(
                heroRes = R.drawable.onboarding_hero_dark,
                secondaryTextColor = com.picmorrow.ui.theme.DarkSecondaryText,
            ),
            onTakeFirstPhotoClick = {},
            onExploreFirstClick = {},
        )
    }
}

private val SCREEN_HORIZONTAL_PADDING = 32.dp
private val CONTENT_TOP_SPACING = 72.dp
