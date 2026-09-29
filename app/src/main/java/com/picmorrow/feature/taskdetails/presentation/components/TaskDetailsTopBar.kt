@file:Suppress("MagicNumber")

package com.picmorrow.feature.taskdetails.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.picmorrow.R
import com.picmorrow.ui.theme.PicmorrowTheme

@Composable
internal fun TaskDetailsTopBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        IconButton(onClick = onBackClick) {
            Icon(painterResource(R.drawable.ic_arrow_back), stringResource(R.string.task_details_back))
        }
        Text(stringResource(R.string.task_details_title), fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.size(48.dp))
    }
}

@Preview(name = "Task details top bar - Light", widthDp = 393, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Task details top bar - Dark", widthDp = 393, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
@Suppress("UnusedPrivateMember")
private fun TaskDetailsTopBarPreview() {
    val darkTheme = androidx.compose.foundation.isSystemInDarkTheme()
    PicmorrowTheme(darkTheme) {
        Surface(color = MaterialTheme.colorScheme.background) {
            TaskDetailsTopBar(onBackClick = {})
        }
    }
}
