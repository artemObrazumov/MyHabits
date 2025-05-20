package com.artem_obrazumov.habits.features.habits.presentation.preview

import com.artem_obrazumov.habits.features.habits.domain.model.GoalType
import com.artem_obrazumov.habits.features.habits.domain.model.Habit
import com.artem_obrazumov.habits.features.habits.domain.model.ProgressFrequency
import java.time.LocalDate
import java.time.LocalDateTime

val HABITS_LIST = listOf(
    Habit(
        id = 1,
        name = "Test an app",
        measurement = "times",
        goalType = GoalType.SINGLE,
        frequency = ProgressFrequency.WEEKLY,
        progress = 1.0,
        goal = 2.0,
        startedAt = LocalDate.now(),
        editedAt = LocalDateTime.now(),
    ),
    Habit(
        id = 1,
        name = "Very loooooooooooooooooooooooooooooooooong title",
        measurement = "times",
        goalType = GoalType.SINGLE,
        frequency = ProgressFrequency.WEEKLY,
        progress = 1.0,
        goal = 10.5,
        startedAt = LocalDate.now(),
        editedAt = LocalDateTime.now(),
    )
)