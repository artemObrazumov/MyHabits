package com.artem_obrazumov.habits.features.habits.presentation.habit_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.artem_obrazumov.habits.common.ui.util.collectEffect

@Composable
fun HabitDetailsScreen(
    viewModel: HabitDetailsScreenViewModel,
    modifier: Modifier = Modifier,
    menu: @Composable (() -> Unit) = {},
    onHabitTitleLoaded: (String) -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    viewModel.effect.collectEffect { effect ->
        when (effect) {
            is HabitDetailsScreenEffect.UpdateHabitName -> {
                onHabitTitleLoaded(effect.name)
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        menu()
    }
}