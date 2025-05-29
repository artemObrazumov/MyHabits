package com.artem_obrazumov.habits.common.ui.util

import org.junit.Assert.assertEquals
import org.junit.Test

class NumberFormattersTest {

    @Test
    fun `Removes trailing zeros`() {
        assertEquals("1", 1.0.formatToString())
        assertEquals("1.5", 1.5.formatToString())
        assertEquals("1.5", 1.500.formatToString())
    }

    @Test
    fun `Correctly processing digits parameter`() {
        assertEquals("1", 1.5.formatToString(digits = 0))
        assertEquals("1.5", 1.52.formatToString(digits = 1))
        assertEquals("1.6", 1.599.formatToString(digits = 1))
        assertEquals("1.400", 1.4.formatToString(digits = 3))
    }
}