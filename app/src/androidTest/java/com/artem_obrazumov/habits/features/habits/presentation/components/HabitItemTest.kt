package com.artem_obrazumov.habits.features.habits.presentation.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import com.artem_obrazumov.habits.features.habits.domain.model.GoalType
import com.artem_obrazumov.habits.features.habits.domain.model.Habit
import com.artem_obrazumov.habits.features.habits.domain.model.ProgressFrequency
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime

class HabitItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val testHabit = Habit(
        id = 5,
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

    @Test
    fun displays_correct_name() {
        composeTestRule.setContent {
            HabitItem(habit = testHabit)
        }

        composeTestRule.onNodeWithText("Test habit")
            .assertExists()
    }

    @Test
    fun displays_correct_date() {
        composeTestRule.setContent {
            HabitItem(habit = testHabit)
        }

        composeTestRule.onNodeWithText("01.05.2025")
            .assertExists()
    }

    @Test
    fun displays_correct_progress() {
        composeTestRule.setContent {
            HabitItem(habit = testHabit)
        }

        composeTestRule.onNodeWithText("Прогресс: 0.1 из 10 meters")
            .assertExists()
    }

    @Test
    fun displays_correct_progress_percentage() {
        composeTestRule.setContent {
            HabitItem(habit = testHabit)
        }

        composeTestRule.onNodeWithText("1%")
            .assertExists()
    }

    @Test
    fun not_showing_users_if_single() {
        composeTestRule.setContent {
            HabitItem(habit = testHabit)
        }

        composeTestRule.onNodeWithText("1 участник")
            .assertDoesNotExist()
    }

    @Test
    fun displays_correct_user_plural1() {
        composeTestRule.setContent {
            HabitItem(
                habit = testHabit
                    .copy(usersCount = 2)
            )
        }

        composeTestRule.onNodeWithText("2 участника")
            .assertExists()
    }

    @Test
    fun displays_correct_user_plural2() {
        composeTestRule.setContent {
            HabitItem(
                habit = testHabit
                    .copy(usersCount = 5)
            )
        }

        composeTestRule.onNodeWithText("5 участников")
            .assertExists()
    }

    @Test
    fun handles_clicks() {

        var clicked = false

        composeTestRule.setContent {
            HabitItem(
                habit = testHabit,
                onHabitClicked = { clicked = true }
            )
        }

        composeTestRule.onRoot()
            .performClick()

        assert(clicked)
    }
}