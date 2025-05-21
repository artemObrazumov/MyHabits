package com.artem_obrazumov.habits.common.ui.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class FailureScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val message = "Test error message"

    @Test
    fun displays_correct_message() {

        composeTestRule.setContent {
            FailureScreen(message = message)
        }

        composeTestRule.onNodeWithText(message)
            .assertExists()
    }

    @Test
    fun handles_clicks() {

        var clicked = false

        composeTestRule.setContent {
            FailureScreen(
                message = message,
                onRetry = { clicked = true }
            )
        }

        composeTestRule.onNodeWithTag(FailureScreenTags.RETRY_BUTTON_TAG)
            .performClick()

        assert(clicked)
    }
}