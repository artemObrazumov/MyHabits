package com.artem_obrazumov.habits.features.habits.domain.model

import java.time.LocalDateTime

data class Progress(
    val progress: Double,
    val createdAt: LocalDateTime,
    val content: String,
    val attachments: List<Attachment>
)
