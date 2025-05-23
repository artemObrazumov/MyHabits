package com.artem_obrazumov.habits.features.habits.domain.use_case

import com.artem_obrazumov.habits.features.habits.domain.data_source.HabitsLocalDataSource
import org.junit.Test
import io.mockk.coEvery
import io.mockk.mockk

class LoadHabitsUseCaseTest {

    private val mockLocalDataSource: HabitsLocalDataSource = mockk()
    private val loadHabitUseCase = LoadHabitsUseCase(mockLocalDataSource)

    @Test

}