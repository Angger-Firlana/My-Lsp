// BarcodeScannerScreen.kt
package com.example.mylsp.ui.screen

import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CenterFocusWeak
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.example.mylsp.ui.component.form.HeaderForm
import com.example.mylsp.ui.component.LoadingScreen
import com.example.mylsp.ui.component.ScannerConfig
import com.example.mylsp.ui.component.ScannerDefaults
import com.example.mylsp.ui.component.ScannerOverlay
import com.example.mylsp.ui.component.ScannerTheme
import com.example.mylsp.common.helper.barcode.BarcodeAnalyzer
import kotlinx.coroutines.delay

@Composable
fun BarcodeScannerScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    onScanned: (String) -> Unit,
    scannerTheme: ScannerTheme = ScannerDefaults.lightTheme,
    scannerConfig: ScannerConfig = ScannerConfig()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var isLoading by remember { mutableStateOf(false) }
    var isFlashOn by remember { mutableStateOf(false) }
    var camera by remember { mutableStateOf<Camera?>(null) }
    var scanned by remember { mutableStateOf(false) }
    var getScanned by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(isLoading) {
        delay(1500) // tunggu 1.5 detik
        isLoading = false
        getScanned?.let { onScanned(it) }
    }

    Box(modifier = modifier.fillMaxSize()) {
        // Camera Preview
        CameraPreview(
            onCameraReady = { cam -> camera = cam },
            onCodeScanned = { code ->
                if (!scanned) {
                    scanned = true
                    isLoading = true
                    getScanned = code
                }
            }

        )

        // Scanner Overlay
        ScannerOverlay(
            theme = scannerTheme,
            config = scannerConfig
        )

        // Header
        ScannerHeader(
            modifier = Modifier.fillMaxWidth()
        )

        // Flash Toggle Button
        FlashToggleButton(
            isFlashOn = isFlashOn,
            onToggle = {
                isFlashOn = !isFlashOn
                camera?.cameraControl?.enableTorch(isFlashOn)
            },
            theme = scannerTheme,
            config = scannerConfig,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (scannerConfig.scanAreaSize / 2) + 50.dp)
        )

        // Instructions
        ScannerInstructions(
            theme = scannerTheme,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(scannerConfig.instructionPadding)
        )

        if (isLoading){
            LoadingScreen()
        }
    }
}

@Composable
private fun CameraPreview(
    onCameraReady: (Camera) -> Unit,
    onCodeScanned: (String) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        factory = { ctx ->
            val previewView = PreviewView(ctx)
            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = androidx.camera.core.Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                val analyzer = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also {
                        it.setAnalyzer(
                            ContextCompat.getMainExecutor(context),
                            BarcodeAnalyzer { code -> onCodeScanned(code) }
                        )
                    }

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                try {
                    cameraProvider.unbindAll()
                    val camera = cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        analyzer
                    )
                    onCameraReady(camera)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, ContextCompat.getMainExecutor(context))

            previewView
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun ScannerHeader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HeaderForm(
            title = "Scan Barcode Digital TTD",
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}

@Composable
private fun FlashToggleButton(
    isFlashOn: Boolean,
    onToggle: () -> Unit,
    theme: ScannerTheme,
    config: ScannerConfig,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onToggle,
        modifier = modifier
            .size(config.flashButtonSize)
            .background(
                if (isFlashOn) theme.flashOnColor else theme.flashOffColor,
                CircleShape
            )
    ) {
        Icon(
            imageVector = if (isFlashOn) Icons.Default.FlashOn else Icons.Default.FlashOff,
            contentDescription = if (isFlashOn) "Turn off flash" else "Turn on flash",
            tint = if (isFlashOn) theme.flashOffColor else theme.instructionTextColor
        )
    }
}

@Composable
private fun ScannerInstructions(
    theme: ScannerTheme,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                theme.instructionBackgroundColor,
                RoundedCornerShape(12.dp)
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Default.CenterFocusWeak,
            contentDescription = null,
            tint = theme.instructionTextColor,
            modifier = Modifier.size(32.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Posisikan barcode di dalam frame",
            color = theme.instructionTextColor,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Scanner akan otomatis mendeteksi",
            color = theme.instructionSecondaryTextColor,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}