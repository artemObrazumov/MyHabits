package com.artem_obrazumov.habits.common.ui.components.containers

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
private fun Modifier.shimmerEffect(
    shape: Shape = MaterialTheme.shapes.small
): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition(label = "Shimmer")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1200)
        ),
        label = "shimmer_start_offset"
    )

    return@composed background(
        brush = Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
                MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp),
                MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        ),
        shape = shape,
    ).onGloballyPositioned {
        size = it.size
    }
}

@Composable
fun ShimmerBox(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .shimmerEffect()
    )
}
