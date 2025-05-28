package com.artem_obrazumov.habits.features.habits.presentation.habit_details

import androidx.lifecycle.viewModelScope
import com.artem_obrazumov.habits.common.ui.view_model.Action
import com.artem_obrazumov.habits.common.ui.view_model.Effect
import com.artem_obrazumov.habits.common.ui.view_model.State
import com.artem_obrazumov.habits.common.ui.view_model.StatefulViewModel
import com.artem_obrazumov.habits.features.habits.domain.use_case.LoadHabitDetailsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel(
    assistedFactory = HabitDetailsScreenViewModel.Factory::class
)
class HabitDetailsScreenViewModel @AssistedInject constructor(
    @Assisted val id: Long,
    private val loadHabitDetailsUseCase: LoadHabitDetailsUseCase
): StatefulViewModel<HabitDetailsScreenState, HabitDetailsScreenAction, HabitDetailsScreenEffect>(
    initialState = HabitDetailsScreenState.Loading
) {

    private fun loadHabitDetails() {
        viewModelScope.launch {

        }
    }

    override fun onAction(action: HabitDetailsScreenAction) {

    }

    @AssistedFactory
    interface Factory {
        fun create(id: Long): HabitDetailsScreenViewModel
    }
}

sealed interface HabitDetailsScreenState : State {

    data object Loading: HabitDetailsScreenState
}

sealed interface HabitDetailsScreenAction : Action

sealed interface HabitDetailsScreenEffect : Effect {

    data class UpdateHabitName(
        val name: String
    ): HabitDetailsScreenEffect
}