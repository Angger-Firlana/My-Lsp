// ScannerTheme.kt
package com.example.mylsp.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ScannerTheme(
    val overlayColor: Color = Color.White,
    val frameColor: Color = Color.Black,
    val frameCornerColor: Color = Color.Black,
    val scanLineColor: Color = Color.Red,
    val flashOnColor: Color = Color.Yellow.copy(alpha = 0.8f),
    val flashOffColor: Color = Color.Black.copy(alpha = 0.5f),
    val instructionBackgroundColor: Color = Color.White,
    val instructionTextColor: Color = Color.Black,
    val instructionSecondaryTextColor: Color = Color.Black.copy(alpha = 0.8f)
)

data class ScannerConfig(
    val scanAreaSize: Dp = 250.dp,
    val frameStrokeWidth: Dp = 4.dp,
    val cornerLength: Dp = 30.dp,
    val flashButtonSize: Dp = 40.dp,
    val instructionPadding: Dp = 32.dp,
    val enableAnimation: Boolean = true,
    val enableScanLine: Boolean = true
)

object ScannerDefaults {
    val lightTheme = ScannerTheme()

    val darkTheme = ScannerTheme(
        overlayColor = Color.Black.copy(alpha = 0.6f),
        frameColor = Color.Green,
        frameCornerColor = Color.Green,
        scanLineColor = Color.Green
    )

    val blueTheme = ScannerTheme(
        overlayColor = Color.Black.copy(alpha = 0.5f),
        frameColor = Color.Blue,
        frameCornerColor = Color.Cyan,
        scanLineColor = Color.Blue
    )

    val redTheme = ScannerTheme(
        overlayColor = Color.Black.copy(alpha = 0.5f),
        frameColor = Color.Red,
        frameCornerColor = Color.Red,
        scanLineColor = Color.Red
    )
}