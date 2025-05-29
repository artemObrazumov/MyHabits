package com.artem_obrazumov.habits.common.ui.util

sealed class Message {
    abstract val content: UIText

    data class Neutral(
        override val content: UIText
    ): Message()

    data class Success(
        override val content: UIText
    ): Message()

    data class Error(
        override val content: UIText
    ): Message()
}