package com.artem_obrazumov.habits.common.data.converters

import androidx.room.TypeConverter
import com.artem_obrazumov.habits.features.habits.domain.model.GoalType

class GoalTypeConverter {

    private val codesMap = GoalType.entries.associateWith { it.name }
    private val goalTypeMap = GoalType.entries.associateBy { it.toString() }

    @TypeConverter
    fun fromGoalType(goalType: GoalType): String {
        return codesMap[goalType] ?: throw IllegalArgumentException("Unknown GoalType: ${goalType.name}")
    }

    @TypeConverter
    fun toGoalType(goalTypeString: String): GoalType {
        return goalTypeMap[goalTypeString] ?: throw IllegalArgumentException("Unknown GoalType: $goalTypeString")
    }
}