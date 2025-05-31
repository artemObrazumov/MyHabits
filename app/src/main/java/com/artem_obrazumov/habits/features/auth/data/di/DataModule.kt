package com.artem_obrazumov.habits.features.auth.data.di

import android.content.Context
import com.artem_obrazumov.habits.app.data.AppDatabase
import com.artem_obrazumov.habits.features.auth.data.UsersRepositoryImpl
import com.artem_obrazumov.habits.features.auth.data.local.UsersLocalDataSourceImpl
import com.artem_obrazumov.habits.features.auth.data.local.dao.UsersDao
import com.artem_obrazumov.habits.features.auth.data.local.datastore.authDataStore
import com.artem_obrazumov.habits.features.auth.domain.UsersRepository
import com.artem_obrazumov.habits.features.auth.domain.data_source.UsersLocalDataSource
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
    fun provideUsersDao(
        database: AppDatabase
    ): UsersDao {
        return database.usersDao()
    }

    @Provides
    @Singleton
    fun provideUsersLocalDataSource(
        @ApplicationContext context: Context,
        usersDao: UsersDao
    ): UsersLocalDataSource {
        return UsersLocalDataSourceImpl(
            authDataStore = context.authDataStore,
            usersDao = usersDao
        )
    }

    @Provides
    @Singleton
    fun provideUsersRepository(
        usersLocalDataSource: UsersLocalDataSource
    ): UsersRepository {
        return UsersRepositoryImpl(
            usersLocalDataSource = usersLocalDataSource
        )
    }
}