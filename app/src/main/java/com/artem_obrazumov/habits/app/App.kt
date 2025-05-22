package com.artem_obrazumov.habits.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.artem_obrazumov.habits.common.ui.components.DesignThemeScreen
import com.artem_obrazumov.habits.features.habits.presentation.habits_list.HabitsListScreen
import com.artem_obrazumov.habits.features.habits.presentation.habits_list.HabitsListScreenViewModel

@Composable
fun App(modifier: Modifier = Modifier) {

    val viewModel: HabitsListScreenViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    HabitsListScreen(
        state = state
    )

}