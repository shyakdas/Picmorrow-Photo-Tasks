package com.picmorrow.feature.onboarding.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.picmorrow.R
import com.picmorrow.ui.theme.DarkSecondaryText
import com.picmorrow.ui.theme.LightSecondaryText
import com.picmorrow.ui.theme.PicmorrowTheme

@Composable
internal fun WelcomeActions(
    onTakeFirstPhotoClick: () -> Unit,
    onExploreFirstClick: () -> Unit,
    secondaryTextColor: Color,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Button(
            onClick = onTakeFirstPhotoClick,
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
                text = stringResource(R.string.welcome_take_first_photo),
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
                text = stringResource(R.string.welcome_explore_first),
                style = MaterialTheme.typography.labelMedium,
                textDecoration = TextDecoration.Underline,
            )
        }

        Spacer(modifier = Modifier.height(BOTTOM_SPACING))
    }
}

@Preview(name = "Welcome Actions - Light", showBackground = true, widthDp = ACTIONS_PREVIEW_WIDTH)
@Composable
@Suppress("UnusedPrivateMember")
private fun WelcomeActionsLightPreview() {
    PicmorrowTheme(darkTheme = false) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(ACTIONS_PREVIEW_PADDING),
        ) {
            WelcomeActions(
                onTakeFirstPhotoClick = {},
                onExploreFirstClick = {},
                secondaryTextColor = LightSecondaryText,
            )
        }
    }
}

@Preview(name = "Welcome Actions - Dark", showBackground = true, widthDp = ACTIONS_PREVIEW_WIDTH)
@Composable
@Suppress("UnusedPrivateMember")
private fun WelcomeActionsDarkPreview() {
    PicmorrowTheme(darkTheme = true) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(ACTIONS_PREVIEW_PADDING),
        ) {
            WelcomeActions(
                onTakeFirstPhotoClick = {},
                onExploreFirstClick = {},
                secondaryTextColor = DarkSecondaryText,
            )
        }
    }
}

private val PRIMARY_BUTTON_HEIGHT = 48.dp
private val SECONDARY_ACTION_TOP_SPACING = 12.dp
private val BOTTOM_SPACING = 28.dp
private val ACTIONS_PREVIEW_PADDING = 32.dp
private const val ACTIONS_PREVIEW_WIDTH = 411
private const val BUTTON_CORNER_PERCENT = 50
