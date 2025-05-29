package com.artem_obrazumov.habits.features.habits.domain.model

import com.artem_obrazumov.habits.features.auth.domain.model.User

data class HabitDetails(
    val habit: Habit,
    val users: List<User>
)