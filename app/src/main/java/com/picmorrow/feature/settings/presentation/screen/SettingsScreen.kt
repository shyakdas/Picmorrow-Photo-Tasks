@file:Suppress("MagicNumber", "LongMethod", "LongParameterList")

package com.picmorrow.feature.settings.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.picmorrow.BuildConfig
import com.picmorrow.R
import com.picmorrow.feature.settings.domain.model.AppThemeMode
import com.picmorrow.feature.settings.presentation.model.ThemeSettingsUiState
import com.picmorrow.ui.theme.DarkBorder
import com.picmorrow.ui.theme.DarkSecondaryText
import com.picmorrow.ui.theme.DarkSurfaceRaised
import com.picmorrow.ui.theme.LightBorder
import com.picmorrow.ui.theme.LightSecondaryText
import com.picmorrow.ui.theme.PicmorrowTheme
import com.picmorrow.ui.theme.isPicmorrowDarkTheme

@Composable
internal fun SettingsScreen(
    uiState: ThemeSettingsUiState,
    notificationsAllowed: Boolean,
    isCameraShortcutPinned: Boolean,
    onBackClick: () -> Unit,
    onThemeModeSelected: (AppThemeMode) -> Unit,
    onNotificationClick: () -> Unit,
    onAddCameraShortcutClick: () -> Unit,
    modifier: Modifier = Modifier,
    darkTheme: Boolean = isPicmorrowDarkTheme(),
) {
    var showThemeSheet by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }
    var showPrivacyDialog by remember { mutableStateOf(false) }
    val colors = settingsColors(darkTheme)

    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding(),
        ) {
            SettingsTopBar(onBackClick)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
            ) {
                SettingsSectionLabel(R.string.settings_appearance, colors.secondaryText)
                Spacer(Modifier.height(10.dp))
                SettingsRow(
                    title = stringResource(R.string.settings_theme),
                    value = stringResource(uiState.selectedThemeMode.labelRes),
                    colors = colors,
                    onClick = { showThemeSheet = true },
                )

                Spacer(Modifier.height(26.dp))
                SettingsSectionLabel(R.string.settings_reminders, colors.secondaryText)
                Spacer(Modifier.height(10.dp))
                SettingsRow(
                    title = stringResource(R.string.settings_permission_status),
                    value = stringResource(
                        if (notificationsAllowed) R.string.settings_allowed else R.string.settings_not_allowed,
                    ),
                    showStatusDot = notificationsAllowed,
                    colors = colors,
                    onClick = onNotificationClick,
                )

                Spacer(Modifier.height(26.dp))
                SettingsSectionLabel(R.string.settings_camera_shortcut, colors.secondaryText)
                Spacer(Modifier.height(10.dp))
                SettingsRow(
                    title = stringResource(R.string.settings_add_to_home_screen),
                    subtitle = stringResource(R.string.settings_camera_shortcut_body),
                    showStatusDot = isCameraShortcutPinned,
                    colors = colors,
                    onClick = onAddCameraShortcutClick,
                )

                Spacer(Modifier.height(26.dp))
                SettingsSectionLabel(R.string.settings_storage, colors.secondaryText)
                Spacer(Modifier.height(12.dp))
                Text(
                    text = stringResource(R.string.settings_storage_body),
                    color = colors.secondaryText,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                )
                Spacer(Modifier.height(12.dp))
                StorageNotice(colors)

                Spacer(Modifier.height(26.dp))
                SettingsSectionLabel(R.string.settings_about, colors.secondaryText)
                Spacer(Modifier.height(10.dp))
                SettingsRow(
                    title = stringResource(R.string.settings_about_picmorrow),
                    colors = colors,
                    onClick = { showAboutDialog = true },
                )
                Spacer(Modifier.height(10.dp))
                SettingsRow(
                    title = stringResource(R.string.settings_privacy_policy),
                    colors = colors,
                    onClick = { showPrivacyDialog = true },
                )
                Spacer(Modifier.height(28.dp))
            }
        }
    }

    if (showThemeSheet) {
        ThemeBottomSheet(
            selectedMode = uiState.selectedThemeMode,
            onDismiss = { showThemeSheet = false },
            onSelected = {
                onThemeModeSelected(it)
                showThemeSheet = false
            },
        )
    }
    if (showAboutDialog) {
        InfoDialog(
            title = stringResource(R.string.settings_about_picmorrow),
            body = stringResource(R.string.settings_about_body, BuildConfig.VERSION_NAME),
            onDismiss = { showAboutDialog = false },
        )
    }
    if (showPrivacyDialog) {
        InfoDialog(
            title = stringResource(R.string.settings_privacy_policy),
            body = stringResource(R.string.settings_privacy_body),
            onDismiss = { showPrivacyDialog = false },
        )
    }
}

@Composable
private fun SettingsTopBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().height(72.dp).padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        IconButton(onClick = onBackClick) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.settings_back))
        }
        Text(
            text = stringResource(R.string.settings_title),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.size(48.dp))
    }
}

@Composable
private fun SettingsSectionLabel(labelRes: Int, color: Color) {
    Text(
        text = stringResource(labelRes),
        color = color,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
private fun SettingsRow(
    title: String,
    colors: SettingsColors,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    value: String? = null,
    subtitle: String? = null,
    showStatusDot: Boolean = false,
) {
    Surface(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        color = colors.rowBackground,
        border = BorderStroke(1.dp, colors.rowBorder),
    ) {
        Row(
            modifier = Modifier
                .heightIn(min = if (subtitle == null) 52.dp else 80.dp)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = colors.primaryText,
                    fontSize = 16.sp,
                    lineHeight = 21.sp,
                    fontWeight = FontWeight.Medium,
                )
                if (subtitle != null) {
                    Spacer(Modifier.height(5.dp))
                    Text(
                        text = subtitle,
                        color = colors.secondaryText,
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                    )
                }
            }
            if (showStatusDot) {
                Surface(modifier = Modifier.size(8.dp), shape = RoundedCornerShape(4.dp), color = AllowedGreen) {}
                Spacer(Modifier.width(8.dp))
            }
            if (value != null) {
                Text(text = value, color = colors.secondaryText, fontSize = 14.sp)
                Spacer(Modifier.width(8.dp))
            }
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = colors.chevron,
            )
        }
    }
}

@Composable
private fun StorageNotice(colors: SettingsColors) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        color = colors.noticeBackground,
        border = BorderStroke(1.dp, NoticeBorder),
    ) {
        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
            Icon(
                imageVector = Icons.Outlined.WarningAmber,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = NoticeText,
            )
            Spacer(Modifier.width(8.dp))
            Column {
                Text(
                    text = stringResource(R.string.settings_storage_notice_title),
                    color = NoticeText,
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.settings_storage_notice_body),
                    color = NoticeText,
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ThemeBottomSheet(
    selectedMode: AppThemeMode,
    onDismiss: () -> Unit,
    onSelected: (AppThemeMode) -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        ThemeSheetContent(selectedMode = selectedMode, onSelected = onSelected)
    }
}

@Composable
internal fun ThemeSheetContent(
    selectedMode: AppThemeMode,
    onSelected: (AppThemeMode) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .selectableGroup()
            .navigationBarsPadding()
            .padding(start = 24.dp, end = 24.dp, bottom = 20.dp),
    ) {
        Text(
            text = stringResource(R.string.settings_theme),
            fontSize = 22.sp,
            lineHeight = 28.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(12.dp))
        ThemeOptions.forEach { mode ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = selectedMode == mode,
                        onClick = { onSelected(mode) },
                        role = Role.RadioButton,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(mode.labelRes),
                    modifier = Modifier.weight(1f),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                )
                if (selectedMode == mode) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
    }
}

@Composable
private fun InfoDialog(title: String, body: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { Text(body, lineHeight = 21.sp) },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.settings_ok)) }
        },
    )
}

private val AppThemeMode.labelRes: Int
    get() = when (this) {
        AppThemeMode.System -> R.string.settings_theme_system
        AppThemeMode.Light -> R.string.settings_theme_light
        AppThemeMode.Dark -> R.string.settings_theme_dark
    }

private data class SettingsColors(
    val primaryText: Color,
    val secondaryText: Color,
    val rowBackground: Color,
    val rowBorder: Color,
    val chevron: Color,
    val noticeBackground: Color,
)

@Composable
private fun settingsColors(darkTheme: Boolean): SettingsColors = if (darkTheme) {
    SettingsColors(
        primaryText = MaterialTheme.colorScheme.onBackground,
        secondaryText = DarkSecondaryText,
        rowBackground = DarkSurfaceRaised,
        rowBorder = DarkBorder,
        chevron = DarkSecondaryText,
        noticeBackground = Color(0xFF302A18),
    )
} else {
    SettingsColors(
        primaryText = MaterialTheme.colorScheme.onBackground,
        secondaryText = LightSecondaryText,
        rowBackground = Color.White,
        rowBorder = LightBorder,
        chevron = Color(0xFF9C9894),
        noticeBackground = Color(0xFFFFF8E6),
    )
}

@Preview(name = "Settings - Light", widthDp = 393, heightDp = 852, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Settings - Dark", widthDp = 393, heightDp = 852, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
@Suppress("UnusedPrivateMember")
private fun SettingsScreenPreview() {
    val darkTheme = isSystemInDarkTheme()
    PicmorrowTheme(darkTheme) {
        SettingsScreen(
            uiState = ThemeSettingsUiState(),
            notificationsAllowed = true,
            isCameraShortcutPinned = true,
            onBackClick = {},
            onThemeModeSelected = {},
            onNotificationClick = {},
            onAddCameraShortcutClick = {},
            darkTheme = darkTheme,
        )
    }
}

private val AllowedGreen = Color(0xFF18AF7A)
private val NoticeBorder = Color(0xFFF0B800)
private val NoticeText = Color(0xFFC75A00)
private val ThemeOptions = listOf(AppThemeMode.System, AppThemeMode.Dark, AppThemeMode.Light)
