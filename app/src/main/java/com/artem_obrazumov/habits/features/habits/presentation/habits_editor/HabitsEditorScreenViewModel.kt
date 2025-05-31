package com.artem_obrazumov.habits.features.habits.presentation.habits_editor

import androidx.lifecycle.viewModelScope
import com.artem_obrazumov.habits.R
import com.artem_obrazumov.habits.common.domain.Result
import com.artem_obrazumov.habits.common.ui.util.Message
import com.artem_obrazumov.habits.common.ui.util.UIText
import com.artem_obrazumov.habits.common.ui.view_model.Action
import com.artem_obrazumov.habits.common.ui.view_model.Effect
import com.artem_obrazumov.habits.common.ui.view_model.State
import com.artem_obrazumov.habits.common.ui.view_model.StatefulViewModel
import com.artem_obrazumov.habits.features.habits.domain.HabitsRepository
import com.artem_obrazumov.habits.features.habits.domain.model.GoalType
import com.artem_obrazumov.habits.features.habits.domain.model.Habit
import com.artem_obrazumov.habits.features.habits.domain.model.ProgressFrequency
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.properties.Delegates

@HiltViewModel(
    assistedFactory = HabitsEditorScreenViewModel.Factory::class
)
class HabitsEditorScreenViewModel @AssistedInject constructor(
    @Assisted val id: Long? = null,
    private val habitsRepository: HabitsRepository,
    private val habitsEditorFormValidator: HabitsEditorFormValidator
) : StatefulViewModel<HabitsEditorScreenState, HabitsEditorScreenAction, HabitsEditorScreenEffect>(
    initialState = HabitsEditorScreenState.Loading
) {

    private var formState by Delegates.observable(FormState()) { _, old, new ->
        if (old != new) updateContentState()
    }
    private var loadingState by Delegates.observable(LoadingState()) { _, old, new ->
        if (old != new) updateContentState()
    }

    init {
        loadHabit()
    }

    private fun updateContentState() {
        viewModelScope.launch {
            updateState(
                HabitsEditorScreenState.Content(
                    formState = formState,
                    loadingState = loadingState
                )
            )
        }
    }

    private fun loadHabit() {
        viewModelScope.launch {
            if (id == null) {
                updateContentState()
                return@launch
            }

            when (val result = habitsRepository.observeHabitById(id).first()) {
                is Result.Failure -> {
                    updateState(
                        HabitsEditorScreenState.Failure(
                            when (result.error) {
                                else -> UIText.StringResource(R.string.unknown_error)
                            }
                        )
                    )
                }

                is Result.Success -> {
                    val habit = result.data
                    formState = FormState(
                        name = habit.name,
                        goalTypeOption = habit.goalType,
                        frequencyOption = habit.frequency,
                        measurement = habit.measurement,
                        startString = habit.progress.toString(),
                        goalString = habit.goal.toString(),
                        userCount = habit.usersCount,
                        startedAt = habit.startedAt
                    )
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        loadingState = loadingState.copy(
            message = null
        )

        if (state.value !is HabitsEditorScreenState.Content) return false

        val habitName = formState.name.trim()
        val measurement = formState.measurement.trim()
        val startValue = formState.startString.toDoubleOrNull()
        val goalValue = formState.goalString.toDoubleOrNull()

        val result = habitsEditorFormValidator.validate(
            habitName = habitName,
            measurement = measurement,
            start = startValue,
            goal = goalValue
        )
        val error = when (result) {
            HabitEditorFormValidationResult.HabitNameTooShort -> UIText.StringResource(R.string.habit_name_too_short)
            HabitEditorFormValidationResult.HabitNameTooLong -> UIText.StringResource(R.string.habit_name_too_long)
            HabitEditorFormValidationResult.MeasurementTooShort -> UIText.StringResource(R.string.measurement_too_short)
            HabitEditorFormValidationResult.MeasurementTooLong -> UIText.StringResource(R.string.measurement_too_long)
            HabitEditorFormValidationResult.InvalidStart -> UIText.StringResource(R.string.invalid_start)
            HabitEditorFormValidationResult.InvalidGoal -> UIText.StringResource(R.string.invalid_goal)
            HabitEditorFormValidationResult.GoalNotHigherThanStart -> UIText.StringResource(R.string.goal_should_be_higher_than_start)

            HabitEditorFormValidationResult.Correct -> null
        }
        return if (error == null) {
            true
        } else {
            loadingState = loadingState.copy(
                message = Message.Error(error)
            )
            false
        }
    }

    private fun upsertHabit() {
        viewModelScope.launch {
            loadingState = loadingState.copy(
                isUploading = true
            )
            val formState = (state.value as HabitsEditorScreenState.Content).formState
            val habit = Habit(
                id = id,
                name = formState.name,
                measurement = formState.measurement,
                goalType = formState.goalTypeOption,
                frequency = formState.frequencyOption,
                progress = formState.startString.toDouble(),
                goal = formState.goalString.toDouble(),
                startedAt = formState.startedAt ?: LocalDate.now(),
                editedAt = LocalDateTime.now(),
                usersCount = formState.userCount
            )
            when(val result = habitsRepository.upsertHabit(habit)) {
                is Result.Failure -> {
                    val messageResource = when(result.error) {
                        else -> R.string.error_occured
                    }
                    loadingState = loadingState.copy(
                        message = Message.Error(UIText.StringResource(messageResource)),
                        isUploading = false
                    )
                }
                is Result.Success -> {
                    loadingState = loadingState.copy(
                        message = Message.Success(UIText.StringResource(R.string.habit_uploaded)),
                        isUploading = false
                    )
                }
            }
        }
    }

    override fun onAction(action: HabitsEditorScreenAction) {
        when (action) {
            is HabitsEditorScreenAction.ChangeHabitFrequency -> {
                formState = formState.copy(
                    frequencyOption = action.frequency
                )
            }

            is HabitsEditorScreenAction.ChangeHabitGoalType -> {
                formState = formState.copy(
                    goalTypeOption = action.goalType
                )
            }

            is HabitsEditorScreenAction.ChangeHabitMeasurement -> {
                formState = formState.copy(
                    measurement = action.measurement
                )
            }

            is HabitsEditorScreenAction.ChangeHabitName -> {
                formState = formState.copy(
                    name = action.name
                )
            }

            is HabitsEditorScreenAction.ChangeHabitStartString -> {
                formState = formState.copy(
                    startString = action.startString
                )
            }

            is HabitsEditorScreenAction.ChangeHabitGoalString -> {
                formState = formState.copy(
                    goalString = action.goalString
                )
            }

            HabitsEditorScreenAction.Retry -> {
                loadHabit()
            }

            HabitsEditorScreenAction.Save -> {
                if (
                    state.value is HabitsEditorScreenState.Content &&
                    !(state.value as HabitsEditorScreenState.Content).loadingState.isUploading
                ) {
                    if (validateForm()) {
                        upsertHabit()
                    }
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(id: Long?): HabitsEditorScreenViewModel
    }
}

sealed interface HabitsEditorScreenState : State {

    data object Loading : HabitsEditorScreenState

    data class Content(
        val formState: FormState = FormState(),
        val loadingState: LoadingState = LoadingState()
    ) : HabitsEditorScreenState

    data class Failure(
        val errorMessage: UIText
    ) : HabitsEditorScreenState
}

data class FormState(
    val name: String = "",
    val goalTypeOption: GoalType = GoalType.SINGLE,
    val frequencyOption: ProgressFrequency = ProgressFrequency.DAILY,
    val measurement: String = "",
    val startString: String = "",
    val goalString: String = "",
    val userCount: Int = 1,
    val startedAt: LocalDate? = null
)

data class LoadingState(
    val isUploading: Boolean = false,
    val message: Message? = null
)

sealed interface HabitsEditorScreenAction : Action {

    data object Retry : HabitsEditorScreenAction

    data class ChangeHabitName(
        val name: String
    ) : HabitsEditorScreenAction

    data class ChangeHabitGoalType(
        val goalType: GoalType
    ) : HabitsEditorScreenAction

    data class ChangeHabitFrequency(
        val frequency: ProgressFrequency
    ) : HabitsEditorScreenAction

    data class ChangeHabitMeasurement(
        val measurement: String
    ) : HabitsEditorScreenAction

    data class ChangeHabitStartString(
        val startString: String
    ) : HabitsEditorScreenAction

    data class ChangeHabitGoalString(
        val goalString: String
    ) : HabitsEditorScreenAction

    data object Save : HabitsEditorScreenAction
}

sealed interface HabitsEditorScreenEffect : Effect
