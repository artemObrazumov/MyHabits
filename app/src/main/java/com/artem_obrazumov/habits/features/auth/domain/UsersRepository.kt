package com.artem_obrazumov.habits.features.auth.domain

import com.artem_obrazumov.habits.common.domain.Error
import com.artem_obrazumov.habits.common.domain.Result
import com.artem_obrazumov.habits.features.auth.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    fun observeLocalUser(): Flow<Result<User?, Error>>
    fun observeUserById(id: Long): Flow<Result<User?, Error>>
    suspend fun upsertUserLocally(user: User): Result<Long, Error>
}