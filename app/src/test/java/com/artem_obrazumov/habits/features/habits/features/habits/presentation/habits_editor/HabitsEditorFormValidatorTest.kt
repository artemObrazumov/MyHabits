package com.artem_obrazumov.habits.features.habits.features.habits.presentation.habits_editor

import com.artem_obrazumov.habits.features.habits.presentation.habits_editor.HabitEditorFormValidationResult
import com.artem_obrazumov.habits.features.habits.presentation.habits_editor.HabitsEditorFormValidator
import junit.framework.TestCase.assertEquals
import org.junit.Test

class HabitsEditorFormValidatorTest {

    private val validator = HabitsEditorFormValidator()

    @Test
    fun `returns Correct for valid input`() {
        val result = validator.validate(
            habitName = "Habit name",
            measurement = "%",
            start = 1.0,
            goal = 10.0
        )

        assertEquals(HabitEditorFormValidationResult.Correct, result)
    }

    @Test
    fun `returns HabitNameTooShort when name is too short`() {
        val result = validator.validate(
            habitName = "0",
            measurement = "%",
            start = 1.0,
            goal = 10.0
        )

        assertEquals(HabitEditorFormValidationResult.HabitNameTooShort, result)
    }

    @Test
    fun `returns HabitNameTooLong when name is too long`() {
        val result = validator.validate(
            habitName = "Way toooooooooooooooooooooooooo long habit name",
            measurement = "%",
            start = 1.0,
            goal = 10.0
        )

        assertEquals(HabitEditorFormValidationResult.HabitNameTooLong, result)
    }

    @Test
    fun `returns MeasurementTooShort when measurement is too short`() {
        val result = validator.validate(
            habitName = "Habit name",
            measurement = "",
            start = 1.0,
            goal = 10.0
        )

        assertEquals(HabitEditorFormValidationResult.MeasurementTooShort, result)
    }

    @Test
    fun `returns MeasurementTooLong when measurement is too long`() {
        val result = validator.validate(
            habitName = "Habit name",
            measurement = "Way toooooooooo long measurement",
            start = 1.0,
            goal = 10.0
        )

        assertEquals(HabitEditorFormValidationResult.MeasurementTooLong, result)
    }

    @Test
    fun `returns InvalidStart when start is null`() {
        val result = validator.validate(
            habitName = "Habit name",
            measurement = "%",
            start = null,
            goal = 10.0
        )

        assertEquals(HabitEditorFormValidationResult.InvalidStart, result)
    }

    @Test
    fun `returns InvalidGoal when goal is null`() {
        val result = validator.validate(
            habitName = "Habit name",
            measurement = "%",
            start = 1.0,
            goal = null
        )

        assertEquals(HabitEditorFormValidationResult.InvalidGoal, result)
    }

    @Test
    fun `returns GoalNotHigherThanStart when goal equals start`() {
        val result = validator.validate(
            habitName = "Habit name",
            measurement = "%",
            start = 5.0,
            goal = 5.0
        )

        assertEquals(HabitEditorFormValidationResult.GoalNotHigherThanStart, result)
    }

    @Test
    fun `returns GoalNotHigherThanStart when goal less than start`() {
        val result = validator.validate(
            habitName = "Habit name",
            measurement = "%",
            start = 10.0,
            goal = 5.0
        )

        assertEquals(HabitEditorFormValidationResult.GoalNotHigherThanStart, result)
    }
}