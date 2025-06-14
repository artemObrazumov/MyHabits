package com.artem_obrazumov.habits.features.auth.data.di

import android.content.Context
import com.artem_obrazumov.habits.features.auth.data.local.UsersLocalDataSourceImpl
import com.artem_obrazumov.habits.features.auth.data.local.dao.UsersDao
import com.artem_obrazumov.habits.features.auth.data.local.database.UsersDatabase
import com.artem_obrazumov.habits.features.auth.data.local.datastore.authDataStore
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
    fun provideUsersDatabase(
        @ApplicationContext context: Context
    ): UsersDatabase {
        return UsersDatabase.create(context)
    }

    @Provides
    @Singleton
    fun provideUsersDao(
        database: UsersDatabase
    ): UsersDao {
        return database.usersDao()
    }

    @Provides
    @Singleton
    fun provideUsersLocalDataSource(
        context: Context,
        usersDao: UsersDao
    ): UsersLocalDataSource {
        return UsersLocalDataSourceImpl(
            authDataStore = context.authDataStore,
            usersDao = usersDao
        )
    }
}