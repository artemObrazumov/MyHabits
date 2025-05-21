package com.artem_obrazumov.habits.common.ui.util

import org.junit.Assert.assertEquals
import org.junit.Test

class PluralStringTest {

    @Test
    fun `Correctly select the right string form`() {
        val single = "пользователь"
        val plural1 = "пользователя"
        val plural2 = "пользователей"
        val testCases = mapOf(
            // single
            1 to single,
            21 to single,
            31 to single,
            101 to single,

            // plural1
            2 to plural1,
            3 to plural1,
            4 to plural1,
            22 to plural1,
            23 to plural1,
            24 to plural1,
            32 to plural1,

            // plural2
            5 to plural2,
            11 to plural2,
            15 to plural2,
            20 to plural2,
            25 to plural2,
            30 to plural2,
            100 to plural2
        )
        testCases.forEach { (count, correct) ->
            assertEquals(getPluralString(count, single, plural1, plural2), correct)
        }
    }
}