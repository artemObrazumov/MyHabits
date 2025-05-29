package com.artem_obrazumov.habits.features.habits.domain.data_source

import com.artem_obrazumov.habits.features.habits.domain.model.Habit
import com.artem_obrazumov.habits.features.habits.domain.model.HabitDetails
import kotlinx.coroutines.flow.Flow

interface HabitsLocalDataSource {

    suspend fun observeHabitsFromDatabase(): Flow<List<Habit>>
    suspend fun observeHabitById(id: Long): Flow<Habit>
    suspend fun observeHabitDetailsFromDatabase(id: Long): Flow<HabitDetails>
    suspend fun upsertHabit(habit: Habit): Long
}