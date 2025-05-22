package com.artem_obrazumov.habits.common.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.artem_obrazumov.habits.common.ui.components.text.MediumTitle
import com.artem_obrazumov.habits.common.ui.components.text.RegularText
import com.artem_obrazumov.habits.common.ui.screens.EmptyStateScreenTags.EMPTY_STATE_SCREEN

@Composable
fun EmptyStateScreen(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(48.dp)
            .testTag(EMPTY_STATE_SCREEN),
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MediumTitle(
            text = title,
            textAlign = TextAlign.Center
        )
        RegularText(
            text = subtitle,
            textAlign = TextAlign.Center
        )
    }
}

object EmptyStateScreenTags {

    const val EMPTY_STATE_SCREEN = "empty_state_screen"
}