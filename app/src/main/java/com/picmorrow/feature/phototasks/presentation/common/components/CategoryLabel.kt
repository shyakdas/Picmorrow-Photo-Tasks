package com.picmorrow.feature.phototasks.presentation.common.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.picmorrow.feature.phototasks.presentation.common.model.PhotoTaskCategory

@Composable
internal fun CategoryLabel(category: PhotoTaskCategory, color: Color, compact: Boolean = false) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(category.iconRes),
            contentDescription = null,
            modifier = Modifier.size(if (compact) 13.dp else 16.dp),
            tint = color,
        )
        Spacer(modifier = Modifier.width(if (compact) 4.dp else 7.dp))
        Text(
            text = stringResource(category.labelRes),
            color = color,
            fontSize = if (compact) 12.sp else 14.sp,
            lineHeight = if (compact) 16.sp else 18.sp,
            fontWeight = FontWeight.SemiBold,
        )
    }
}
