package com.artem_obrazumov.habits.features.auth.domain.use_case

import com.artem_obrazumov.habits.common.domain.Error
import com.artem_obrazumov.habits.common.domain.Result
import com.artem_obrazumov.habits.common.domain.UnknownError
import com.artem_obrazumov.habits.features.auth.domain.data_source.UsersLocalDataSource
import com.artem_obrazumov.habits.features.auth.domain.model.User
import kotlinx.coroutines.flow.Flow

/*
* Syncs user and returns a flow with user
*/
class ObserveUserByIdUseCase(
    private val usersLocalDataSource: UsersLocalDataSource
) {

    suspend operator fun invoke(id: Long): Result<Flow<User?>, Error> {
        return try {
            Result.Success(usersLocalDataSource.observeUserById(id))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(UnknownError)
        }
    }
}