package com.artem_obrazumov.habits.features.habits.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.artem_obrazumov.habits.features.habits.data.local.entity.HabitEntity.Companion.HABIT_TABLE
import com.artem_obrazumov.habits.features.habits.domain.model.GoalType
import com.artem_obrazumov.habits.features.habits.domain.model.Habit
import com.artem_obrazumov.habits.features.habits.domain.model.ProgressFrequency
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(tableName = HABIT_TABLE)
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Long?,
    @ColumnInfo(name = NAME)
    val name: String,
    @ColumnInfo(name = MEASUREMENT)
    val measurement: String,
    @ColumnInfo(name = GOAL_TYPE)
    val goalType: GoalType,
    @ColumnInfo(name = FREQUENCY)
    val frequency: ProgressFrequency,
    @ColumnInfo(name = PROGRESS)
    val progress: Double,
    @ColumnInfo(name = GOAL)
    val goal: Double,
    @ColumnInfo(name = STARTED_AT)
    val startedAt: LocalDate,
    @ColumnInfo(name = EDITED_AT)
    val editedAt: LocalDateTime,
    @ColumnInfo(name = USERS_COUNT)
    val usersCount: Int,
    @ColumnInfo(name = IS_DELETED)
    val isDeleted: Boolean,
) {
    companion object {

        const val HABIT_TABLE = "habits"
        const val ID = "id"
        const val NAME = "name"
        const val MEASUREMENT = "measurement"
        const val GOAL_TYPE = "goal_type"
        const val FREQUENCY = "frequency"
        const val PROGRESS = "progress"
        const val GOAL = "goal"
        const val STARTED_AT = "started_at"
        const val EDITED_AT = "edited_at"
        const val USERS_COUNT = "users_count"
        const val IS_DELETED = "is_deleted"
    }
}

fun HabitEntity.toHabit(): Habit {
    return Habit(
        id = id,
        name = name,
        measurement = measurement,
        goalType = goalType,
        frequency = frequency,
        progress = progress,
        goal = goal,
        startedAt = startedAt,
        editedAt = editedAt,
        usersCount = usersCount
    )
}

fun Habit.toHabitEntity(): HabitEntity {
    return HabitEntity(
        id = id,
        name = name,
        measurement = measurement,
        goalType = goalType,
        frequency = frequency,
        progress = progress,
        goal = goal,
        startedAt = startedAt,
        editedAt = editedAt,
        usersCount = usersCount,
        isDeleted = false
    )
}
