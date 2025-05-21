package com.artem_obrazumov.habits.features.habits.presentation.habits_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.artem_obrazumov.habits.common.ui.components.containers.ShimmerBox
import com.artem_obrazumov.habits.common.ui.screens.FailureScreen

@Composable
fun HabitsListScreen(
    state: HabitsListScreenState,
    modifier: Modifier = Modifier
) {
    when(state) {
        is HabitsListScreenState.Content -> {

        }
        is HabitsListScreenState.Failure -> {
            FailureScreen(
                modifier = modifier,
                message = state.errorMessage.asString()
            )
        }
        HabitsListScreenState.Loading -> {
            HabitsListLoadingScreen(
                modifier = modifier
            )
        }
    }
}

@Composable
fun HabitsListLoadingScreen(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(
            count = 6
        ) {
            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(196.dp)
            )
        }
    }
}