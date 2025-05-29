package com.artem_obrazumov.habits.features.habits.presentation.habits_list

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.artem_obrazumov.habits.common.ui.screens.EmptyStateScreenTags
import com.artem_obrazumov.habits.common.ui.screens.FailureScreenTags
import com.artem_obrazumov.habits.common.ui.util.UIText
import com.artem_obrazumov.habits.features.habits.domain.model.GoalType
import com.artem_obrazumov.habits.features.habits.domain.model.Habit
import com.artem_obrazumov.habits.features.habits.domain.model.ProgressFrequency
import com.artem_obrazumov.habits.features.habits.presentation.components.HabitItemTags
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime

class HabitsListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displays_loading_state() {
        val loadingState = HabitsListScreenState.Loading

        composeTestRule.setContent {
            HabitsListScreenContent(
                state = loadingState
            )
        }

        composeTestRule.onNodeWithTag(HabitsListScreenTags.HABITS_LIST_LOADING)
            .assertExists()
    }

    private val failureState = HabitsListScreenState.Failure(
        errorMessage = UIText.DynamicText("Failure habits list screen message")
    )

    @Test
    fun displays_failure_state() {

        composeTestRule.setContent {
            HabitsListScreenContent(
                state = failureState
            )
        }

        composeTestRule.onNodeWithTag(FailureScreenTags.FAILURE_SCREEN)
            .assertExists()
    }

    @Test
    fun handles_failure_screen_clicks() {

        var clicked = false

        composeTestRule.setContent {
            HabitsListScreenContent(
                state = failureState,
                onAction = { action ->
                    if (action is HabitsListScreenAction.Retry) {
                        clicked = true
                    }
                }
            )
        }

        composeTestRule.onNodeWithTag(FailureScreenTags.RETRY_BUTTON_TAG)
            .performClick()

        assert(clicked)
    }

    @Test
    fun displays_content_empty_state() {
        val emptyContentState = HabitsListScreenState.Content(emptyList())

        composeTestRule.setContent {
            HabitsListScreenContent(
                state = emptyContentState
            )
        }

        composeTestRule.onNodeWithTag(EmptyStateScreenTags.EMPTY_STATE_SCREEN)
            .assertExists()
    }

    private val habitItemTemplate = Habit(
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

    private val contentState = HabitsListScreenState.Content(
        listOf(
            habitItemTemplate,
            habitItemTemplate.copy(id = 2),
            habitItemTemplate.copy(id = 3),
        )
    )

    @Test
    fun displays_content_state() {

        composeTestRule.setContent {
            HabitsListScreenContent(
                state = contentState
            )
        }

        composeTestRule.onNodeWithTag(HabitsListScreenTags.HABITS_LIST_CONTENT)
            .assertExists()

        composeTestRule.onAllNodesWithText(habitItemTemplate.name)
            .assertCountEquals(3)

        composeTestRule.onAllNodesWithTag(HabitItemTags.HABIT_ITEM)
            .assertCountEquals(3)
    }

    @Test
    fun handles_content_screen_habits_clicks() {

        val clicked = BooleanArray(3) { false }

        composeTestRule.setContent {
            HabitsListScreenContent(
                state = contentState,
                onAction = { action ->
                    if (action is HabitsListScreenAction.OpenHabitDetails) {
                        clicked[action.id.toInt() - 1] = true
                    }
                }
            )
        }

        val habitCards = composeTestRule.onAllNodesWithTag(HabitItemTags.HABIT_ITEM)

        habitCards[0].performClick()
        assert(clicked[0])

        habitCards[1].performClick()
        assert(clicked[1])

        habitCards[2].performClick()
        assert(clicked[2])
    }

    @Test
    fun content_state_displays_add_button_and_handles_clicks() {

        var clicked = false

        composeTestRule.setContent {
            HabitsListScreenContent(
                state = contentState,
                onAction = { action ->
                    if (action is HabitsListScreenAction.AddHabit) {
                        clicked = true
                    }
                }
            )
        }

        composeTestRule.onNodeWithTag(HabitsListScreenTags.HABITS_LIST_ADD_BUTTON)
            .performClick()

        assert(clicked)
    }
}