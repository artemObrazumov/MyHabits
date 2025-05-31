package com.artem_obrazumov.habits.features.habits.data.di

import com.artem_obrazumov.habits.app.data.AppDatabase
import com.artem_obrazumov.habits.features.habits.data.HabitsRepositoryImpl
import com.artem_obrazumov.habits.features.habits.data.local.HabitsLocalDataSourceImpl
import com.artem_obrazumov.habits.features.habits.data.local.dao.HabitDao
import com.artem_obrazumov.habits.features.habits.domain.HabitsRepository
import com.artem_obrazumov.habits.features.habits.domain.data_source.HabitsLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideHabitDao(
        database: AppDatabase
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

    @Provides
    @Singleton
    fun provideHabitsRepository(
        habitsLocalDataSource: HabitsLocalDataSource
    ): HabitsRepository {
        return HabitsRepositoryImpl(
            habitsLocalDataSource = habitsLocalDataSource
        )
    }
}