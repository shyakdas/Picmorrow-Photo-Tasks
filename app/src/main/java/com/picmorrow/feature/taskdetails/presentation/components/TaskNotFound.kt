@file:Suppress("MagicNumber")

package com.picmorrow.feature.taskdetails.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.picmorrow.R
import com.picmorrow.ui.theme.PicmorrowTheme

@Composable
internal fun TaskNotFound(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize().statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TaskDetailsTopBar(onBackClick = onBackClick)
        Spacer(Modifier.weight(1f))
        Text(
            text = stringResource(R.string.task_details_not_found),
            modifier = Modifier.padding(horizontal = 24.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
        )
        Spacer(Modifier.weight(1f))
    }
}

@Preview(
    name = "Task not found - Light",
    widthDp = 393,
    heightDp = 851,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Task not found - Dark",
    widthDp = 393,
    heightDp = 851,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun TaskNotFoundPreview() {
    val darkTheme = isSystemInDarkTheme()
    PicmorrowTheme(darkTheme) {
        Surface(color = MaterialTheme.colorScheme.background) {
            TaskNotFound(onBackClick = {})
        }
    }
}
