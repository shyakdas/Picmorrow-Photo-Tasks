package com.picmorrow.feature.onboarding.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.picmorrow.R
import com.picmorrow.ui.theme.DarkSecondaryText
import com.picmorrow.ui.theme.LightSecondaryText
import com.picmorrow.ui.theme.PicmorrowTheme

@Composable
internal fun WelcomeCopy(
    secondaryTextColor: Color,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.welcome_title),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Black,
            lineHeight = MaterialTheme.typography.headlineLarge.lineHeight,
        )

        Spacer(modifier = Modifier.height(COPY_SPACING))

        Text(
            text = stringResource(R.string.welcome_body),
            style = MaterialTheme.typography.bodyLarge,
            color = secondaryTextColor,
            lineHeight = MaterialTheme.typography.bodyLarge.lineHeight,
        )
    }
}

@Preview(name = "Welcome Copy - Light", showBackground = true, widthDp = COPY_PREVIEW_WIDTH)
@Composable
@Suppress("UnusedPrivateMember")
private fun WelcomeCopyLightPreview() {
    PicmorrowTheme(darkTheme = false) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(COPY_PREVIEW_PADDING),
        ) {
            WelcomeCopy(secondaryTextColor = LightSecondaryText)
        }
    }
}

@Preview(name = "Welcome Copy - Dark", showBackground = true, widthDp = COPY_PREVIEW_WIDTH)
@Composable
@Suppress("UnusedPrivateMember")
private fun WelcomeCopyDarkPreview() {
    PicmorrowTheme(darkTheme = true) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(COPY_PREVIEW_PADDING),
        ) {
            WelcomeCopy(secondaryTextColor = DarkSecondaryText)
        }
    }
}

private val COPY_SPACING = 22.dp
private val COPY_PREVIEW_PADDING = 32.dp
private const val COPY_PREVIEW_WIDTH = 411
