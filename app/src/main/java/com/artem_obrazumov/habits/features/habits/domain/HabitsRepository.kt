package com.artem_obrazumov.habits.features.habits.domain

import com.artem_obrazumov.habits.common.domain.Error
import com.artem_obrazumov.habits.common.domain.Result
import com.artem_obrazumov.habits.features.habits.domain.model.Habit
import com.artem_obrazumov.habits.features.habits.domain.model.HabitDetails
import kotlinx.coroutines.flow.Flow

interface HabitsRepository {

    fun observeHabits(): Flow<Result<List<Habit>, Error>>
    fun observeHabitById(id: Long): Flow<Result<Habit, Error>>
    fun observeHabitDetailsById(id: Long): Flow<Result<HabitDetails, Error>>
    suspend fun upsertHabit(habit: Habit): Result<Long, Error>
}