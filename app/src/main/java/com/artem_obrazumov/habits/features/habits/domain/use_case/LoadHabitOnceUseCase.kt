package com.artem_obrazumov.habits.features.habits.domain.use_case

import com.artem_obrazumov.habits.common.domain.Error
import com.artem_obrazumov.habits.common.domain.Result
import com.artem_obrazumov.habits.common.domain.UnknownError
import com.artem_obrazumov.habits.features.habits.domain.data_source.HabitsLocalDataSource
import com.artem_obrazumov.habits.features.habits.domain.model.Habit
import kotlinx.coroutines.flow.first

/*
* Loads habit by id once (for example, for habit editor)
 */
class LoadHabitOnceUseCase(
    private val habitsLocalDataSource: HabitsLocalDataSource
) {

    suspend operator fun invoke(id: Long): Result<Habit, Error> {
        return try {
            Result.Success(habitsLocalDataSource.observeHabitById(id).first())
        } catch (e: Exception) {
            Result.Failure(UnknownError)
        }
    }
}