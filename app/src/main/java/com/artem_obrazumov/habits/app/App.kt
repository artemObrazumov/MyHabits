package com.artem_obrazumov.habits.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.artem_obrazumov.habits.common.ui.components.button.Button
import com.artem_obrazumov.habits.common.ui.components.button.EmptyButton
import com.artem_obrazumov.habits.common.ui.components.button.Fab
import com.artem_obrazumov.habits.common.ui.components.containers.Card
import com.artem_obrazumov.habits.common.ui.components.input.TextDropdownMenu
import com.artem_obrazumov.habits.common.ui.components.input.TextField
import com.artem_obrazumov.habits.common.ui.components.text.Label
import com.artem_obrazumov.habits.common.ui.components.text.MediumLabel
import com.artem_obrazumov.habits.common.ui.components.text.MediumTitle
import com.artem_obrazumov.habits.common.ui.components.text.RegularText
import com.artem_obrazumov.habits.common.ui.components.text.Title
import com.artem_obrazumov.habits.common.ui.theme.secondaryContentColor

@Composable
fun App(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {},
            text = "Test button"
        )
        Title(
            text = "Title",
        )
        MediumTitle(
            text = "Medium title"
        )
        RegularText(
            text = "Regular text"
        )
        RegularText(
            text = "Regular text with secondary color",
            color = secondaryContentColor()
        )
        Label(
            text = "Small label"
        )
        Card {
            RegularText(
                text = "Regular text on card"
            )
        }
        Fab(
            onClick = {}
        ) {
            Icon(
                Icons.Default.Place,
                contentDescription = null)
        }
        TextField(
            value = "Uneditable text",
            onValueChange = {}
        )
        var text by remember { mutableStateOf("") }
        TextField(
            value = text,
            onValueChange = { text = it },
            label = {
                MediumLabel(
                    text = "Text field with label"
                )
            },
            placeholder = {
                RegularText(
                    text = "Placeholder text"
                )
            }
        )
        TextDropdownMenu(
            items = listOf("1", "2", "3"),
            label = {
                MediumLabel(
                    text = "Dropdown menu"
                )
            },
            onClick = {}
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            EmptyButton(
                modifier = Modifier.weight(1f),
                onClick = {}
            ) {
                RegularText(
                    text = "Empty"
                )
            }

            Button(
                modifier = Modifier.weight(1f),
                onClick = {},
                text = "Filled"
            )
        }
    }
}