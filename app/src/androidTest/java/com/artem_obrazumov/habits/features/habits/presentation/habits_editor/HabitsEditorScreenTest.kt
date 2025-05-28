package com.artem_obrazumov.habits.features.habits.presentation.habits_editor

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.artem_obrazumov.habits.common.ui.components.text.MessageTestTags
import com.artem_obrazumov.habits.common.ui.screens.FailureScreenTags
import com.artem_obrazumov.habits.common.ui.util.Message
import com.artem_obrazumov.habits.common.ui.util.UIText
import com.artem_obrazumov.habits.features.habits.domain.model.GoalType
import com.artem_obrazumov.habits.features.habits.domain.model.ProgressFrequency
import com.artem_obrazumov.habits.features.habits.presentation.util.asString
import org.junit.Rule
import org.junit.Test

class HabitsEditorScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displays_loading_state() {

        val loadingState = HabitsEditorScreenState.Loading

        composeTestRule.setContent {
            HabitsEditorScreenContent(
                state = loadingState
            )
        }

        composeTestRule.onNodeWithTag(HabitsEditorScreenTags.HABITS_EDITOR_LOADING)
            .assertExists()
    }

    private val failureState = HabitsEditorScreenState.Failure(
        errorMessage = UIText.DynamicText("Failure habits editor screen message")
    )

    @Test
    fun displays_failure_state() {

        composeTestRule.setContent {
            HabitsEditorScreenContent(
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
            HabitsEditorScreenContent(
                state = failureState,
                onAction = { action ->
                    if (action is HabitsEditorScreenAction.Retry) {
                        clicked = true
                    }
                }
            )
        }

        composeTestRule.onNodeWithTag(FailureScreenTags.RETRY_BUTTON_TAG)
            .performClick()

        assert(clicked)
    }

    private val contentState = HabitsEditorScreenState.Content(
        formState = FormState(),
        loadingState = LoadingState()
    )

    @Test
    fun displays_content_state() {

        composeTestRule.setContent {
            HabitsEditorScreenContent(
                state = contentState
            )
        }

        composeTestRule.onNodeWithTag(HabitsEditorScreenTags.HABITS_EDITOR_CONTENT)
            .performClick()
    }

    @Test
    fun displays_form_values() {

        val newFormState = FormState(
            name = "test form habit name",
            goalTypeOption = GoalType.FREQUENT,
            frequencyOption = ProgressFrequency.ONCE_IN_3_DAYS,
            measurement = "test measurement",
            startString = "4.5",
            goalString = "99.2",
        )

        var context: Context? = null

        composeTestRule.setContent {
            HabitsEditorScreenContent(
                state = contentState.copy(
                    formState = newFormState
                )
            )
            context = LocalContext.current
        }

        val goalTypeString = newFormState.goalTypeOption.asString(context!!)
        val frequencyString = newFormState.frequencyOption.asString(context!!)

        composeTestRule.onNodeWithText(newFormState.name)
            .assertExists()

        composeTestRule.onNodeWithText(goalTypeString)
            .assertExists()

        composeTestRule.onNodeWithText(frequencyString)
            .assertExists()

        composeTestRule.onNodeWithText(newFormState.measurement)
            .assertExists()

        composeTestRule.onNodeWithText(newFormState.startString)
            .assertExists()

        composeTestRule.onNodeWithText(newFormState.goalString)
            .assertExists()
    }

    @Test
    fun displays_loading_message_in_correct_element() {

        val messageText = "Text message string"

        val newFormLoadingState = LoadingState(
            message = Message.Error(UIText.DynamicText(messageText))
        )

        composeTestRule.setContent {
            HabitsEditorScreenContent(
                state = contentState.copy(
                    loadingState = newFormLoadingState
                )
            )
        }

        composeTestRule.onNodeWithTag(MessageTestTags.MESSAGE_TEXT)
            .assertTextEquals(messageText)
    }
}