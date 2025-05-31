package com.artem_obrazumov.habits.features.habits.presentation.routes

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object HabitsList : NavKey

@Serializable
data class HabitsEditor(
    val id: Long? = null
) : NavKey

@Serializable
data class HabitsDetails(
    val id: Long
) : NavKey

@Serializable
data class ProgressEditor(
    val habitId: Long,
    val id: Long? = null,
) : NavKey
