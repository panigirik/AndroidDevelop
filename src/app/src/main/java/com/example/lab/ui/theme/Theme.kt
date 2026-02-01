package com.example.lab.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// --- Контрастные цвета ---
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF6750A4),          // яркий фиолетовый
    secondary = Color(0xFF3700B3),        // тёмный фиолетовый для контраста
    tertiary = Color(0xFFFF4081),         // яркий розовый для акцентов
    background = Color(0xFF121212),       // очень тёмный фон
    surface = Color(0xFF1E1E1E),          // темная поверхность
    onPrimary = Color.White,              // текст на primary
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6750A4),          // основной фиолетовый
    secondary = Color(0xFF3700B3),
    tertiary = Color(0xFFFF4081),
    background = Color(0xFFFFFFFF),       // белый фон
    surface = Color(0xFFF2F2F2),          // светлая поверхность
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun LabTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
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
        content = content
    )
}

@Composable
fun CalculatorTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    LabTheme(darkTheme = darkTheme) {
        content()
    }
}
