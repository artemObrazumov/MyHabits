package com.artem_obrazumov.habits.features.habits.presentation.di

import com.artem_obrazumov.habits.features.habits.domain.data_source.HabitsLocalDataSource
import com.artem_obrazumov.habits.features.habits.domain.use_case.LoadHabitOnceUseCase
import com.artem_obrazumov.habits.features.habits.domain.use_case.LoadHabitsUseCase
import com.artem_obrazumov.habits.features.habits.domain.use_case.UpsertHabitUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideLoadHabitsUseCase(
        habitsLocalDataSource: HabitsLocalDataSource
    ): LoadHabitsUseCase {
        return LoadHabitsUseCase(habitsLocalDataSource)
    }

    @Provides
    fun provideLoadHabitOnceUseCase(
        habitsLocalDataSource: HabitsLocalDataSource
    ): LoadHabitOnceUseCase {
        return LoadHabitOnceUseCase(habitsLocalDataSource)
    }

    @Provides
    fun provideUpsertHabitUseCase(
        habitsLocalDataSource: HabitsLocalDataSource
    ): UpsertHabitUseCase {
        return UpsertHabitUseCase(habitsLocalDataSource)
    }
}