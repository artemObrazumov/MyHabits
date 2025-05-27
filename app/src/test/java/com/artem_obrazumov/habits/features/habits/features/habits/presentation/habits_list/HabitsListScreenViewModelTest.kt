package com.artem_obrazumov.habits.features.habits.features.habits.presentation.habits_list

import app.cash.turbine.test
import com.artem_obrazumov.habits.common.domain.Result
import com.artem_obrazumov.habits.common.domain.UnknownError
import com.artem_obrazumov.habits.features.habits.domain.model.GoalType
import com.artem_obrazumov.habits.features.habits.domain.model.Habit
import com.artem_obrazumov.habits.features.habits.domain.model.ProgressFrequency
import com.artem_obrazumov.habits.features.habits.domain.use_case.LoadHabitsUseCase
import com.artem_obrazumov.habits.features.habits.presentation.habits_list.HabitsListScreenAction
import com.artem_obrazumov.habits.features.habits.presentation.habits_list.HabitsListScreenEffect
import com.artem_obrazumov.habits.features.habits.presentation.habits_list.HabitsListScreenState
import com.artem_obrazumov.habits.features.habits.presentation.habits_list.HabitsListScreenViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class HabitsListScreenViewModelTest {

    private val mockUseCase: LoadHabitsUseCase = mockk()
    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: HabitsListScreenViewModel

    private val habits = listOf(
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

    private val habits2 = habits.map { it.copy(id = (it.id ?: 0) + 1) }

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun setToDefaults() {
        Dispatchers.resetMain()
    }

    @Test
    fun `updates state when habits loaded with failure`() = runTest {

        coEvery { mockUseCase.invoke() } coAnswers {
            delay(100)
            Result.Failure(UnknownError)
        }

        viewModel = HabitsListScreenViewModel(mockUseCase)

        viewModel.state.test {
            assertEquals(HabitsListScreenState.Loading, awaitItem())
            assert(awaitItem() is HabitsListScreenState.Failure)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `updates state when habits loaded successfully`() = runTest {

        val testFlow = flow {
            delay(100)
            emit(habits)
            delay(100)
            emit(habits2)
        }

        coEvery { mockUseCase.invoke() } returns Result.Success(testFlow)

        viewModel = HabitsListScreenViewModel(mockUseCase)

        viewModel.state.test {
            assertEquals(HabitsListScreenState.Loading, awaitItem())
            assertEquals(habits, (awaitItem() as HabitsListScreenState.Content).habitsList)
            assertEquals(habits2, (awaitItem() as HabitsListScreenState.Content).habitsList)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emits NavigateToAddHabitScreen effect on AddHabit action`() = runTest {

        val testFlow = flowOf(habits)

        coEvery { mockUseCase.invoke() } returns Result.Success(testFlow)

        viewModel = HabitsListScreenViewModel(mockUseCase)

        viewModel.effect.test {
            viewModel.onAction(HabitsListScreenAction.AddHabit)
            assertEquals(HabitsListScreenEffect.NavigateToAddHabitScreen, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emits NavigateToHabitDetails effect on OpenHabitDetails action`() = runTest {

        val testFlow = flowOf(habits)
        val id = 101L

        coEvery { mockUseCase.invoke() } returns Result.Success(testFlow)

        viewModel = HabitsListScreenViewModel(mockUseCase)

        viewModel.effect.test {
            viewModel.onAction(HabitsListScreenAction.OpenHabitDetails(id))
            assertEquals(HabitsListScreenEffect.NavigateToHabitDetailsScreen(id), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `reload habits on retry action`() = runTest {

        val testFlow = flow {
            delay(100)
            emit(habits)
        }

        coEvery { mockUseCase.invoke() } returns Result.Failure(UnknownError)

        viewModel = HabitsListScreenViewModel(mockUseCase)

        coEvery { mockUseCase.invoke() } returns Result.Success(testFlow)

        viewModel.state.test {
            assert(awaitItem() is HabitsListScreenState.Failure)
            cancelAndIgnoreRemainingEvents()
        }

        viewModel.onAction(HabitsListScreenAction.Retry)

        viewModel.state.test {
            assertEquals(HabitsListScreenState.Loading, awaitItem())
            assertEquals(HabitsListScreenState.Content(habits), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}