package com.artem_obrazumov.habits.features.habits.presentation.routes

import kotlinx.serialization.Serializable

@Serializable
data object HabitsList

@Serializable
data class HabitsDetails(
    val id: Long
)
