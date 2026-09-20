@file:Suppress("MagicNumber", "TooManyFunctions")

package com.picmorrow.feature.phototasks.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.picmorrow.R
import com.picmorrow.feature.camera.presentation.model.CameraCategory
import com.picmorrow.feature.phototasks.presentation.components.CapturedPhotoCard
import com.picmorrow.feature.phototasks.presentation.components.CategorySelector
import com.picmorrow.feature.phototasks.presentation.components.FieldLabel
import com.picmorrow.feature.phototasks.presentation.components.LabeledInput
import com.picmorrow.feature.phototasks.presentation.components.NewPhotoTaskTopBar
import com.picmorrow.feature.phototasks.presentation.components.ReminderBottomSheet
import com.picmorrow.feature.phototasks.presentation.components.ReminderRow
import com.picmorrow.feature.phototasks.presentation.components.SaveTaskButton
import com.picmorrow.feature.phototasks.presentation.components.showReminderDateTimePicker
import com.picmorrow.ui.theme.PicmorrowTheme

@Suppress("LongMethod", "LongParameterList")
@Composable
internal fun NewPhotoTaskScreen(
    photoPath: String,
    selectedCategory: CameraCategory,
    onCancelClick: () -> Unit,
    onRetakeClick: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    darkTheme: Boolean = isSystemInDarkTheme(),
) {
    val colors = newPhotoTaskColors(darkTheme)
    val context = LocalContext.current
    var title by rememberSaveable { mutableStateOf("") }
    var notes by rememberSaveable { mutableStateOf("") }
    var currentCategory by rememberSaveable { mutableStateOf(selectedCategory) }
    var reminderAtMillis by rememberSaveable { mutableStateOf<Long?>(null) }
    var showReminderSheet by rememberSaveable { mutableStateOf(false) }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = colors.background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
                .padding(horizontal = SCREEN_HORIZONTAL_PADDING),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
            ) {
                Spacer(modifier = Modifier.height(TOP_SPACING))

                NewPhotoTaskTopBar(
                    colors = colors,
                    onCancelClick = onCancelClick,
                )

                Spacer(modifier = Modifier.height(PHOTO_TOP_SPACING))

                CapturedPhotoCard(
                    photoPath = photoPath,
                    colors = colors,
                    onRetakeClick = onRetakeClick,
                )

                Spacer(modifier = Modifier.height(SECTION_TOP_SPACING))

                FieldLabel(
                    text = stringResource(R.string.new_photo_task_category_label),
                    colors = colors,
                )

                Spacer(modifier = Modifier.height(CATEGORY_LABEL_BOTTOM_SPACING))

                CategorySelector(
                    selectedCategory = currentCategory,
                    colors = colors,
                    onCategorySelected = { currentCategory = it },
                )

                Spacer(modifier = Modifier.height(FIELD_TOP_SPACING))

                LabeledInput(
                    label = stringResource(R.string.new_photo_task_title_label),
                    value = title,
                    onValueChange = { title = it },
                    placeholder = stringResource(R.string.new_photo_task_title_placeholder),
                    minHeight = TITLE_INPUT_HEIGHT,
                    colors = colors,
                )

                Spacer(modifier = Modifier.height(FIELD_TOP_SPACING))

                LabeledInput(
                    label = stringResource(R.string.new_photo_task_notes_label),
                    value = notes,
                    onValueChange = { notes = it },
                    placeholder = stringResource(R.string.new_photo_task_notes_placeholder),
                    minHeight = NOTES_INPUT_HEIGHT,
                    colors = colors,
                    singleLine = false,
                )

                Spacer(modifier = Modifier.height(REMINDER_TOP_SPACING))

                ReminderRow(
                    colors = colors,
                    reminderAtMillis = reminderAtMillis,
                    onClick = { showReminderSheet = true },
                )

                Spacer(modifier = Modifier.height(BOTTOM_SPACING))
            }

            SaveTaskButton(
                onSaveClick = onSaveClick,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(BOTTOM_SPACING))
        }
    }

    if (showReminderSheet) {
        ReminderBottomSheet(
            darkTheme = darkTheme,
            onDismiss = { showReminderSheet = false },
            onPresetSelected = { reminderTime ->
                reminderAtMillis = reminderTime.toInstant().toEpochMilli()
                showReminderSheet = false
            },
            onChooseCustom = {
                showReminderSheet = false
                showReminderDateTimePicker(context, darkTheme, reminderAtMillis) { reminderAtMillis = it }
            },
            onRemove = {
                reminderAtMillis = null
                showReminderSheet = false
            },
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
@Suppress("UnusedPrivateMember")
private fun NewPhotoTaskScreenLightPreview() {
    PicmorrowTheme(darkTheme = false) {
        NewPhotoTaskScreen(
            photoPath = "",
            selectedCategory = CameraCategory.Remember,
            onCancelClick = {},
            onRetakeClick = {},
            onSaveClick = {},
            darkTheme = false,
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
@Suppress("UnusedPrivateMember")
private fun NewPhotoTaskScreenDarkPreview() {
    PicmorrowTheme(darkTheme = true) {
        NewPhotoTaskScreen(
            photoPath = "",
            selectedCategory = CameraCategory.Remember,
            onCancelClick = {},
            onRetakeClick = {},
            onSaveClick = {},
            darkTheme = true,
        )
    }
}

private val SCREEN_HORIZONTAL_PADDING = 20.dp
private val TOP_SPACING = 18.dp
private val PHOTO_TOP_SPACING = 12.dp
private val SECTION_TOP_SPACING = 22.dp
private val CATEGORY_LABEL_BOTTOM_SPACING = 8.dp
private val FIELD_TOP_SPACING = 20.dp
private val REMINDER_TOP_SPACING = 20.dp
private val BOTTOM_SPACING = 22.dp
private val TITLE_INPUT_HEIGHT = 52.dp
private val NOTES_INPUT_HEIGHT = 80.dp
