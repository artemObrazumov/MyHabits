package com.artem_obrazumov.habits.features.auth.domain.data_source

import com.artem_obrazumov.habits.features.auth.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UsersLocalDataSource {

    suspend fun observeLocalUser(): Flow<User?>
    suspend fun observeUserById(id: Long): Flow<User?>
    suspend fun upsertUser(user: User): Long
}