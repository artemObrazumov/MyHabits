package com.artem_obrazumov.habits.features.habits.presentation.di

import com.artem_obrazumov.habits.features.habits.domain.data_source.HabitsLocalDataSource
import com.artem_obrazumov.habits.features.habits.domain.use_case.LoadHabitDetailsUseCase
import com.artem_obrazumov.habits.features.habits.domain.use_case.LoadHabitOnceUseCase
import com.artem_obrazumov.habits.features.habits.domain.use_case.LoadHabitsUseCase
import com.artem_obrazumov.habits.features.habits.domain.use_case.UpsertHabitUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideLoadHabitsUseCase(
        habitsLocalDataSource: HabitsLocalDataSource
    ): LoadHabitsUseCase {
        return LoadHabitsUseCase(habitsLocalDataSource)
    }

    @Provides
    @ViewModelScoped
    fun provideLoadHabitOnceUseCase(
        habitsLocalDataSource: HabitsLocalDataSource
    ): LoadHabitOnceUseCase {
        return LoadHabitOnceUseCase(habitsLocalDataSource)
    }

    @Provides
    @ViewModelScoped
    fun provideUpsertHabitUseCase(
        habitsLocalDataSource: HabitsLocalDataSource
    ): UpsertHabitUseCase {
        return UpsertHabitUseCase(habitsLocalDataSource)
    }

    @Provides
    @ViewModelScoped
    fun provideLoadHabitDetailsUseCase(
        habitsLocalDataSource: HabitsLocalDataSource
    ): LoadHabitDetailsUseCase {
        return LoadHabitDetailsUseCase(habitsLocalDataSource)
    }
}