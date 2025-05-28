package com.artem_obrazumov.habits.features.habits.domain.data_source

import com.artem_obrazumov.habits.features.habits.domain.model.Habit
import kotlinx.coroutines.flow.Flow

interface HabitsLocalDataSource {

    suspend fun observeHabitsFromDatabase(): Flow<List<Habit>>
    suspend fun observeHabitById(id: Long): Flow<Habit>
    suspend fun upsertHabit(habit: Habit): Long
}