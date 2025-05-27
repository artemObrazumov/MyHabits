package com.artem_obrazumov.habits.features.habits.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.artem_obrazumov.habits.R
import com.artem_obrazumov.habits.features.habits.domain.model.GoalType

private val GoalTypeToStringResMap = mapOf(
    GoalType.SINGLE to R.string.goal_type_single,
    GoalType.FREQUENT to R.string.goal_type_frequent
)

@Composable
fun GoalType.toStringComp(): String {
    return stringResource(GoalTypeToStringResMap[this] ?: throw IllegalStateException("No such goalType"))
}
