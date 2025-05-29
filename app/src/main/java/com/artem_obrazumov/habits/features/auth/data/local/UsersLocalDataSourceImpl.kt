package com.artem_obrazumov.habits.features.auth.data.local

import com.artem_obrazumov.habits.features.auth.data.local.dao.UsersDao
import com.artem_obrazumov.habits.features.auth.data.local.entity.toUser
import com.artem_obrazumov.habits.features.auth.data.local.entity.toUserEntity
import com.artem_obrazumov.habits.features.auth.domain.data_source.UsersLocalDataSource
import com.artem_obrazumov.habits.features.auth.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UsersLocalDataSourceImpl(
    private val usersDao: UsersDao
): UsersLocalDataSource {

    override suspend fun observeLocalUser(): Flow<User?> {
        TODO("Local storage with local user id is not created")
    }

    override suspend fun observeUserById(id: Long): Flow<User?> {
        return usersDao.observeById(id).map { it?.toUser() }
    }

    override suspend fun upsertUser(user: User): Long {
        return usersDao.upsertUser(user.toUserEntity())
    }
}