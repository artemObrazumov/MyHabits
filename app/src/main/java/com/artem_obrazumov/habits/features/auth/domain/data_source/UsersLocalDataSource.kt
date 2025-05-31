package com.artem_obrazumov.habits.features.auth.domain.data_source

import com.artem_obrazumov.habits.features.auth.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UsersLocalDataSource {

    fun observeLocalUser(): Flow<User?>
    fun observeUserById(id: Long): Flow<User?>
    suspend fun upsertUserLocally(user: User): Long
}