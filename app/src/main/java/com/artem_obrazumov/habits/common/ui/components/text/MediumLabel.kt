package com.artem_obrazumov.habits.common.ui.components.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.artem_obrazumov.habits.common.ui.theme.Typography
import com.artem_obrazumov.habits.common.ui.theme.primaryContentColor

@Composable
fun MediumLabel(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = primaryContentColor()
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = Typography.labelMedium
    )
}