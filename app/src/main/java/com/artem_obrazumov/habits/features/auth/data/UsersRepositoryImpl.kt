package com.artem_obrazumov.habits.features.auth.data

import com.artem_obrazumov.habits.common.domain.Error
import com.artem_obrazumov.habits.common.domain.Result
import com.artem_obrazumov.habits.common.domain.UnknownError
import com.artem_obrazumov.habits.features.auth.domain.UsersRepository
import com.artem_obrazumov.habits.features.auth.domain.data_source.UsersLocalDataSource
import com.artem_obrazumov.habits.features.auth.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class UsersRepositoryImpl(
    private val usersLocalDataSource: UsersLocalDataSource
) : UsersRepository {

    override fun observeLocalUser(): Flow<Result<User?, Error>> {
        return usersLocalDataSource.observeLocalUser()
            .map {
                Result.Success(it)
            }.catch { e ->
                Result.Failure(
                    when (e) {
                        else -> UnknownError
                    }
                )
            }
    }

    override fun observeUserById(id: Long): Flow<Result<User?, Error>> {
        return usersLocalDataSource.observeUserById(id)
            .map {
                Result.Success(it)
            }.catch { e ->
                Result.Failure(
                    when (e) {
                        else -> UnknownError
                    }
                )
            }
    }

    override suspend fun upsertUserLocally(user: User): Result<Long, Error> {
        return try {
            Result.Success(usersLocalDataSource.upsertUserLocally(user))
        } catch (e: Exception) {
            Result.Failure(
                when (e) {
                    else -> UnknownError
                }
            )
        }
    }
}