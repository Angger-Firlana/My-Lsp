// ScannerOverlay.kt
package com.example.mylsp.ui.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ScannerOverlay(
    modifier: Modifier = Modifier,
    theme: ScannerTheme,
    config: ScannerConfig
) {
    val infiniteTransition = rememberInfiniteTransition(label = "scanner_animation")

    val scanLinePosition by if (config.enableAnimation && config.enableScanLine) {
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "scan_line"
        )
    } else {
        remember { mutableFloatStateOf(0f) }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val scanArea = calculateScanArea(config.scanAreaSize)

        // Draw dark overlay
        drawOverlay(scanArea, theme.overlayColor)

        // Draw corner frames
        drawCornerFrames(scanArea, theme.frameCornerColor, config)

        // Draw animated scan line
        if (config.enableScanLine) {
            drawScanLine(scanArea, scanLinePosition, theme.scanLineColor)
        }
    }
}

private fun DrawScope.calculateScanArea(scanAreaSize: Dp): ScanArea {
    val canvasWidth = size.width
    val canvasHeight = size.height
    val scanAreaPx = scanAreaSize.toPx()
    val scanAreaLeft = (canvasWidth - scanAreaPx) / 2
    val scanAreaTop = (canvasHeight - scanAreaPx) / 2

    return ScanArea(
        left = scanAreaLeft,
        top = scanAreaTop,
        size = scanAreaPx,
        canvasWidth = canvasWidth,
        canvasHeight = canvasHeight
    )
}

private fun DrawScope.drawOverlay(scanArea: ScanArea, overlayColor: Color) {
    // Top overlay
    drawRect(
        color = overlayColor,
        topLeft = Offset(0f, 0f),
        size = Size(scanArea.canvasWidth, scanArea.top)
    )

    // Bottom overlay
    drawRect(
        color = overlayColor,
        topLeft = Offset(0f, scanArea.top + scanArea.size),
        size = Size(scanArea.canvasWidth, scanArea.canvasHeight - (scanArea.top + scanArea.size))
    )

    // Left overlay
    drawRect(
        color = overlayColor,
        topLeft = Offset(0f, scanArea.top),
        size = Size(scanArea.left, scanArea.size)
    )

    // Right overlay
    drawRect(
        color = overlayColor,
        topLeft = Offset(scanArea.left + scanArea.size, scanArea.top),
        size = Size(scanArea.canvasWidth - (scanArea.left + scanArea.size), scanArea.size)
    )
}

private fun DrawScope.drawCornerFrames(
    scanArea: ScanArea,
    frameColor: Color,
    config: ScannerConfig
) {
    val strokeWidth = config.frameStrokeWidth.toPx()
    val cornerLength = config.cornerLength.toPx()

    val corners = listOf(
        // Top-left corner
        Corner(
            horizontal = Offset(scanArea.left, scanArea.top) to
                    Offset(scanArea.left + cornerLength, scanArea.top),
            vertical = Offset(scanArea.left, scanArea.top) to
                    Offset(scanArea.left, scanArea.top + cornerLength)
        ),
        // Top-right corner
        Corner(
            horizontal = Offset(scanArea.right - cornerLength, scanArea.top) to
                    Offset(scanArea.right, scanArea.top),
            vertical = Offset(scanArea.right, scanArea.top) to
                    Offset(scanArea.right, scanArea.top + cornerLength)
        ),
        // Bottom-left corner
        Corner(
            horizontal = Offset(scanArea.left, scanArea.bottom) to
                    Offset(scanArea.left + cornerLength, scanArea.bottom),
            vertical = Offset(scanArea.left, scanArea.bottom - cornerLength) to
                    Offset(scanArea.left, scanArea.bottom)
        ),
        // Bottom-right corner
        Corner(
            horizontal = Offset(scanArea.right - cornerLength, scanArea.bottom) to
                    Offset(scanArea.right, scanArea.bottom),
            vertical = Offset(scanArea.right, scanArea.bottom - cornerLength) to
                    Offset(scanArea.right, scanArea.bottom)
        )
    )

    corners.forEach { corner ->
        drawLine(
            color = frameColor,
            start = corner.horizontal.first,
            end = corner.horizontal.second,
            strokeWidth = strokeWidth
        )
        drawLine(
            color = frameColor,
            start = corner.vertical.first,
            end = corner.vertical.second,
            strokeWidth = strokeWidth
        )
    }
}

private fun DrawScope.drawScanLine(
    scanArea: ScanArea,
    position: Float,
    scanLineColor: Color
) {
    val scanLineY = scanArea.top + (scanArea.size * position)
    val margin = 10.dp.toPx()

    drawLine(
        color = scanLineColor,
        start = Offset(scanArea.left + margin, scanLineY),
        end = Offset(scanArea.right - margin, scanLineY),
        strokeWidth = 2.dp.toPx()
    )
}

// Data classes untuk mempermudah perhitungan
private data class ScanArea(
    val left: Float,
    val top: Float,
    val size: Float,
    val canvasWidth: Float,
    val canvasHeight: Float
) {
    val right: Float get() = left + size
    val bottom: Float get() = top + size
}

private data class Corner(
    val horizontal: Pair<Offset, Offset>,
    val vertical: Pair<Offset, Offset>
)