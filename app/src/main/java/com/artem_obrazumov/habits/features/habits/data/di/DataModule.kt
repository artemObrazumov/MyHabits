package com.artem_obrazumov.habits.features.habits.data.di

import android.content.Context
import com.artem_obrazumov.habits.features.habits.data.local.HabitsLocalDataSourceImpl
import com.artem_obrazumov.habits.features.habits.data.local.dao.HabitDao
import com.artem_obrazumov.habits.features.habits.data.local.database.HabitDatabase
import com.artem_obrazumov.habits.features.habits.domain.data_source.HabitsLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideHabitDatabase(
        @ApplicationContext context: Context
    ): HabitDatabase {
        return HabitDatabase.create(context)
    }

    @Provides
    @Singleton
    fun provideHabitDao(
        database: HabitDatabase
    ): HabitDao {
        return database.habitDao()
    }

    @Provides
    @Singleton
    fun provideHabitsLocalDataSource(
        habitDao: HabitDao
    ): HabitsLocalDataSource {
        return HabitsLocalDataSourceImpl(habitDao)
    }
}