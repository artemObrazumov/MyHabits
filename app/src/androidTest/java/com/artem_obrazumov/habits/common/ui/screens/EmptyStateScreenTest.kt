package com.artem_obrazumov.habits.common.ui.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class EmptyStateScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displays_title_and_subtitle() {

        val title = "No content test title"
        val subtitle = "No content test subtitle"

        composeTestRule.setContent {
            EmptyStateScreen(
                title = title,
                subtitle = subtitle
            )
        }

        composeTestRule.onNodeWithText(title)
            .assertExists()

        composeTestRule.onNodeWithText(subtitle)
            .assertExists()
    }
}