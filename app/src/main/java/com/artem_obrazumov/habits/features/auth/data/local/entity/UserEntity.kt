package com.artem_obrazumov.habits.features.auth.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.artem_obrazumov.habits.features.auth.data.local.entity.UserEntity.Companion.USERS_TABLE
import com.artem_obrazumov.habits.features.auth.domain.model.User
import java.time.LocalDateTime

@Entity(tableName = USERS_TABLE)
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = ID)
    val id: Long,
    @ColumnInfo(name = NAME)
    val name: String,
    @ColumnInfo(name = AVATAR_URL)
    val avatarUrl: String?,
    @ColumnInfo(name = UPDATED_AT)
    val updatedAt: LocalDateTime
) {
    companion object {

        const val USERS_TABLE = "users"
        const val ID = "id"
        const val NAME = "name"
        const val AVATAR_URL = "avatar_url"
        const val UPDATED_AT = "updated_at"
    }
}

fun UserEntity.toUser(): User {
    return User(
        id = id,
        name = name,
        avatarUrl = avatarUrl
    )
}

fun User.toUserEntity(): UserEntity {
    return UserEntity(
        id = id,
        name = name,
        avatarUrl = avatarUrl,
        updatedAt = LocalDateTime.now()
    )
}
