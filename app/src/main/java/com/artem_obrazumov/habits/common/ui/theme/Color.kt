package com.artem_obrazumov.habits.common.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val OrangeLight = Color(0xFFFF6B35)
val BackgroundLight = Color(0xFFF9FAFB)
val DarkPurple = Color(0xFF4A4E69)
val LightGray = Color(0xFF6B7280)
val LightSuccessGreen = Color(0xFF4CAF50)
val darkerGrayLight = Color(0xFFD1D5DB)
val SecondaryContainer = Color(0xFFf9FAFB)
val SecondaryContainerSelected = Color(0xFFEBEFF4)

val OrangeDark = Color(0xFFD45A2A)
val BackgroundDark = Color(0xFF121212)
val LightPurple = Color(0xFFA0A4C4)
val DarkGray = Color(0xFF9E9E9E)
val DarkSuccessGreen = Color(0xFF81C784)
val DarkContainerBackground = Color(0xFF383838)
val SecondaryContainerDark = Color(0xFFf9FAFB)
val SecondaryContainerDarkSelected = Color(0xFFEBEFF4)

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

@Composable
fun successColor(): Color {
    return if (isSystemInDarkTheme()) {
        DarkSuccessGreen
    } else {
        LightSuccessGreen
    }
}

@Composable
fun secondaryContainerColor(
    isActive: Boolean = false
): Color {
    return if (isSystemInDarkTheme()) {
        if (isActive) SecondaryContainerSelected else SecondaryContainer
    } else {
        if (isActive) SecondaryContainerDarkSelected else SecondaryContainerDark
    }
}
