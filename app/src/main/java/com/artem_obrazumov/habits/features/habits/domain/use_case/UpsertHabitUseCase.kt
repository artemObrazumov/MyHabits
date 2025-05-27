package com.artem_obrazumov.habits.features.habits.domain.use_case

import com.artem_obrazumov.habits.common.domain.Error
import com.artem_obrazumov.habits.common.domain.Result
import com.artem_obrazumov.habits.common.domain.UnknownError
import com.artem_obrazumov.habits.features.habits.domain.data_source.HabitsLocalDataSource
import com.artem_obrazumov.habits.features.habits.domain.model.Habit

/*
* Upsert habit in data sources
 */
class UpsertHabitUseCase(
    private val habitsLocalDataSource: HabitsLocalDataSource
) {

    suspend operator fun invoke(habit: Habit): Result<Unit, Error> {
        return try {
            habitsLocalDataSource.upsertHabit(habit)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(UnknownError)
        }
    }
}