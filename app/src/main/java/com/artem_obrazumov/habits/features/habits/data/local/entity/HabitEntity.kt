package com.artem_obrazumov.habits.features.habits.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.artem_obrazumov.habits.features.habits.domain.model.GoalType
import com.artem_obrazumov.habits.features.habits.domain.model.ProgressFrequency
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Long,
//    @ColumnInfo(name = NAME)
//    val name: String,
//    @ColumnInfo(name = MEASUREMENT)
//    val measurement: String,
//    @ColumnInfo(name = GOAL_TYPE)
//    val goalType: GoalType,
//    val frequency: ProgressFrequency,
//    val progress: Double,
//    val goal: Double,
//    val startedAt: LocalDate,
//    val editedAt: LocalDateTime,
//    val usersCount: Int,
) {
    companion object {

        const val ID = "id"
        const val NAME = "name"
        const val MEASUREMENT = "measurement"
        const val GOAL_TYPE = "goal_type"
    }
}
