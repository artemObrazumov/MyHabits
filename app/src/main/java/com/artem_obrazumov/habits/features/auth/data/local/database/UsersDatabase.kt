package com.artem_obrazumov.habits.features.auth.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.artem_obrazumov.habits.common.data.converters.DateTimeConverter
import com.artem_obrazumov.habits.features.auth.data.local.dao.UsersDao
import com.artem_obrazumov.habits.features.auth.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
@TypeConverters(DateTimeConverter::class)
abstract class UsersDatabase: RoomDatabase() {

    abstract fun usersDao(): UsersDao

    companion object {

        private const val USERS_DATABASE_NAME = "users.db"

        fun create(context: Context): UsersDatabase {
            return Room.databaseBuilder(
                context,
                UsersDatabase::class.java,
                USERS_DATABASE_NAME
            ).build()
        }
    }
}