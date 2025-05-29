package com.artem_obrazumov.habits.features.habits.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.artem_obrazumov.habits.features.auth.data.local.entity.UserEntity
import com.artem_obrazumov.habits.features.habits.data.local.entity.HabitUsersEntity.Companion.HABIT_ID
import com.artem_obrazumov.habits.features.habits.data.local.entity.HabitUsersEntity.Companion.HABIT_USERS_TABLE
import com.artem_obrazumov.habits.features.habits.data.local.entity.HabitUsersEntity.Companion.USER_ID

@Entity(
    tableName = HABIT_USERS_TABLE,
    primaryKeys = [USER_ID, HABIT_ID],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = [UserEntity.ID],
            childColumns = [USER_ID],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = HabitEntity::class,
            parentColumns = [HabitEntity.ID],
            childColumns = [HABIT_ID],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = [HABIT_ID]),
        Index(value = [USER_ID])
    ]
)
data class HabitUsersEntity(
    @ColumnInfo(name = USER_ID)
    val userId: Long,
    @ColumnInfo(name = HABIT_ID)
    val habitId: Long
) {

    companion object {

        const val HABIT_USERS_TABLE = "habit_users_table"
        const val USER_ID = "user_id"
        const val HABIT_ID = "habit_id"
    }
}
