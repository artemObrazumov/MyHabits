package com.artem_obrazumov.habits.features.habits.domain.use_case

import com.artem_obrazumov.habits.common.domain.Error
import com.artem_obrazumov.habits.common.domain.Result
import com.artem_obrazumov.habits.common.domain.UnknownError
import com.artem_obrazumov.habits.features.habits.domain.data_source.HabitsLocalDataSource
import com.artem_obrazumov.habits.features.habits.domain.model.GoalType
import com.artem_obrazumov.habits.features.habits.domain.model.Habit
import com.artem_obrazumov.habits.features.habits.domain.model.ProgressFrequency
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime

class LoadHabitOnceUseCaseTest {

    private val mockDataSource: HabitsLocalDataSource = mockk()
    private val loadHabitOnceUseCase = LoadHabitOnceUseCase(mockDataSource)
    private val id = 1L

    private val habit = Habit(
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
    private val habit2 = habit.copy(id = 2)

    @Test
    fun `returns success result correctly`() = runTest {

        coEvery { mockDataSource.observeHabitById(id) } returns flowOf(habit)

        val result = loadHabitOnceUseCase(id)
        assert(result is Result.Success)
        assertEquals((result as Result.Success).data, habit)
    }

    @Test
    fun `doesnt update when success`() = runTest {

        val testFlow = flow {
            delay(100)
            emit(habit)
            delay(100)
            emit(habit2)
        }

        coEvery { mockDataSource.observeHabitById(id) } returns testFlow

        var result: Result<Habit, Error>? = null
        launch { result = loadHabitOnceUseCase(id) }
        launch {
            delay(300)
            assert(result is Result.Success)
            assertEquals((result as Result.Success).data, habit)
        }
    }

    @Test
    fun `returns failure result correctly`() = runTest {
        coEvery { mockDataSource.observeHabitById(id) } throws Exception("Test exception")

        val result = loadHabitOnceUseCase(id)
        assert(result is Result.Failure)
        assertEquals((result as Result.Failure).error, UnknownError)
    }
}