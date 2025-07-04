package com.artem_obrazumov.habits.features.habits.presentation.util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.artem_obrazumov.habits.R
import com.artem_obrazumov.habits.features.habits.domain.model.GoalType

private val GoalTypeToStringResMap = mapOf(
    GoalType.SINGLE to R.string.goal_type_single,
    GoalType.FREQUENT to R.string.goal_type_frequent
)

fun GoalType.asString(context: Context): String {
    return context.getString(GoalTypeToStringResMap[this] ?: throw IllegalStateException("No such goalType"))
}

@Composable
fun GoalType.asStringComp(): String {
    return this.asString(LocalContext.current)
}
