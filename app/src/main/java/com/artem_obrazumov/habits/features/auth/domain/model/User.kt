package com.artem_obrazumov.habits.features.auth.domain.model

data class User(
    val id: Long,
    val name: String,
    val avatarUrl: String?
)
