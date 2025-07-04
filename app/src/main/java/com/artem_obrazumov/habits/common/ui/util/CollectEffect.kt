package com.artem_obrazumov.habits.common.ui.util

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@SuppressLint("ComposableNaming")
@Composable
fun <T> Flow<T>.collectEffect(
    context: CoroutineContext = EmptyCoroutineContext,
    onError: (Throwable) -> Unit = {},
    block: (T) -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        onEach { block(it) }
            .catch { onError(it) }
            .flowOn(context)
            .launchIn(this)
    }
}