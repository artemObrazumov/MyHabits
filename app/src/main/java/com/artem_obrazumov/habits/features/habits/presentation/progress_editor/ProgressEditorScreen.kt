package com.artem_obrazumov.habits.features.habits.presentation.progress_editor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.artem_obrazumov.habits.R
import com.artem_obrazumov.habits.common.ui.components.input.TextField
import com.artem_obrazumov.habits.common.ui.components.text.Label
import com.artem_obrazumov.habits.common.ui.components.text.RegularText
import com.artem_obrazumov.habits.common.ui.util.collectEffect
import com.artem_obrazumov.habits.features.habits.domain.model.Habit

@Composable
fun ProgressEditorScreen(
    viewModel: ProgressEditorScreenViewModel,
    modifier: Modifier = Modifier,
    menu: @Composable (() -> Unit) = {}
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    viewModel.effect.collectEffect {  }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        menu()

        ProgressEditorScreenContent(
            state = state,
            onAction = viewModel::onAction
        )
    }
}

@Composable
fun ProgressEditorScreenContent(
    state: ProgressEditorScreenState,
    modifier: Modifier = Modifier,
    onAction: (ProgressEditorScreenAction) -> Unit = {}
) {

    when (state) {
        is ProgressEditorScreenState.Content -> {
            ProgressEditorContentState(
                habit = state.habit,
                formState = state.formState,
                loadingState = state.loadingState,
                onAction = onAction
            )
        }
        ProgressEditorScreenState.Loading -> {
            ProgressEditorLoading(
                modifier = modifier
            )
        }
        is ProgressEditorScreenState.Failure -> TODO()
    }
}

@Composable
fun ProgressEditorContentState(
    habit: Habit,
    formState: FormState,
    loadingState: LoadingState,
    modifier: Modifier = Modifier,
    onAction: (ProgressEditorScreenAction) -> Unit = {}
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        TextField(
            value = formState.content,
            onValueChange = {
                onAction(ProgressEditorScreenAction.ChangeContent(it))
            },
            label = {
                Label(
                    text = stringResource(R.string.content)
                )
            },
            placeholder = {
                RegularText(
                    text = stringResource(R.string.content_placeholder)
                )
            },
            minLines = 6,
            maxLines = 6,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences,
            )
        )

        TextField(
            value = formState.progressString,
            onValueChange = {
                onAction(ProgressEditorScreenAction.ChangeProgress(it))
            },
            label = {
                Label(
                    text = stringResource(R.string.new_progress)
                )
            },
            placeholder = {
                RegularText(
                    text = stringResource(R.string.new_progress_placeholder)
                )
            },
            afterField = {
                RegularText(
                    text = habit.measurement
                )
            }
        )
    }
}

@Composable
fun ProgressEditorLoading(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        RegularText("loading paceholder")
    }
}