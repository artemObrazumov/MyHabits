package com.artem_obrazumov.habits.features.auth.domain.use_case

import com.artem_obrazumov.habits.common.domain.Result
import com.artem_obrazumov.habits.common.domain.UnknownError
import com.artem_obrazumov.habits.features.auth.domain.data_source.UsersLocalDataSource
import com.artem_obrazumov.habits.features.auth.domain.model.User
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ObserveLocalUserUseCaseTest {

    private val mockLocalDataSource: UsersLocalDataSource = mockk()
    private val observeUserUseCase = ObserveLocalUserUseCase(mockLocalDataSource)

    @Test
    fun `returns success result correctly`() = runTest {
        val user = User(
            id = 1,
            name = "test name",
            avatarUrl = null
        )
        coEvery { mockLocalDataSource.observeLocalUser() } returns flowOf(user)

        val result = observeUserUseCase()
        assert(result is Result.Success)

        assertEquals((result as Result.Success).data.first(), user)
    }

    @Test
    fun `returns success result with null correctly`() = runTest {
        val user = null
        coEvery { mockLocalDataSource.observeLocalUser() } returns flowOf(user)

        val result = observeUserUseCase()
        assert(result is Result.Success)

        assertEquals((result as Result.Success).data.first(), user)
    }

    @Test
    fun `returns failure result correctly`() = runTest {
        coEvery { mockLocalDataSource.observeLocalUser() } throws Exception("Test exception")

        val result = observeUserUseCase()
        assert(result is Result.Failure)

        assertEquals((result as Result.Failure).error, UnknownError)
    }
}