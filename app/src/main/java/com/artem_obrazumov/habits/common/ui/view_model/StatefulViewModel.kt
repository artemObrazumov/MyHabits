package com.artem_obrazumov.habits.common.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn

abstract class StatefulViewModel<S: State, A: Action, E: Effect>(
    initialState: S
): ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(
                stopTimeoutMillis = 1000,
                replayExpirationMillis = 9000
            ),
            initialValue = initialState
        )

    private val _effect = MutableSharedFlow<Effect>()
    val effect = _effect.asSharedFlow()

    protected fun updateState(newState: S) {
        _state.value = newState
    }

    protected suspend fun updateEffect(effect: E) {
        _effect.emit(effect)
    }

    abstract fun onAction(action: A)
}

interface State

interface Action

interface Effect