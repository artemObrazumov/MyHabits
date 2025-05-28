package com.artem_obrazumov.habits.features.habits.domain.model

data class HabitDetails(
    val habit: Habit,
    val progresses: List<Progress>
)