package com.artem_obrazumov.habits.features.habits.presentation.habits_editor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component1
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component2
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component3
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.artem_obrazumov.habits.R
import com.artem_obrazumov.habits.common.ui.components.button.Button
import com.artem_obrazumov.habits.common.ui.components.containers.ShimmerBox
import com.artem_obrazumov.habits.common.ui.components.input.DropdownTextItem
import com.artem_obrazumov.habits.common.ui.components.input.TextDropdownMenu
import com.artem_obrazumov.habits.common.ui.components.input.TextField
import com.artem_obrazumov.habits.common.ui.components.text.Label
import com.artem_obrazumov.habits.common.ui.components.text.Message
import com.artem_obrazumov.habits.common.ui.components.text.RegularText
import com.artem_obrazumov.habits.common.ui.screens.FailureScreen
import com.artem_obrazumov.habits.common.ui.theme.HabitsTheme
import com.artem_obrazumov.habits.common.ui.util.collectEffect
import com.artem_obrazumov.habits.features.habits.domain.model.GoalType
import com.artem_obrazumov.habits.features.habits.domain.model.ProgressFrequency
import com.artem_obrazumov.habits.features.habits.presentation.habits_editor.HabitsEditorScreenTags.HABITS_EDITOR_CONTENT
import com.artem_obrazumov.habits.features.habits.presentation.habits_editor.HabitsEditorScreenTags.HABITS_EDITOR_LOADING
import com.artem_obrazumov.habits.features.habits.presentation.util.asStringComp

@Composable
fun HabitsEditorScreen(
    viewModel: HabitsEditorScreenViewModel,
    modifier: Modifier = Modifier,
    menu: @Composable (() -> Unit) = {}
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    viewModel.effect.collectEffect {}

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        menu()

        HabitsEditorScreenContent(
            state = state,
            onAction = viewModel::onAction
        )
    }
}

@Composable
fun HabitsEditorScreenContent(
    state: HabitsEditorScreenState,
    modifier: Modifier = Modifier,
    onAction: (action: HabitsEditorScreenAction) -> Unit = {}
) {

    when(state) {
        HabitsEditorScreenState.Loading -> {
            HabitsEditorLoading(
                modifier = modifier
            )
        }
        is HabitsEditorScreenState.Failure -> {
            FailureScreen(
                modifier = modifier,
                message = state.errorMessage.asString(),
                onRetry = {
                    onAction(HabitsEditorScreenAction.Retry)
                }
            )
        }
        is HabitsEditorScreenState.Content -> {
            HabitsEditorScreenContentState(
                id = state.habitId,
                formState = state.formState,
                loadingState = state.loadingState,
                onAction = onAction
            )
        }
    }
}

@Composable
fun HabitsEditorScreenContentState(
    id: Long?,
    formState: FormState,
    loadingState: LoadingState,
    modifier: Modifier = Modifier,
    onAction: (action: HabitsEditorScreenAction) -> Unit = {},
) {
    val isEditingFirstTime = (id == null)
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val (measurementField, startField, goalField) = remember { FocusRequester.createRefs() }

    Column (
        modifier = modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .padding(16.dp)
            .testTag(HABITS_EDITOR_CONTENT),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        TextField(
            value = formState.name,
            onValueChange = {
                onAction(HabitsEditorScreenAction.ChangeHabitName(it))
            },
            label = {
                Label(
                    text = stringResource(R.string.name)
                )
            },
            placeholder = {
                RegularText(
                    text = stringResource(R.string.name_placeholder)
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    measurementField.requestFocus()
                }
            )
        )

        var goalTypeMenuExpanded by remember { mutableStateOf(false) }
        TextDropdownMenu(
            isExpanded = goalTypeMenuExpanded,
            onClick = {
                goalTypeMenuExpanded = !goalTypeMenuExpanded
            },
            value = formState.goalTypeOption.asStringComp(),
            label = {
                Label(
                    text = stringResource(R.string.goal_type)
                )
            }
        ) {
            GoalType.entries.forEach { goalType ->
                DropdownTextItem(
                    text = goalType.asStringComp(),
                    onClick = {
                        goalTypeMenuExpanded = false
                        onAction(HabitsEditorScreenAction.ChangeHabitGoalType(goalType))
                    }
                )
            }
        }

        var frequencyMenuExpanded by remember { mutableStateOf(false) }
        TextDropdownMenu(
            isExpanded = frequencyMenuExpanded,
            onClick = {
                frequencyMenuExpanded = !frequencyMenuExpanded
            },
            value = formState.frequencyOption.asStringComp(),
            label = {
                Label(
                    text = stringResource(R.string.progress_frequency)
                )
            }
        ) {
            ProgressFrequency.entries.forEach { frequency ->
                DropdownTextItem(
                    text = frequency.asStringComp(),
                    onClick = {
                        frequencyMenuExpanded = false
                        onAction(HabitsEditorScreenAction.ChangeHabitFrequency(frequency))
                    }
                )
            }
        }

        TextField(
            modifier = Modifier
                .focusRequester(measurementField),
            value = formState.measurement,
            onValueChange = {
                onAction(HabitsEditorScreenAction.ChangeHabitMeasurement(it))
            },
            label = {
                Label(
                    text = stringResource(R.string.measurement)
                )
            },
            placeholder = {
                RegularText(
                    text = stringResource(R.string.measurement_placeholder)
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    startField.requestFocus()
                }
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(startField),
                value = formState.startString,
                enabled = isEditingFirstTime,
                onValueChange = {
                    onAction(HabitsEditorScreenAction.ChangeHabitStartString(it))
                },
                label = {
                    Label(
                        text = stringResource(R.string.start)
                    )
                },
                placeholder = {
                    RegularText(
                        text = (0).toString()
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        goalField.requestFocus()
                    }
                )
            )

            TextField(
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(goalField),
                value = formState.goalString,
                enabled = isEditingFirstTime,
                onValueChange = {
                    onAction(HabitsEditorScreenAction.ChangeHabitGoalString(it))
                },
                label = {
                    Label(
                        text = stringResource(R.string.goal)
                    )
                },
                placeholder = {
                    RegularText(
                        text = (100).toString()
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                )
            )
        }

        Column {
            if (loadingState.message != null) {
                Message(
                    message = loadingState.message
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    onAction(HabitsEditorScreenAction.Save)
                }
            ) {
                if (loadingState.isUploading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(20.dp),
                        color = Color.White
                    )
                } else {
                    RegularText(
                        text = stringResource(R.string.save)
                    )
                }
            }
        }
    }
}

@Composable
fun HabitsEditorLoading(
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag(HABITS_EDITOR_LOADING),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        )

        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        )

        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        )

        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ShimmerBox(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            )

            ShimmerBox(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            )
        }

        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        )
    }
}

@PreviewLightDark
@Composable
fun HabitsEditorScreenPreview() {
    HabitsTheme {
        Surface {
            HabitsEditorScreenContentState(
                id = null,
                formState = FormState(),
                loadingState = LoadingState()
            )
        }
    }
}

object HabitsEditorScreenTags {

    const val HABITS_EDITOR_LOADING = "habits_editor_loading"
    const val HABITS_EDITOR_CONTENT = "habits_editor_content"
}
