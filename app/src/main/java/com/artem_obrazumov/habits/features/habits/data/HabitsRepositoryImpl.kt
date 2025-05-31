package com.artem_obrazumov.habits.features.habits.data

import com.artem_obrazumov.habits.common.domain.Error
import com.artem_obrazumov.habits.common.domain.Result
import com.artem_obrazumov.habits.common.domain.UnknownError
import com.artem_obrazumov.habits.features.habits.domain.HabitsRepository
import com.artem_obrazumov.habits.features.habits.domain.data_source.HabitsLocalDataSource
import com.artem_obrazumov.habits.features.habits.domain.model.Habit
import com.artem_obrazumov.habits.features.habits.domain.model.HabitDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class HabitsRepositoryImpl(
    private val habitsLocalDataSource: HabitsLocalDataSource
) : HabitsRepository {

    override fun observeHabits(): Flow<Result<List<Habit>, Error>> {
        return habitsLocalDataSource.observeHabitsFromDatabase()
            .map {
                Result.Success(it)
            }.catch { e ->
                Result.Failure(
                    when (e) {
                        else -> UnknownError
                    }
                )
            }
    }

    override fun observeHabitById(id: Long): Flow<Result<Habit, Error>> {
        return habitsLocalDataSource.observeHabitById(id)
            .map {
                Result.Success(it)
            }.catch { e ->
                Result.Failure(
                    when (e) {
                        else -> UnknownError
                    }
                )
            }
    }

    override fun observeHabitDetailsById(id: Long): Flow<Result<HabitDetails, Error>> {
        return habitsLocalDataSource.observeHabitDetailsFromDatabase(id)
            .map {
                Result.Success(it)
            }.catch { e ->
                Result.Failure(
                    when (e) {
                        else -> UnknownError
                    }
                )
            }
    }

    override suspend fun upsertHabit(habit: Habit): Result<Long, Error> {
        return try {
            Result.Success(habitsLocalDataSource.upsertHabit(habit))
        } catch (e: Exception) {
            Result.Failure(
                when (e) {
                    else -> UnknownError
                }
            )
        }
    }
}