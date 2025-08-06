package com.example.mylsp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF16B5C7), // Turquoise
    secondary = Color(0xFF339CFF), // Sky Blue
    tertiary = Color(0xFFFF7A00), // Orange
    background = Color(0xFFFFFFFF), // White
    surface = Color(0xFFF0F0F0), // Light gray-ish surface
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF2D2D2D), // Dark Gray
    onSurface = Color(0xFF2D2D2D) // Dark Gray
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF16B5C7), // Turquoise
    secondary = Color(0xFF339CFF), // Sky Blue
    tertiary = Color(0xFFFF7A00), // Orange
    background = Color(0xFF2D2D2D), // Dark Gray
    surface = Color(0xFF3C3C3C), // Slightly lighter dark surface
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFFFFFFFF), // White
    onSurface = Color(0xFFFFFFFF), // White

)


@Composable
fun MyLspTheme(
    darkTheme: Boolean = false,
    // Dynamic color is available on Android 12+
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
        content = content
    )
}