package com.artem_obrazumov.habits.common.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val OrangeLight = Color(0xFFFF6B35)
val BackgroundLight = Color(0xFFF9FAFB)
val DarkPurple = Color(0xFF4A4E69)
val LightGray = Color(0xFF6B7280)
val darkerGrayLight = Color(0xFFD1D5DB)

val OrangeDark = Color(0xFFD45A2A)
val BackgroundDark = Color(0xFF121212)
val LightPurple = Color(0xFFA0A4C4)
val DarkGray = Color(0xFF9E9E9E)

val darkShadow = Color(0x0F000000)

@Composable
fun primaryContentColor(): Color {
    return if (isSystemInDarkTheme()) {
        LightPurple
    } else {
        DarkPurple
    }
}

@Composable
fun secondaryContentColor(): Color {
    return if (isSystemInDarkTheme()) {
        DarkGray
    } else {
        LightGray
    }
}

@Composable
fun disabledOutlineColor(): Color {
    return if (isSystemInDarkTheme()) {
        DarkGray
    } else {
        LightGray
    }
}
