package com.artem_obrazumov.habits.common.ui.components.button

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.artem_obrazumov.habits.common.ui.components.text.RegularText
import com.artem_obrazumov.habits.common.ui.theme.primaryContentColor

@Composable
fun Button(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    androidx.compose.material3.Button(
        modifier = modifier,
        onClick = onClick,
        shape = MaterialTheme.shapes.small
    ) {
        CompositionLocalProvider(
            LocalContentColor provides Color.White
        ) {
            content()
        }
    }
}

@Composable
fun Button(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String
) {
    androidx.compose.material3.Button(
        modifier = modifier,
        onClick = onClick,
        shape = MaterialTheme.shapes.small
    ) {
        CompositionLocalProvider(
            LocalContentColor provides Color.White
        ) {
            RegularText(
                text = text
            )
        }
    }
}