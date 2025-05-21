package com.artem_obrazumov.habits.features.habits.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

data class Habit(
    val id: Long,
    val name: String,
    val measurement: String,
    val goalType: GoalType,
    val frequency: ProgressFrequency,
    val progress: Double,
    val goal: Double,
    val startedAt: LocalDate,
    val editedAt: LocalDateTime,
    val usersCount: Int,
)