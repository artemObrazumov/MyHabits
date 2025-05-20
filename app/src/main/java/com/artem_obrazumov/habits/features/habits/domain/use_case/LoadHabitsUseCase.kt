package com.artem_obrazumov.habits.features.habits.domain.use_case

import com.artem_obrazumov.habits.common.domain.Error
import com.artem_obrazumov.habits.common.domain.Result
import com.artem_obrazumov.habits.common.domain.UnknownError
import com.artem_obrazumov.habits.features.habits.domain.data_source.HabitsLocalDataSource
import com.artem_obrazumov.habits.features.habits.domain.model.Habit
import kotlinx.coroutines.flow.Flow

/*
* Syncs habits list and return a habits list flow from database
*/
class LoadHabitsUseCase(
    private val habitsLocalDataSource: HabitsLocalDataSource
) {

    suspend operator fun invoke(): Result<Flow<List<Habit>>, Error> {
        return try {
            Result.Success(habitsLocalDataSource.observeHabitsFromDatabase())
        } catch (e: Exception) {
            Result.Failure(UnknownError)
        }
    }
}