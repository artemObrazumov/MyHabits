package com.artem_obrazumov.habits.features.habits.data.local

import com.artem_obrazumov.habits.features.habits.data.local.dao.HabitDao
import com.artem_obrazumov.habits.features.habits.data.local.entity.toHabit
import com.artem_obrazumov.habits.features.habits.data.local.entity.toHabitDetails
import com.artem_obrazumov.habits.features.habits.data.local.entity.toHabitEntity
import com.artem_obrazumov.habits.features.habits.domain.data_source.HabitsLocalDataSource
import com.artem_obrazumov.habits.features.habits.domain.model.Habit
import com.artem_obrazumov.habits.features.habits.domain.model.HabitDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HabitsLocalDataSourceImpl(
    private val habitDao: HabitDao
): HabitsLocalDataSource {

    override fun observeHabitsFromDatabase(): Flow<List<Habit>> {
        return habitDao.observeNotDeletedHabits().map { habits ->
            habits.map { it.toHabit() }
        }
    }

    override fun observeHabitById(id: Long): Flow<Habit> {
        return habitDao.observeById(id).map { it.toHabit() }
    }

    override fun observeHabitDetailsFromDatabase(id: Long): Flow<HabitDetails> {
        return habitDao.observeHabitDetailsById(id).map { it.toHabitDetails() }
    }

    override suspend fun upsertHabit(habit: Habit): Long {
        return habitDao.upsertHabit(habit.toHabitEntity())
    }
}