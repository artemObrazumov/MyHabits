package com.artem_obrazumov.habits.features.habits.data.local.converters

import com.artem_obrazumov.habits.common.data.converters.GoalTypeConverter
import com.artem_obrazumov.habits.features.habits.domain.model.GoalType
import org.junit.Assert.assertEquals
import org.junit.Test

class GoalTypeConverterTest {

    private val converter = GoalTypeConverter()

    @Test
    fun `convert SINGLE enum to string`() {
        assertEquals("SINGLE", converter.fromGoalType(GoalType.SINGLE))
    }

    @Test
    fun `convert FREQUENT enum to string`() {
        assertEquals("FREQUENT", converter.fromGoalType(GoalType.FREQUENT))
    }

    @Test
    fun `convert SINGLE string to enum`() {
        assertEquals(GoalType.SINGLE, converter.toGoalType("SINGLE"))
    }

    @Test
    fun `convert FREQUENT string to enum`() {
        assertEquals(GoalType.FREQUENT, converter.toGoalType("FREQUENT"))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `throw on invalid string value`() {
        converter.toGoalType("INVALID")
    }
}