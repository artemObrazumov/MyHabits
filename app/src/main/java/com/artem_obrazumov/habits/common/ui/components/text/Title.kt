package com.artem_obrazumov.habits.common.ui.components.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.artem_obrazumov.habits.common.ui.theme.Typography

@Composable
fun Title(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    textAlign: TextAlign = TextAlign.Unspecified
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = Typography.titleLarge,
        overflow = overflow,
        minLines = minLines,
        maxLines = maxLines,
        textAlign = textAlign,
    )
}