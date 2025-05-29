package com.artem_obrazumov.habits.features.habits.domain.use_case

import com.artem_obrazumov.habits.common.domain.Error
import com.artem_obrazumov.habits.common.domain.Result
import com.artem_obrazumov.habits.common.domain.UnknownError
import com.artem_obrazumov.habits.features.habits.domain.data_source.HabitsLocalDataSource
import com.artem_obrazumov.habits.features.habits.domain.model.HabitDetails
import kotlinx.coroutines.flow.Flow

class LoadHabitDetailsUseCase(
    private val habitsLocalDataSource: HabitsLocalDataSource
) {

    suspend operator fun invoke(id: Long): Result<Flow<HabitDetails>, Error> {
        return try {
            Result.Success(habitsLocalDataSource.observeHabitDetailsFromDatabase(id))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(UnknownError)
        }
    }
}