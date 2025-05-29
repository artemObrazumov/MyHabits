package com.artem_obrazumov.habits.features.habits.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.artem_obrazumov.habits.features.habits.data.local.converters.DateTimeConverter
import com.artem_obrazumov.habits.features.habits.data.local.converters.GoalTypeConverter
import com.artem_obrazumov.habits.features.habits.data.local.converters.ProgressFrequencyConverter
import com.artem_obrazumov.habits.features.habits.data.local.dao.HabitDao
import com.artem_obrazumov.habits.features.habits.data.local.entity.HabitEntity

@Database(entities = [HabitEntity::class], version = 1)
@TypeConverters(DateTimeConverter::class, GoalTypeConverter::class, ProgressFrequencyConverter::class)
abstract class HabitDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitDao

    companion object {

        private const val HABIT_DATABASE_NAME = "habit.db"

        fun create(context: Context): HabitDatabase {
            return Room.databaseBuilder(
                context,
                HabitDatabase::class.java,
                HABIT_DATABASE_NAME
            ).build()
        }
    }
}