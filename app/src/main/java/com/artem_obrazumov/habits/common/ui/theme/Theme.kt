package com.artem_obrazumov.habits.common.ui.theme

import android.os.Build
import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

private val DarkColorScheme = darkColorScheme(
    primary = OrangeDark,
    background = BackgroundDark,
    surfaceContainer = DarkContainerBackground,
)

private val LightColorScheme = lightColorScheme(
    primary = OrangeLight,
    background = BackgroundLight,
    surface = BackgroundLight,
    surfaceContainer = Color.White,
    surfaceTint = darkShadow
)

private val Shapes = Shapes(
    small = RoundedCornerShape(8.dp)
)

@Composable
fun HabitsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }


    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides primaryContentColor(),
            LocalOverscrollFactory provides null
        ) {
            content()
        }
    }
}