package com.example.lab.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private fun darkScheme(primary: Color) = darkColorScheme(
    primary = primary,
    onPrimary = Color.Black,

    primaryContainer = Color(0xFF1E88E5),
    onPrimaryContainer = Color.White,

    secondary = Color(0xFF90CAF9),
    onSecondary = Color.Black,

    secondaryContainer = Color(0xFF1565C0),
    onSecondaryContainer = Color.White,

    tertiary = Color(0xFFFF4081),
    onTertiary = Color.Black,

    background = Color(0xFF0F1115),
    onBackground = Color(0xFFE6E6E6),

    surface = Color(0xFF1A1C22),
    onSurface = Color(0xFFE6E6E6),

    surfaceVariant = Color(0xFF2A2D36),
    onSurfaceVariant = Color(0xFFBFC4D1),

    error = Color(0xFFEF5350),
    onError = Color.Black,

    outline = Color(0xFF5C5F6A),
    inverseSurface = Color(0xFFE6E6E6),
    inverseOnSurface = Color(0xFF1A1C22)
)

private fun lightScheme(primary: Color) = lightColorScheme(
    primary = primary,
    onPrimary = Color.White,

    primaryContainer = Color(0xFFBBDEFB),
    onPrimaryContainer = Color(0xFF0D47A1),

    secondary = Color(0xFF1976D2),
    onSecondary = Color.White,

    secondaryContainer = Color(0xFFD6E4FF),
    onSecondaryContainer = Color(0xFF0B3C91),

    tertiary = Color(0xFF7C4DFF),
    onTertiary = Color.White,

    background = Color(0xFFF6F7FB),
    onBackground = Color(0xFF1E1E1E),

    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1E1E1E),

    surfaceVariant = Color(0xFFE3E7F0),
    onSurfaceVariant = Color(0xFF44474F),

    error = Color(0xFFD32F2F),
    onError = Color.White,

    outline = Color(0xFF7A7E87),
    inverseSurface = Color(0xFF1E1E1E),
    inverseOnSurface = Color.White
)

@Composable
fun CalculatorTheme(
    darkTheme: Boolean,
    primaryColor: Color,
    content: @Composable () -> Unit
) {
    val scheme =
        if (darkTheme) darkScheme(primaryColor)
        else lightScheme(primaryColor)

    MaterialTheme(
        colorScheme = scheme,
        typography = Typography,
        content = content
    )
}
