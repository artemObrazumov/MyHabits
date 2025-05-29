package com.artem_obrazumov.habits.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.artem_obrazumov.habits.common.data.converters.DateTimeConverter
import com.artem_obrazumov.habits.common.data.converters.GoalTypeConverter
import com.artem_obrazumov.habits.common.data.converters.ProgressFrequencyConverter
import com.artem_obrazumov.habits.features.auth.data.local.dao.UsersDao
import com.artem_obrazumov.habits.features.auth.data.local.entity.UserEntity
import com.artem_obrazumov.habits.features.habits.data.local.dao.HabitDao
import com.artem_obrazumov.habits.features.habits.data.local.entity.HabitEntity
import com.artem_obrazumov.habits.features.habits.data.local.entity.HabitUsersEntity

@Database(entities = [HabitEntity::class, UserEntity::class, HabitUsersEntity::class], version = 1)
@TypeConverters(DateTimeConverter::class, GoalTypeConverter::class, ProgressFrequencyConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitDao

    abstract fun usersDao(): UsersDao

    companion object {

        private const val APP_DATABASE_NAME = "habits.db"

        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP_DATABASE_NAME
            ).build()
        }
    }
}