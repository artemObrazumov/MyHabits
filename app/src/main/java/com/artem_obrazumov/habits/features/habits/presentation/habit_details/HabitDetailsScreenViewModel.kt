package com.artem_obrazumov.habits.features.habits.presentation.habit_details

import androidx.lifecycle.viewModelScope
import com.artem_obrazumov.habits.common.domain.Result
import com.artem_obrazumov.habits.common.ui.util.UIText
import com.artem_obrazumov.habits.common.ui.view_model.Action
import com.artem_obrazumov.habits.common.ui.view_model.Effect
import com.artem_obrazumov.habits.common.ui.view_model.State
import com.artem_obrazumov.habits.common.ui.view_model.StatefulViewModel
import com.artem_obrazumov.habits.features.auth.domain.use_case.ObserveLocalUserUseCase
import com.artem_obrazumov.habits.features.habits.domain.model.HabitDetails
import com.artem_obrazumov.habits.features.habits.domain.model.Progress
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
    private val loadHabitDetailsUseCase: LoadHabitDetailsUseCase,
    private val observeLocalUserUseCase: ObserveLocalUserUseCase,
) : StatefulViewModel<HabitDetailsScreenState, HabitDetailsScreenAction, HabitDetailsScreenEffect>(
    initialState = HabitDetailsScreenState.Loading
) {

    init {
        loadHabitDetails()
    }

    private fun loadHabitDetails() {
        viewModelScope.launch {
            when (val result = loadHabitDetailsUseCase(id)) {
                is Result.Failure -> {

                }

                is Result.Success -> {
                    updateState(HabitDetailsScreenState.Loading)
                    result.data.collect { details ->
                        updateState(
                            if (state.value is HabitDetailsScreenState.Content) {
                                (state.value as HabitDetailsScreenState.Content).copy(
                                    habitDetails = details
                                )
                            } else {
                                HabitDetailsScreenState.Content(
                                    habitDetails = details
                                )
                            }
                        )

                        updateEffect(HabitDetailsScreenEffect.UpdateHabitName(details.habit.name))
                    }
                    observeLocalUser()
                }
            }
        }
    }

    private fun observeLocalUser() {
        viewModelScope.launch {
            when (val result = observeLocalUserUseCase()) {
                is Result.Failure -> {

                }

                is Result.Success -> {

                }
            }
        }
    }

    override fun onAction(action: HabitDetailsScreenAction) {
        when (action) {
            HabitDetailsScreenAction.AddProgress -> TODO()
            is HabitDetailsScreenAction.LoadProgressList -> {

            }

            HabitDetailsScreenAction.Retry -> TODO()
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(id: Long): HabitDetailsScreenViewModel
    }
}

sealed interface HabitDetailsScreenState : State {

    data object Loading : HabitDetailsScreenState

    data class Content(
        val habitDetails: HabitDetails,
        val progressState: ProgressState = ProgressState.Idle
    ) : HabitDetailsScreenState

    data class Failure(
        val errorMessage: UIText
    ) : HabitDetailsScreenState
}

sealed interface ProgressState {

    data object Idle : ProgressState

    data object Loading : ProgressState

    data class Content(
        val progressList: List<Progress>
    ) : ProgressState
}

sealed interface HabitDetailsScreenAction : Action {

    data object AddProgress : HabitDetailsScreenAction

    data object Retry : HabitDetailsScreenAction

    data class LoadProgressList(
        val userId: Long?
    ) : HabitDetailsScreenAction
}

sealed interface HabitDetailsScreenEffect : Effect {

    data class UpdateHabitName(
        val name: String
    ) : HabitDetailsScreenEffect
}