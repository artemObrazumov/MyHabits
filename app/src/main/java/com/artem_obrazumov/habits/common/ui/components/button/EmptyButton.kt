package com.artem_obrazumov.habits.common.ui.components.button

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import com.artem_obrazumov.habits.common.ui.theme.disabledOutlineColor
import com.artem_obrazumov.habits.common.ui.theme.primaryContentColor

@Composable
fun EmptyButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    androidx.compose.material3.Button(
        modifier = modifier,
        onClick = onClick,
        border = ButtonDefaults.outlinedButtonBorder().copy(
            brush = SolidColor(disabledOutlineColor())
        ),
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = Color.Transparent,
            contentColor = primaryContentColor()
        ),
        shape = MaterialTheme.shapes.small
    ) {
        content()
    }
}