package com.artem_obrazumov.habits.common.ui.components.input

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.artem_obrazumov.habits.common.ui.components.containers.InputContainer
import com.artem_obrazumov.habits.common.ui.components.text.RegularText
import com.artem_obrazumov.habits.common.ui.theme.primaryContentColor

@Composable
fun TextDropdownMenu(
    items: List<String>,
    onClick: (item: String) -> Unit,
    modifier: Modifier = Modifier,
    menuModifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
) {
    Box(
        modifier = modifier
    ) {
        var isExpanded by remember { mutableStateOf(false) }
        var value by remember { mutableStateOf(items.first()) }
        var inputWidth by remember { mutableIntStateOf(0) }

        InputContainer(
            isFocused = isExpanded,
            label = label,
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    isExpanded = true
                }
                .onGloballyPositioned { coordinates ->
                    inputWidth = coordinates.size.width
                }
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (value.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .alpha(0.6f)
                    ) {
                        placeholder?.invoke()
                    }
                }
                RegularText(
                    modifier = Modifier
                        .weight(1f),
                    text = value
                )
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = null
                )
            }
        }

        DropdownMenu(
            modifier = menuModifier
                .width(
                    with(LocalDensity.current) {
                        inputWidth.toDp()
                    }
                ),
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            offset = DpOffset.Zero,
            containerColor = MaterialTheme.colorScheme.onSurface,
        ) {
            items.forEach {
                DropdownTextItem(
                    text = it,
                    onClick = {
                        value = it
                        onClick(it)
                        isExpanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun DropdownTextItem(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = primaryContentColor()
) {
    DropdownMenuItem(
        text = {
            RegularText(
                text = text
            )
        },
        onClick = onClick,
        modifier = modifier,
        colors = MenuDefaults.itemColors().copy(
            textColor = color
        ),
        contentPadding = PaddingValues(12.dp)
    )
}