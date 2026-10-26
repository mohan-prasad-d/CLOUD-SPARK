package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme =
  darkColorScheme(
    primary = AccentCyan,
    onPrimary = DeepObsidian,
    secondary = SafeEmerald,
    onSecondary = DeepObsidian,
    error = AlertCrimson,
    onError = Color.White,
    background = DeepObsidian,
    onBackground = TextPrimary,
    surface = DeepObsidianSurface,
    onSurface = TextPrimary,
  )

@Composable
fun MyApplicationTheme(
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = DarkColorScheme,
    typography = Typography,
    content = content
  )
}
