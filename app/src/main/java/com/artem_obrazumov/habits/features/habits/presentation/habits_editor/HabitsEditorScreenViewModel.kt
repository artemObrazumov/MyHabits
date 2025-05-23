package com.artem_obrazumov.habits.features.habits.presentation.habits_editor

import com.artem_obrazumov.habits.common.ui.view_model.Action
import com.artem_obrazumov.habits.common.ui.view_model.Effect
import com.artem_obrazumov.habits.common.ui.view_model.State
import com.artem_obrazumov.habits.common.ui.view_model.StatefulViewModel
import com.artem_obrazumov.habits.features.habits.domain.use_case.LoadHabitOnceUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(
    assistedFactory = HabitsEditorScreenViewModel.Factory::class
)
class HabitsEditorScreenViewModel @AssistedInject constructor(
    @Assisted val id: Long? = null,
    private val loadHabitOnceUseCase: LoadHabitOnceUseCase
): StatefulViewModel<HabitsEditorScreenState, HabitsEditorScreenAction, HabitsEditorScreenEffect>(
    initialState = HabitsEditorScreenState.Loading
) {
    override fun onAction(action: HabitsEditorScreenAction) {

    }

    @AssistedFactory
    interface Factory {
        fun create(id: Long?): HabitsEditorScreenViewModel
    }
}

sealed interface HabitsEditorScreenState : State {

    data object Loading: HabitsEditorScreenState
}

sealed interface HabitsEditorScreenAction : Action {


}

sealed interface HabitsEditorScreenEffect : Effect {


}
