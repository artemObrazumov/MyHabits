package com.artem_obrazumov.habits.features.auth.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.artem_obrazumov.habits.features.auth.data.local.entity.UserEntity
import com.artem_obrazumov.habits.features.auth.data.local.entity.UserEntity.Companion.ID
import com.artem_obrazumov.habits.features.auth.data.local.entity.UserEntity.Companion.USERS_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {

    @Query("Select * From $USERS_TABLE Where $ID = :id")
    fun observeById(id: Long): Flow<UserEntity?>

    @Upsert
    suspend fun upsertUser(user: UserEntity): Long
}