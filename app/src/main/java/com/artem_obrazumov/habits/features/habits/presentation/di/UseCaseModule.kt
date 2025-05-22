package com.artem_obrazumov.habits.features.habits.presentation.di

import com.artem_obrazumov.habits.features.habits.domain.data_source.HabitsLocalDataSource
import com.artem_obrazumov.habits.features.habits.domain.use_case.LoadHabitsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

//    @Provides
//    fun provideLoadHabitsUseCase(
//        habitsLocalDataSource: HabitsLocalDataSource
//    ): LoadHabitsUseCase {
//        return LoadHabitsUseCase(habitsLocalDataSource)
//    }
}