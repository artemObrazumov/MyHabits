package com.artem_obrazumov.habits.features.habits.presentation.habit_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.artem_obrazumov.habits.R
import com.artem_obrazumov.habits.common.ui.components.button.Button
import com.artem_obrazumov.habits.common.ui.components.text.MediumTitle
import com.artem_obrazumov.habits.common.ui.screens.FailureScreen
import com.artem_obrazumov.habits.common.ui.util.UIText
import com.artem_obrazumov.habits.common.ui.util.collectEffect
import com.artem_obrazumov.habits.features.habits.presentation.components.HabitDetailsCard

@Composable
fun HabitDetailsScreen(
    viewModel: HabitDetailsScreenViewModel,
    modifier: Modifier = Modifier,
    menu: @Composable ((UIText) -> Unit) = {}
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    viewModel.effect.collectEffect {}

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        menu(
            if (state is HabitDetailsScreenState.Content) {
                UIText.DynamicText(
                    (state as HabitDetailsScreenState.Content).habitDetails.habit.name
                )
            } else {
                UIText.StringResource(R.string.loading)
            }
        )

        HabitDetailsScreenContent(
            state = state,
            onAction = viewModel::onAction
        )
    }
}

@Composable
fun HabitDetailsScreenContent(
    state: HabitDetailsScreenState,
    modifier: Modifier = Modifier,
    onAction: (HabitDetailsScreenAction) -> Unit = {}
) {

    when (state) {
        is HabitDetailsScreenState.Content -> {
            HabitDetailsScreenContentState(
                state = state,
                modifier = modifier,
                onAction = onAction
            )
        }

        is HabitDetailsScreenState.Failure -> {
            FailureScreen(
                modifier = modifier,
                message = state.errorMessage.asString(),
                onRetry = {
                    onAction(HabitDetailsScreenAction.Retry)
                }
            )
        }
        HabitDetailsScreenState.Loading -> {

        }
    }
}

@Composable
fun HabitDetailsScreenContentState(
    state: HabitDetailsScreenState.Content,
    modifier: Modifier = Modifier,
    onAction: (HabitDetailsScreenAction) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        item {
            HabitDetailsCard(
                habitDetails = state.habitDetails,
                localUser = state.localUser
            )
        }

        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MediumTitle(
                    modifier = Modifier
                        .weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = stringResource(R.string.progress_history)
                )
                Button(
                    text = stringResource(R.string.add),
                    onClick = {
                        onAction(HabitDetailsScreenAction.AddProgress)
                    }
                )
            }
        }
    }
}