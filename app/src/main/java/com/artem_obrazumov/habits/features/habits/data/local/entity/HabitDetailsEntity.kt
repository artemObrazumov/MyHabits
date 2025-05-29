package com.artem_obrazumov.habits.features.habits.data.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.artem_obrazumov.habits.features.auth.data.local.entity.UserEntity
import com.artem_obrazumov.habits.features.auth.data.local.entity.toUser
import com.artem_obrazumov.habits.features.habits.domain.model.HabitDetails

data class HabitDetailsEntity(

    @Embedded val habit: HabitEntity,
    @Relation(
        parentColumn = HabitEntity.ID,
        entityColumn = UserEntity.ID,
        associateBy = Junction(
            value = HabitUsersEntity::class,
            parentColumn = HabitUsersEntity.HABIT_ID,
            entityColumn = HabitUsersEntity.USER_ID,
        )
    )
    val users: List<UserEntity>
)

fun HabitDetailsEntity.toHabitDetails(): HabitDetails {
    return HabitDetails(
        habit = habit.toHabit(),
        users = users.map { it.toUser() }
    )
}
