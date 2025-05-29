package com.artem_obrazumov.habits.features.habits.domain.use_case

import com.artem_obrazumov.habits.common.domain.Result
import com.artem_obrazumov.habits.common.domain.UnknownError
import com.artem_obrazumov.habits.features.habits.domain.data_source.HabitsLocalDataSource
import com.artem_obrazumov.habits.features.habits.domain.model.GoalType
import com.artem_obrazumov.habits.features.habits.domain.model.Habit
import com.artem_obrazumov.habits.features.habits.domain.model.ProgressFrequency
import org.junit.Test
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import java.time.LocalDate
import java.time.LocalDateTime

class LoadHabitsUseCaseTest {

    private val mockLocalDataSource: HabitsLocalDataSource = mockk()
    private val loadHabitUseCase = LoadHabitsUseCase(mockLocalDataSource)

    @Test
    fun `returns success result correctly`() = runTest {
        val habits = listOf(
            Habit(
                id = 1,
                name = "Test habit",
                measurement = "meters",
                goalType = GoalType.SINGLE,
                frequency = ProgressFrequency.DAILY,
                progress = 0.1,
                goal = 10.0,
                startedAt = LocalDate.parse("2025-05-01"),
                editedAt = LocalDateTime.parse("2025-05-01T12:00:00"),
                usersCount = 1
            )
        )
        coEvery { mockLocalDataSource.observeHabitsFromDatabase() } returns flowOf(habits)

        val result = loadHabitUseCase()
        assert(result is Result.Success)

        assertEquals((result as Result.Success).data.first(), habits)
    }

    @Test
    fun `returns failure result correctly`() = runTest {
        coEvery { mockLocalDataSource.observeHabitsFromDatabase() } throws Exception("Test exception")

        val result = loadHabitUseCase()
        assert(result is Result.Failure)

        assertEquals((result as Result.Failure).error, UnknownError)
    }
}