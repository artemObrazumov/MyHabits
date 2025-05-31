package com.artem_obrazumov.habits.features.habits.presentation.progress_editor

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
import com.artem_obrazumov.habits.features.habits.domain.model.Habit
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@HiltViewModel(
    assistedFactory = ProgressEditorScreenViewModel.Factory::class
)
class ProgressEditorScreenViewModel @AssistedInject constructor(
    @Assisted val habitId: Long,
    @Assisted var id: Long? = null,
    private val habitsRepository: HabitsRepository
) : StatefulViewModel<ProgressEditorScreenState, ProgressEditorScreenAction, ProgressEditorScreenEffect>(
    initialState = ProgressEditorScreenState.Loading
) {

    private var habitData: Habit? = null

    private var formState by Delegates.observable(FormState()) { _, old, new ->
        if (old != new) updateContentState()
    }

    private var loadingState by Delegates.observable(LoadingState()) { _, old, new ->
        if (old != new) updateContentState()
    }

    init {
        loadHabitDataAndDisplayProgressForm()
    }

    private fun updateContentState() {
        viewModelScope.launch {
            updateState(
                ProgressEditorScreenState.Content(
                    progressId = id,
                    habit = requireNotNull(habitData) {
                        "You cant call updateContentState while habitData is null"
                    },
                    formState = formState,
                    loadingState = loadingState
                )
            )
        }
    }

    private fun loadHabitDataAndDisplayProgressForm() {
        viewModelScope.launch {
            val habitResult = habitsRepository.observeHabitById(habitId).first()
            if (habitResult is Result.Failure) {
                updateState(
                    ProgressEditorScreenState.Failure(
                        when(habitResult.error) {
                            else -> UIText.StringResource(R.string.unknown_habit_loading_error)
                        }
                    )
                )
                return@launch
            }

            habitData = (habitResult as Result.Success).data

            if (id == null) {
                updateContentState()
                return@launch
            }

            //val progressResult
        }
    }

    override fun onAction(action: ProgressEditorScreenAction) {
        when (action) {
            is ProgressEditorScreenAction.ChangeContent -> {
                formState = formState.copy(
                    content = action.content
                )
            }

            is ProgressEditorScreenAction.ChangeProgress -> {
                formState = formState.copy(
                    progressString = action.progress
                )
            }

            ProgressEditorScreenAction.Retry -> TODO()
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            habitId: Long, id: Long?
        ): ProgressEditorScreenViewModel
    }
}

sealed interface ProgressEditorScreenState : State {

    data object Loading : ProgressEditorScreenState

    data class Content(
        val progressId: Long? = null,
        val habit: Habit,
        val formState: FormState = FormState(),
        val loadingState: LoadingState = LoadingState()
    ) : ProgressEditorScreenState

    data class Failure(
        val errorMessage: UIText
    ) : ProgressEditorScreenState
}

data class FormState(
    val content: String = "",
    val progressString: String = ""
)

data class LoadingState(
    val isUploading: Boolean = false,
    val message: Message? = null
)

sealed interface ProgressEditorScreenAction : Action {

    data object Retry : ProgressEditorScreenAction

    data class ChangeContent(
        val content: String
    ) : ProgressEditorScreenAction

    data class ChangeProgress(
        val progress: String
    ) : ProgressEditorScreenAction
}

sealed interface ProgressEditorScreenEffect : Effect
