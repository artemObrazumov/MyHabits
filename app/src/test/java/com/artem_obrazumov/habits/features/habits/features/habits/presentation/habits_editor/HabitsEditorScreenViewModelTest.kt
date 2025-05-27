package com.artem_obrazumov.habits.features.habits.features.habits.presentation.habits_editor

import app.cash.turbine.test
import com.artem_obrazumov.habits.common.domain.Result
import com.artem_obrazumov.habits.common.domain.UnknownError
import com.artem_obrazumov.habits.features.habits.domain.model.GoalType
import com.artem_obrazumov.habits.features.habits.domain.model.Habit
import com.artem_obrazumov.habits.features.habits.domain.model.ProgressFrequency
import com.artem_obrazumov.habits.features.habits.domain.use_case.LoadHabitOnceUseCase
import com.artem_obrazumov.habits.features.habits.domain.use_case.UpsertHabitUseCase
import com.artem_obrazumov.habits.features.habits.presentation.habits_editor.FormState
import com.artem_obrazumov.habits.features.habits.presentation.habits_editor.HabitsEditorFormValidator
import com.artem_obrazumov.habits.features.habits.presentation.habits_editor.HabitsEditorScreenAction
import com.artem_obrazumov.habits.features.habits.presentation.habits_editor.HabitsEditorScreenState
import com.artem_obrazumov.habits.features.habits.presentation.habits_editor.HabitsEditorScreenViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
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
class HabitsEditorScreenViewModelTest {

    private val mockLoadHabitUseCase: LoadHabitOnceUseCase = mockk()
    private val upsertHabitUseCase: UpsertHabitUseCase = mockk()
    private val habitsEditorFormValidator = HabitsEditorFormValidator()
    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: HabitsEditorScreenViewModel

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
        usersCount = 5
    )

    private fun initializeViewModel(id: Long? = null) {
        viewModel = HabitsEditorScreenViewModel(
            id = id,
            loadHabitOnceUseCase = mockLoadHabitUseCase,
            upsertHabitUseCase = upsertHabitUseCase,
            habitsEditorFormValidator = habitsEditorFormValidator
        )
    }

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun setToDefaults() {
        Dispatchers.resetMain()
    }

    @Test
    fun `updates state when habit loaded with failure`() = runTest {

        val id = 1L
        coEvery { mockLoadHabitUseCase.invoke(id) } coAnswers {
            delay(100)
            Result.Failure(UnknownError)
        }

        initializeViewModel(id)

        viewModel.state.test {
            assertEquals(HabitsEditorScreenState.Loading, awaitItem())
            assert(awaitItem() is HabitsEditorScreenState.Failure)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `updates state when habit loaded successfully`() = runTest {

        val id = 1L
        coEvery { mockLoadHabitUseCase.invoke(id) } coAnswers {
            delay(100)
            Result.Success(habit)
        }

        val expectedFormState = FormState(
            name = habit.name,
            goalTypeOption = habit.goalType,
            frequencyOption = habit.frequency,
            measurement = habit.measurement,
            startString = habit.progress.toString(),
            goalString = habit.goal.toString(),
            userCount = habit.usersCount,
            startedAt = habit.startedAt
        )

        initializeViewModel(id)

        viewModel.state.test {
            assertEquals(HabitsEditorScreenState.Loading, awaitItem())
            assert((awaitItem() as HabitsEditorScreenState.Content).formState == expectedFormState)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `updates state when id is empty`() = runTest {

        val expectedFormState = FormState()

        initializeViewModel()

        viewModel.state.test {
            assertEquals(HabitsEditorScreenState.Loading, awaitItem())
            assert((awaitItem() as HabitsEditorScreenState.Content).formState == expectedFormState)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should update form state when changing habit name`() = runTest {

        initializeViewModel()

        val name1 = "name 1"
        val name2 = "New name"

        viewModel.onAction(HabitsEditorScreenAction.ChangeHabitName(name1))
        viewModel.state.test {
            assertEquals(
                (awaitItem() as HabitsEditorScreenState.Content).formState.name,
                name1
            )
            cancelAndIgnoreRemainingEvents()
        }

        viewModel.onAction(HabitsEditorScreenAction.ChangeHabitName(name2))
        viewModel.state.test {
            assertEquals(
                (awaitItem() as HabitsEditorScreenState.Content).formState.name,
                name2
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should update form state when changing habit goal`() = runTest {

        initializeViewModel()

        val goal1 = GoalType.FREQUENT
        val goal2 = GoalType.SINGLE

        viewModel.onAction(HabitsEditorScreenAction.ChangeHabitGoalType(goal1))
        viewModel.state.test {
            assertEquals(
                (awaitItem() as HabitsEditorScreenState.Content).formState.goalTypeOption,
                goal1
            )
            cancelAndIgnoreRemainingEvents()
        }

        viewModel.onAction(HabitsEditorScreenAction.ChangeHabitGoalType(goal2))
        viewModel.state.test {
            assertEquals(
                (awaitItem() as HabitsEditorScreenState.Content).formState.goalTypeOption,
                goal2
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should update form state when changing frequency`() = runTest {

        initializeViewModel()

        val frequency1 = ProgressFrequency.WEEKLY
        val frequency2 = ProgressFrequency.DAILY

        viewModel.onAction(HabitsEditorScreenAction.ChangeHabitFrequency(frequency1))
        viewModel.state.test {
            assertEquals(
                (awaitItem() as HabitsEditorScreenState.Content).formState.frequencyOption,
                frequency1
            )
            cancelAndIgnoreRemainingEvents()
        }

        viewModel.onAction(HabitsEditorScreenAction.ChangeHabitFrequency(frequency2))
        viewModel.state.test {
            assertEquals(
                (awaitItem() as HabitsEditorScreenState.Content).formState.frequencyOption,
                frequency2
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should update form state when changing measurement`() = runTest {

        initializeViewModel()

        val measurement1 = "m"
        val measurement2 = "test 2"

        viewModel.onAction(HabitsEditorScreenAction.ChangeHabitMeasurement(measurement1))
        viewModel.state.test {
            assertEquals(
                (awaitItem() as HabitsEditorScreenState.Content).formState.measurement,
                measurement1
            )
            cancelAndIgnoreRemainingEvents()
        }

        viewModel.onAction(HabitsEditorScreenAction.ChangeHabitMeasurement(measurement2))
        viewModel.state.test {
            assertEquals(
                (awaitItem() as HabitsEditorScreenState.Content).formState.measurement,
                measurement2
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should update form state when changing start`() = runTest {

        initializeViewModel()

        val start1 = "0.0"
        val start2 = "12.2"

        viewModel.onAction(HabitsEditorScreenAction.ChangeHabitStartString(start1))
        viewModel.state.test {
            assertEquals(
                (awaitItem() as HabitsEditorScreenState.Content).formState.startString,
                start1
            )
            cancelAndIgnoreRemainingEvents()
        }

        viewModel.onAction(HabitsEditorScreenAction.ChangeHabitStartString(start2))
        viewModel.state.test {
            assertEquals(
                (awaitItem() as HabitsEditorScreenState.Content).formState.startString,
                start2
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should update form state when changing goal`() = runTest {

        initializeViewModel()

        val goal1 = "10.0"
        val goal2 = "12.2"

        viewModel.onAction(HabitsEditorScreenAction.ChangeHabitGoalString(goal1))
        viewModel.state.test {
            assertEquals(
                (awaitItem() as HabitsEditorScreenState.Content).formState.goalString,
                goal1
            )
            cancelAndIgnoreRemainingEvents()
        }

        viewModel.onAction(HabitsEditorScreenAction.ChangeHabitGoalString(goal2))
        viewModel.state.test {
            assertEquals(
                (awaitItem() as HabitsEditorScreenState.Content).formState.goalString,
                goal2
            )
            cancelAndIgnoreRemainingEvents()
        }
    }
}