package com.artem_obrazumov.habits.features.habits.presentation.habits_list

import androidx.lifecycle.viewModelScope
import com.artem_obrazumov.habits.R
import com.artem_obrazumov.habits.common.domain.Result
import com.artem_obrazumov.habits.common.ui.util.UIText
import com.artem_obrazumov.habits.common.ui.view_model.Action
import com.artem_obrazumov.habits.common.ui.view_model.Effect
import com.artem_obrazumov.habits.common.ui.view_model.State
import com.artem_obrazumov.habits.common.ui.view_model.StatefulViewModel
import com.artem_obrazumov.habits.features.habits.domain.model.Habit
import com.artem_obrazumov.habits.features.habits.domain.use_case.LoadHabitsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitsListScreenViewModel @Inject constructor(
    private val loadHabitsUseCase: LoadHabitsUseCase
) : StatefulViewModel<HabitsListScreenState, HabitsListScreenAction, HabitsListScreenEffect>(
    initialState = HabitsListScreenState.Loading
) {

    init {
        loadHabits()
    }

    private fun loadHabits() {
        viewModelScope.launch {
            when (val result = loadHabitsUseCase()) {
                is Result.Failure -> {
                    updateState(
                        HabitsListScreenState.Failure(
                            when(result.error) {
                                else -> UIText.StringResource(R.string.unknown_error)
                            }
                        )
                    )
                }

                is Result.Success -> {
                    updateState(HabitsListScreenState.Loading)
                    result.data.collect { habits ->
                        updateState(HabitsListScreenState.Content(habits))
                    }
                }
            }
        }
    }

    override fun onAction(action: HabitsListScreenAction) {
        when (action) {
            HabitsListScreenAction.AddHabit -> {

            }
            is HabitsListScreenAction.OpenHabitDetails -> TODO()
            HabitsListScreenAction.Retry -> {
                updateState(HabitsListScreenState.Loading)
                loadHabits()
            }
        }
    }

}

sealed class HabitsListScreenState : State {

    data object Loading : HabitsListScreenState()

    data class Content(
        val habitsList: List<Habit>
    ) : HabitsListScreenState()

    data class Failure(
        val errorMessage: UIText
    ) : HabitsListScreenState()
}

sealed class HabitsListScreenAction : Action {

    data object Retry: HabitsListScreenAction()

    data class OpenHabitDetails(
        val id: Long
    ): HabitsListScreenAction()

    data object AddHabit: HabitsListScreenAction()
}

sealed class HabitsListScreenEffect : Effect {

    data object NavigateToAddHabitScreen: HabitsListScreenEffect()

    data class NavigateToHabitDetailsScreen(
        val id: Long
    ): HabitsListScreenEffect()
}
