package com.artem_obrazumov.habits.common.ui.components.bars

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBar(
    value: Float,
    total: Float,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp),
    color: Color = MaterialTheme.colorScheme.primary,
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(8.dp)
    ) {
        val fraction = value / total
        drawRoundRect(
            color = backgroundColor,
            size = this.size,
            cornerRadius = CornerRadius(this.size.height / 2)
        )
        drawRoundRect(
            color = color,
            size = this.size.copy(width = fraction * this.size.width),
            cornerRadius = CornerRadius(this.size.height / 2)
        )
    }
}

@Composable
fun ProgressBar(
    value: Int,
    total: Int,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp),
    color: Color = MaterialTheme.colorScheme.primary,
) {
    ProgressBar(
        value = value.toFloat(),
        total = total.toFloat(),
        modifier = modifier,
        backgroundColor = backgroundColor,
        color = color
    )
}
