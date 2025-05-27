package com.artem_obrazumov.habits.features.habits.presentation.habits_list

class HabitsEditorFormValidator {

    fun validate(
        habitName: String,
        measurement: String,
        start: Double?,
        goal: Double?,
    ): HabitEditorFormValidationResult {
        return if (habitName.length < HabitsEditorFormConstants.HABIT_NAME_MIN_LENGTH) {
            HabitEditorFormValidationResult.HabitNameTooShort
        } else if (habitName.length > HabitsEditorFormConstants.HABIT_NAME_MAX_LENGTH) {
            HabitEditorFormValidationResult.HabitNameTooLong
        } else if (measurement.length < HabitsEditorFormConstants.MEASUREMENT_MIN_LENGTH) {
            HabitEditorFormValidationResult.MeasurementTooShort
        } else if (measurement.length > HabitsEditorFormConstants.MEASUREMENT_MAX_LENGTH) {
            HabitEditorFormValidationResult.MeasurementTooLong
        } else if (start == null) {
            HabitEditorFormValidationResult.InvalidStart
        } else if (goal == null) {
            HabitEditorFormValidationResult.InvalidGoal
        } else if (goal <= start) {
            HabitEditorFormValidationResult.GoalNotHigherThanStart
        } else {
            HabitEditorFormValidationResult.Correct
        }
    }
}

object HabitsEditorFormConstants {

    const val HABIT_NAME_MIN_LENGTH = 3
    const val HABIT_NAME_MAX_LENGTH = 32
    const val MEASUREMENT_MIN_LENGTH = 1
    const val MEASUREMENT_MAX_LENGTH = 16
}

sealed class HabitEditorFormValidationResult {

    data object HabitNameTooShort: HabitEditorFormValidationResult()
    data object HabitNameTooLong: HabitEditorFormValidationResult()
    data object MeasurementTooShort: HabitEditorFormValidationResult()
    data object MeasurementTooLong: HabitEditorFormValidationResult()
    data object InvalidStart: HabitEditorFormValidationResult()
    data object InvalidGoal: HabitEditorFormValidationResult()
    data object GoalNotHigherThanStart: HabitEditorFormValidationResult()

    data object Correct: HabitEditorFormValidationResult()
}
