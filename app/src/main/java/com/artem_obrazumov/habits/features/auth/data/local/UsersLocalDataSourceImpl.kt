package com.artem_obrazumov.habits.features.auth.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.artem_obrazumov.habits.features.auth.data.local.dao.UsersDao
import com.artem_obrazumov.habits.features.auth.data.local.datastore.AuthLocalStorageConstants.LOCAL_USER_ID
import com.artem_obrazumov.habits.features.auth.data.local.entity.toUser
import com.artem_obrazumov.habits.features.auth.data.local.entity.toUserEntity
import com.artem_obrazumov.habits.features.auth.domain.data_source.UsersLocalDataSource
import com.artem_obrazumov.habits.features.auth.domain.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class UsersLocalDataSourceImpl(
    private val authDataStore: DataStore<Preferences>,
    private val usersDao: UsersDao
): UsersLocalDataSource {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun observeLocalUser(): Flow<User?> {
        return authDataStore.data.map { data -> data[LOCAL_USER_ID] }
            .distinctUntilChanged()
            .flatMapLatest { id ->
                if (id == null) {
                    flowOf(null)
                } else {
                    usersDao.observeById(id).map { it?.toUser() }
                }
            }
    }

    override suspend fun observeUserById(id: Long): Flow<User?> {
        return usersDao.observeById(id).map { it?.toUser() }
    }

    override suspend fun upsertUser(user: User): Long {
        return usersDao.upsertUser(user.toUserEntity())
    }
}