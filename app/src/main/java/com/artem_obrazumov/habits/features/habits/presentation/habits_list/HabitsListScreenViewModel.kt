package com.artem_obrazumov.habits.features.habits.presentation.habits_list

import androidx.lifecycle.viewModelScope
import com.artem_obrazumov.habits.R
import com.artem_obrazumov.habits.common.domain.Error
import com.artem_obrazumov.habits.common.domain.Result
import com.artem_obrazumov.habits.common.ui.util.UIText
import com.artem_obrazumov.habits.common.ui.view_model.Action
import com.artem_obrazumov.habits.common.ui.view_model.Effect
import com.artem_obrazumov.habits.common.ui.view_model.State
import com.artem_obrazumov.habits.common.ui.view_model.StatefulViewModel
import com.artem_obrazumov.habits.features.habits.domain.HabitsRepository
import com.artem_obrazumov.habits.features.habits.domain.model.Habit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitsListScreenViewModel @Inject constructor(
    private val habitsRepository: HabitsRepository,
) : StatefulViewModel<HabitsListScreenState, HabitsListScreenAction, HabitsListScreenEffect>(
    initialState = HabitsListScreenState.Loading
) {

    init {
        observeHabits()
    }

    private fun observeHabits() {
        viewModelScope.launch {
            updateState(HabitsListScreenState.Loading)
            habitsRepository.observeHabits().collect { result ->
                onHabitResult(result)
            }
        }
    }

    private fun onHabitResult(result: Result<List<Habit>, Error>) {
        when (result) {
            is Result.Failure -> {
                updateState(
                    HabitsListScreenState.Failure(
                        when(result.error) {
                            else -> UIText.StringResource(R.string.unknown_loading_error)
                        }
                    )
                )
            }

            is Result.Success -> {
                updateState(HabitsListScreenState.Content(result.data))
            }
        }
    }

    override fun onAction(action: HabitsListScreenAction) {
        viewModelScope.launch {
            when (action) {
                HabitsListScreenAction.AddHabit -> {
                    updateEffect(HabitsListScreenEffect.NavigateToAddHabitScreen)
                }

                is HabitsListScreenAction.OpenHabitDetails -> {
                    updateEffect(HabitsListScreenEffect.NavigateToHabitDetailsScreen(action.id))
                }

                HabitsListScreenAction.Retry -> {
                    updateState(HabitsListScreenState.Loading)
                    observeHabits()
                }
            }
        }
    }
}

sealed interface HabitsListScreenState : State {

    data object Loading : HabitsListScreenState

    data class Content(
        val habitsList: List<Habit>
    ) : HabitsListScreenState

    data class Failure(
        val errorMessage: UIText
    ) : HabitsListScreenState
}

sealed interface HabitsListScreenAction : Action {

    data object Retry: HabitsListScreenAction

    data class OpenHabitDetails(
        val id: Long
    ): HabitsListScreenAction

    data object AddHabit: HabitsListScreenAction
}

sealed interface HabitsListScreenEffect : Effect {

    data object NavigateToAddHabitScreen: HabitsListScreenEffect

    data class NavigateToHabitDetailsScreen(
        val id: Long
    ): HabitsListScreenEffect
}
