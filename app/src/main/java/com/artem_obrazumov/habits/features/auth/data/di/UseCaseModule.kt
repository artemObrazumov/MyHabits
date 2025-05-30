package com.artem_obrazumov.habits.features.auth.data.di

import com.artem_obrazumov.habits.features.auth.domain.data_source.UsersLocalDataSource
import com.artem_obrazumov.habits.features.auth.domain.use_case.ObserveLocalUserUseCase
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
    fun provideObserveLocalUserUseCase(
        usersLocalDataSource: UsersLocalDataSource
    ): ObserveLocalUserUseCase = ObserveLocalUserUseCase(usersLocalDataSource)
}