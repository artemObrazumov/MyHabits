package com.artem_obrazumov.habits.common.ui.components.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.artem_obrazumov.habits.common.ui.theme.Typography

@Composable
fun Label(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = Typography.labelSmall
    )
}