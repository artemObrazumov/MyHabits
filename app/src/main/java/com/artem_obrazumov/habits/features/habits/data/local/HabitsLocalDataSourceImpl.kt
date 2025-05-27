package com.artem_obrazumov.habits.features.habits.data.local

import com.artem_obrazumov.habits.features.habits.data.local.dao.HabitDao
import com.artem_obrazumov.habits.features.habits.data.local.entity.toHabit
import com.artem_obrazumov.habits.features.habits.data.local.entity.toHabitEntity
import com.artem_obrazumov.habits.features.habits.domain.data_source.HabitsLocalDataSource
import com.artem_obrazumov.habits.features.habits.domain.model.Habit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HabitsLocalDataSourceImpl(
    private val habitDao: HabitDao
): HabitsLocalDataSource {

    override suspend fun observeHabitsFromDatabase(): Flow<List<Habit>> {
        return habitDao.observeNotDeletedHabits().map { habits ->
            habits.map { it.toHabit() }
        }
    }

    override suspend fun observeHabitById(id: Long): Flow<Habit> {
        return habitDao.observeById(id).map { it.toHabit() }
    }

    override suspend fun upsertHabit(habit: Habit) {
        habitDao.upsertHabit(habit.toHabitEntity())
    }
}