package com.artem_obrazumov.habits.common.ui.components.containers

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.artem_obrazumov.habits.common.ui.theme.disabledOutlineColor

@Composable
fun InputContainer(
    modifier: Modifier = Modifier,
    isFocused: Boolean = false,
    label: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        if (label != null) {
            label()
            Spacer(
                modifier = Modifier.height(4.dp)
            )
        }
        val borderColor by animateColorAsState(
            targetValue = if (isFocused) {
                MaterialTheme.colorScheme.primary
            } else {
                disabledOutlineColor()
            }
        )
        Box(
            modifier = Modifier
                .border(
                    color = borderColor,
                    shape = MaterialTheme.shapes.small,
                    width = 1.dp
                )
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = MaterialTheme.shapes.small
                )
                .padding(12.dp)
        ) {
            content()
        }
    }
}