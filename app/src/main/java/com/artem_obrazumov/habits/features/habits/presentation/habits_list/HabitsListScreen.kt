package com.artem_obrazumov.habits.features.habits.presentation.habits_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavBackStack
import com.artem_obrazumov.habits.R
import com.artem_obrazumov.habits.common.ui.components.button.Fab
import com.artem_obrazumov.habits.common.ui.components.containers.ShimmerBox
import com.artem_obrazumov.habits.common.ui.screens.EmptyStateScreen
import com.artem_obrazumov.habits.common.ui.screens.FailureScreen
import com.artem_obrazumov.habits.common.ui.util.collectEffect
import com.artem_obrazumov.habits.features.habits.domain.model.Habit
import com.artem_obrazumov.habits.features.habits.presentation.components.HabitItem
import com.artem_obrazumov.habits.features.habits.presentation.habits_list.HabitsListScreenTags.HABITS_LIST_ADD_BUTTON
import com.artem_obrazumov.habits.features.habits.presentation.habits_list.HabitsListScreenTags.HABITS_LIST_CONTENT
import com.artem_obrazumov.habits.features.habits.presentation.habits_list.HabitsListScreenTags.HABITS_LIST_LOADING
import com.artem_obrazumov.habits.features.habits.presentation.routes.HabitsDetails
import com.artem_obrazumov.habits.features.habits.presentation.routes.HabitsEditor

@Composable
fun HabitsListScreen(
    backStack: NavBackStack,
    viewModel: HabitsListScreenViewModel,
    modifier: Modifier = Modifier,
    menu: @Composable (() -> Unit) = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    viewModel.effect.collectEffect { effect ->
        when(effect) {
            HabitsListScreenEffect.NavigateToAddHabitScreen -> {
                backStack.add(HabitsEditor())
            }
            is HabitsListScreenEffect.NavigateToHabitDetailsScreen -> {
                backStack.add(HabitsDetails(effect.id))
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        menu()

        HabitsListScreenContent(
            state = state,
            modifier = modifier,
            onAction = viewModel::onAction,
        )
    }
}

@Composable
fun HabitsListScreenContent(
    state: HabitsListScreenState,
    modifier: Modifier = Modifier,
    onAction: (action: HabitsListScreenAction) -> Unit = {},
) {

    when(state) {
        is HabitsListScreenState.Content -> {
            HabitsListScreenContentState(
                habits = state.habitsList,
                onAction = onAction
            )
        }
        is HabitsListScreenState.Failure -> {
            FailureScreen(
                modifier = modifier,
                message = state.errorMessage.asString(),
                onRetry = {
                    onAction(HabitsListScreenAction.Retry)
                }
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
fun HabitsListScreenContentState(
    habits: List<Habit>,
    modifier: Modifier = Modifier,
    onAction: (action: HabitsListScreenAction) -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag(HABITS_LIST_CONTENT)
    ) {
        if (habits.isEmpty()) {
            EmptyStateScreen(
                title = stringResource(R.string.habits_empty_title),
                subtitle = stringResource(R.string.habits_empty_subtitle)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(
                    items = habits,
                    key = { it.id ?: throw IllegalArgumentException("Habits without id cant appear in habits list") }
                ) {
                    HabitItem(
                        habit = it,
                        onHabitClicked = {
                            onAction(HabitsListScreenAction.OpenHabitDetails(
                                it.id ?: throw IllegalArgumentException("Habits without id cant appear in habits list")
                            ))
                        }
                    )
                }
            }
        }

        Fab(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 32.dp)
                .padding(end = 16.dp)
                .testTag(HABITS_LIST_ADD_BUTTON),
            onClick = {
                onAction(HabitsListScreenAction.AddHabit)
            }
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = stringResource(R.string.add_habit)
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
            .padding(horizontal = 16.dp)
            .testTag(HABITS_LIST_LOADING),
        verticalArrangement = Arrangement.spacedBy(16.dp)
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

object HabitsListScreenTags {

    const val HABITS_LIST_LOADING = "habits_list_loading"
    const val HABITS_LIST_CONTENT = "habits_list_content"
    const val HABITS_LIST_ADD_BUTTON = "habits_list_add_button"
}
