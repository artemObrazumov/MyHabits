package com.artem_obrazumov.habits.features.habits.domain.use_case

import com.artem_obrazumov.habits.common.domain.Error
import com.artem_obrazumov.habits.common.domain.Result
import com.artem_obrazumov.habits.common.domain.UnknownError
import com.artem_obrazumov.habits.features.habits.domain.data_source.HabitsLocalDataSource
import com.artem_obrazumov.habits.features.habits.domain.model.HabitDetails

class LoadHabitDetailsUseCase(
    private val habitsLocalDataSource: HabitsLocalDataSource
) {

    suspend operator fun invoke(): Result<HabitDetails, Error> {
        return try {
            TODO()
            //Result.Success(habitsLocalDataSource.)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(UnknownError)
        }
    }
}