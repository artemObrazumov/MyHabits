package com.artem_obrazumov.habits.features.habits.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.artem_obrazumov.habits.features.habits.data.local.entity.HabitEntity
import com.artem_obrazumov.habits.features.habits.data.local.entity.HabitEntity.Companion.HABIT_TABLE
import com.artem_obrazumov.habits.features.habits.data.local.entity.HabitEntity.Companion.ID
import com.artem_obrazumov.habits.features.habits.data.local.entity.HabitEntity.Companion.IS_DELETED
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Query("Select * From $HABIT_TABLE Where $IS_DELETED = 0")
    fun observeNotDeletedHabits(): Flow<List<HabitEntity>>

    @Query("Select * From $HABIT_TABLE Where $ID = :id")
    fun observeById(id: Long): Flow<HabitEntity>

    @Upsert
    suspend fun upsertHabit(habit: HabitEntity): Long
}