@file:Suppress("MagicNumber", "LongMethod")

package com.picmorrow.feature.settings.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.picmorrow.R
import com.picmorrow.core.common.AppLinks
import com.picmorrow.ui.theme.PicmorrowTheme

@Composable
internal fun PrivacyPolicyScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current

    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            Column(
                modifier = Modifier
                    .widthIn(max = PRIVACY_CONTENT_MAX_WIDTH)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .statusBarsPadding()
                    .navigationBarsPadding(),
            ) {
                SettingsDetailTopBar(
                    title = stringResource(R.string.privacy_title),
                    onBackClick = onBackClick,
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp),
                ) {
                Text(
                    text = stringResource(R.string.privacy_effective_date),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 13.sp,
                )
                Spacer(Modifier.height(18.dp))
                Text(
                    text = stringResource(R.string.privacy_intro),
                    fontSize = 15.sp,
                    lineHeight = 23.sp,
                )
                PrivacySection(R.string.privacy_local_title, R.string.privacy_local_body)
                PrivacySection(R.string.privacy_permissions_title, R.string.privacy_permissions_body)
                PrivacySection(R.string.privacy_collection_title, R.string.privacy_collection_body)
                PrivacySection(R.string.privacy_export_title, R.string.privacy_export_body)
                PrivacySection(R.string.privacy_retention_title, R.string.privacy_retention_body)
                PrivacySection(R.string.privacy_security_title, R.string.privacy_security_body)
                PrivacySection(R.string.privacy_children_title, R.string.privacy_children_body)
                PrivacySection(R.string.privacy_changes_title, R.string.privacy_changes_body)
                PrivacyLinkRow(
                    title = stringResource(R.string.privacy_view_online),
                    icon = Icons.AutoMirrored.Filled.OpenInNew,
                    onClick = { uriHandler.openUri(AppLinks.PrivacyPolicy) },
                )
                PrivacyLinkRow(
                    title = stringResource(R.string.privacy_contact),
                    icon = Icons.Outlined.SupportAgent,
                    onClick = { uriHandler.openUri(AppLinks.Support) },
                )
                    Spacer(Modifier.height(32.dp))
                }
            }
        }
    }
}

private val PRIVACY_CONTENT_MAX_WIDTH = 720.dp

@Composable
private fun PrivacySection(titleRes: Int, bodyRes: Int) {
    Spacer(Modifier.height(24.dp))
    Text(
        text = stringResource(titleRes),
        fontSize = 18.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Bold,
    )
    Spacer(Modifier.height(8.dp))
    Text(
        text = stringResource(bodyRes),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontSize = 14.sp,
        lineHeight = 22.sp,
    )
}

@Composable
private fun PrivacyLinkRow(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.small,
    ) {
        androidx.compose.foundation.layout.Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = title,
                modifier = Modifier.padding(start = 14.dp),
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Preview(name = "Privacy - Light", widthDp = 393, heightDp = 852, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Privacy - Dark", widthDp = 393, heightDp = 852, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
@Suppress("UnusedPrivateMember")
private fun PrivacyPolicyScreenPreview() {
    val darkTheme = androidx.compose.foundation.isSystemInDarkTheme()
    PicmorrowTheme(darkTheme) {
        PrivacyPolicyScreen(onBackClick = {})
    }
}
