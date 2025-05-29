package com.artem_obrazumov.habits.features.habits.presentation.di

import com.artem_obrazumov.habits.features.habits.presentation.habits_editor.HabitsEditorFormValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ValidatorModule {

    @Provides
    fun provideHabitsEditorFormValidator(): HabitsEditorFormValidator = HabitsEditorFormValidator()
}