package com.picmorrow.feature.phototasks.presentation.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import com.picmorrow.feature.phototasks.domain.model.PhotoTask
import com.picmorrow.feature.phototasks.presentation.common.model.PhotoTaskListColors

@Composable
@Suppress("LongParameterList")
internal fun PhotoTaskList(
    tasks: List<PhotoTask>,
    colors: PhotoTaskListColors,
    darkTheme: Boolean,
    onCompleteClick: ((Long) -> Unit)?,
    modifier: Modifier = Modifier,
    bottomPadding: Dp = 100.dp,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(top = 8.dp, bottom = bottomPadding),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(tasks, key = PhotoTask::id) { task ->
            PhotoTaskCard(
                task = task,
                colors = colors,
                darkTheme = darkTheme,
                onCompleteClick = onCompleteClick?.let { { it(task.id) } },
            )
        }
    }
}
