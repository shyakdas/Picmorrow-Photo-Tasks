package com.picmorrow.feature.onboarding.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.picmorrow.R
import com.picmorrow.feature.onboarding.presentation.WelcomeViewModel
import com.picmorrow.feature.onboarding.presentation.components.WelcomeActions
import com.picmorrow.feature.onboarding.presentation.components.WelcomeCopy
import com.picmorrow.feature.onboarding.presentation.components.WelcomeHero
import com.picmorrow.feature.onboarding.presentation.model.WelcomeUiState
import com.picmorrow.ui.theme.PicmorrowTheme
import com.picmorrow.ui.theme.isPicmorrowDarkTheme
import com.picmorrow.ui.adaptive.LocalAdaptiveLayoutInfo
import org.koin.androidx.compose.koinViewModel

@Composable
fun WelcomeRoute(
    onTakeFirstPhotoClick: () -> Unit,
    onExploreFirstClick: () -> Unit,
    modifier: Modifier = Modifier,
    darkTheme: Boolean = isPicmorrowDarkTheme(),
    viewModel: WelcomeViewModel = koinViewModel(),
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
    val adaptiveInfo = LocalAdaptiveLayoutInfo.current
    val useHorizontalLayout = adaptiveInfo.useWideLayout && !adaptiveInfo.isTabletop

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        if (useHorizontalLayout) {
            WelcomeWideContent(uiState, onTakeFirstPhotoClick, onExploreFirstClick)
        } else if (adaptiveInfo.isShortHeight) {
            WelcomeShortContent(uiState, onTakeFirstPhotoClick, onExploreFirstClick)
        } else {
            WelcomePortraitContent(uiState, onTakeFirstPhotoClick, onExploreFirstClick)
        }
    }
}

@Composable
private fun WelcomeWideContent(
    uiState: WelcomeUiState,
    onTakeFirstPhotoClick: () -> Unit,
    onExploreFirstClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = WIDE_SCREEN_PADDING, vertical = WIDE_VERTICAL_PADDING),
    ) {
        WelcomeHero(
            heroRes = uiState.heroRes,
            compact = true,
            modifier = Modifier.weight(1f).fillMaxHeight(),
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .widthIn(max = WIDE_CONTENT_MAX_WIDTH)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = WIDE_PANE_PADDING),
        ) {
            Spacer(Modifier.height(WIDE_CONTENT_SPACING))
            WelcomeCopy(secondaryTextColor = uiState.secondaryTextColor)
            Spacer(modifier = Modifier.height(WIDE_CONTENT_SPACING))
            WelcomeActions(onTakeFirstPhotoClick, onExploreFirstClick, uiState.secondaryTextColor)
        }
    }
}

@Composable
private fun WelcomeShortContent(
    uiState: WelcomeUiState,
    onTakeFirstPhotoClick: () -> Unit,
    onExploreFirstClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = SCREEN_HORIZONTAL_PADDING),
    ) {
        WelcomeHero(heroRes = uiState.heroRes, compact = true)
        Spacer(modifier = Modifier.height(COMPACT_LANDSCAPE_SPACING))
        WelcomeCopy(secondaryTextColor = uiState.secondaryTextColor)
        Spacer(modifier = Modifier.height(COMPACT_LANDSCAPE_SPACING))
        WelcomeActions(onTakeFirstPhotoClick, onExploreFirstClick, uiState.secondaryTextColor)
    }
}

@Composable
private fun WelcomePortraitContent(
    uiState: WelcomeUiState,
    onTakeFirstPhotoClick: () -> Unit,
    onExploreFirstClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = SCREEN_HORIZONTAL_PADDING),
    ) {
        WelcomeHero(heroRes = uiState.heroRes)
        Spacer(modifier = Modifier.height(CONTENT_TOP_SPACING))
        WelcomeCopy(secondaryTextColor = uiState.secondaryTextColor)
        Spacer(modifier = Modifier.weight(1f))
        WelcomeActions(onTakeFirstPhotoClick, onExploreFirstClick, uiState.secondaryTextColor)
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
private val WIDE_SCREEN_PADDING = 48.dp
private val WIDE_VERTICAL_PADDING = 20.dp
private val WIDE_PANE_PADDING = 32.dp
private val WIDE_CONTENT_SPACING = 32.dp
private val WIDE_CONTENT_MAX_WIDTH = 520.dp
private val COMPACT_LANDSCAPE_SPACING = 24.dp
