package com.picmorrow.feature.camera.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.picmorrow.R
import com.picmorrow.feature.camera.presentation.components.CameraTopBar
import com.picmorrow.ui.theme.PicmorrowTheme
import androidx.compose.ui.tooling.preview.Preview as ComposePreview

@Composable
internal fun CameraPermissionScreen(
    onCloseClick: () -> Unit,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier,
    showRationale: Boolean = false,
    isPermanentlyDenied: Boolean = false,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            CameraTopBar(
                onCloseClick = onCloseClick,
                modifier = Modifier.align(Alignment.TopCenter),
            )

            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = PERMISSION_HORIZONTAL_PADDING),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = stringResource(
                        if (isPermanentlyDenied) {
                            R.string.camera_permission_permanent_title
                        } else {
                            R.string.camera_permission_title
                        },
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.size(PERMISSION_BODY_TOP_SPACING))

                Text(
                    text = stringResource(
                        when {
                            isPermanentlyDenied -> R.string.camera_permission_permanent_body
                            showRationale -> R.string.camera_permission_rationale_body
                            else -> R.string.camera_permission_body
                        },
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.size(PERMISSION_ACTION_TOP_SPACING))

                Button(onClick = onActionClick) {
                    Text(
                        text = stringResource(
                            if (isPermanentlyDenied) {
                                R.string.camera_permission_open_settings
                            } else {
                                R.string.camera_permission_action
                            },
                        ),
                    )
                }
            }
        }
    }
}

@ComposePreview(showBackground = true)
@Composable
@Suppress("UnusedPrivateMember")
private fun CameraPermissionScreenPreview() {
    PicmorrowTheme {
        CameraPermissionScreen(
            onCloseClick = {},
            onActionClick = {},
        )
    }
}

private val PERMISSION_HORIZONTAL_PADDING = 32.dp
private val PERMISSION_BODY_TOP_SPACING = 16.dp
private val PERMISSION_ACTION_TOP_SPACING = 28.dp
