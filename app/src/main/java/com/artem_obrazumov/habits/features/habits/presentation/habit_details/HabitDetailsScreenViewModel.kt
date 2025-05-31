package com.artem_obrazumov.habits.features.habits.presentation.habit_details

import androidx.lifecycle.viewModelScope
import com.artem_obrazumov.habits.R
import com.artem_obrazumov.habits.common.domain.Result
import com.artem_obrazumov.habits.common.ui.util.UIText
import com.artem_obrazumov.habits.common.ui.view_model.Action
import com.artem_obrazumov.habits.common.ui.view_model.Effect
import com.artem_obrazumov.habits.common.ui.view_model.State
import com.artem_obrazumov.habits.common.ui.view_model.StatefulViewModel
import com.artem_obrazumov.habits.features.auth.domain.UsersRepository
import com.artem_obrazumov.habits.features.auth.domain.model.User
import com.artem_obrazumov.habits.features.habits.domain.HabitsRepository
import com.artem_obrazumov.habits.features.habits.domain.model.HabitDetails
import com.artem_obrazumov.habits.features.habits.domain.model.Progress
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@HiltViewModel(
    assistedFactory = HabitDetailsScreenViewModel.Factory::class
)
class HabitDetailsScreenViewModel @AssistedInject constructor(
    @Assisted val id: Long,
    private val habitsRepository: HabitsRepository,
    private val usersRepository: UsersRepository,
) : StatefulViewModel<HabitDetailsScreenState, HabitDetailsScreenAction, HabitDetailsScreenEffect>(
    initialState = HabitDetailsScreenState.Loading
) {

    private var userProgressListObservingJob: Job? = null

    init {
        observeHabitDetailsAndUserInfo()
    }

    private fun observeHabitDetailsAndUserInfo() {
        viewModelScope.launch {
            updateState(HabitDetailsScreenState.Loading)
            combine(
                habitsRepository.observeHabitDetailsById(id),
                usersRepository.observeLocalUser()
            ) { habitsResult, userResult ->
                when (habitsResult) {
                    is Result.Failure -> {
                        HabitDetailsScreenState.Failure(
                            when (habitsResult.error) {
                                else -> UIText.StringResource(R.string.unknown_error)
                            }
                        )
                    }

                    is Result.Success -> {
                        val user = if (userResult is Result.Success) {
                            userResult.data
                        } else {
                            null
                        }
                        produceContentState(habitsResult.data, user)
                    }
                }
            }.collect { state ->
                updateState(state)
            }
        }
    }

    private fun produceContentState(
        details: HabitDetails,
        localUser: User?
    ): HabitDetailsScreenState.Content {

        val oldSelectedUserId = getSelectedUserIdFromState(state.value)
        // Setting selected user to null if it was in old user list but not present in the current one
        val newSelectedUserId =
            if (oldSelectedUserId != null && details.users.firstOrNull { it.id == oldSelectedUserId } != null) {
                oldSelectedUserId
            } else {
                null
            }
        observeUserProgressList(newSelectedUserId)
        return HabitDetailsScreenState.Content(
            habitDetails = details,
            localUser = localUser,
            selectedUserId = newSelectedUserId
        )
    }

    private fun getSelectedUserIdFromState(state: HabitDetailsScreenState): Long? {
        return if (state is HabitDetailsScreenState.Content) {
            state.selectedUserId
        } else null
    }

    private fun observeUserProgressList(id: Long? = null) {
        userProgressListObservingJob?.cancel()
        userProgressListObservingJob = viewModelScope.launch {

        }
    }

    override fun onAction(action: HabitDetailsScreenAction) {
        when (action) {
            HabitDetailsScreenAction.AddProgress -> TODO()
            is HabitDetailsScreenAction.LoadProgressList -> {
                if (action.userId != (state.value as HabitDetailsScreenState.Content).selectedUserId) {
                    observeUserProgressList()
                }
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
        val selectedUserId: Long? = null,
        val localUser: User? = null,
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

sealed interface HabitDetailsScreenEffect : Effect