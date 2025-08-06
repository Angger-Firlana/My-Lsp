package com.example.mylsp.screen.asesor

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylsp.util.CaptureBox
import com.example.mylsp.util.generateQRCode
import com.example.mylsp.util.generateSecureIdFromUri
import com.example.mylsp.util.rememberCaptureController
import com.example.mylsp.util.saveBitmapToUri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignatureScreen(context: Context) {
    var paths by remember { mutableStateOf(listOf<List<Pair<Float, Float>>>()) }
    var currentPath by remember { mutableStateOf(listOf<Pair<Float, Float>>()) }
    var bitmapToSave by remember { mutableStateOf<Bitmap?>(null) }
    var qrSignature by remember { mutableStateOf<Bitmap?>(null) }
    var secureId by remember { mutableStateOf("") }
    var showBarcode by remember { mutableStateOf(false) }

    val captureController = rememberCaptureController()
    val pilihFolder = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("image/png"),
        onResult = { uri ->
            if (uri != null && bitmapToSave != null) {
                secureId = generateSecureIdFromUri(uri.toString())
                saveBitmapToUri(context, uri, bitmapToSave!!)
                qrSignature = generateQRCode(secureId)
                showBarcode = true
            }
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF667eea),
                        Color(0xFF764ba2)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Main Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Header
                    Text(
                        text = "Generate Your Digital Sign",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2d3748),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Gambarkan tanda tangan anda dibawah ini",
                        fontSize = 14.sp,
                        color = Color(0xFF718096),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    // Signature Canvas
                    CaptureBox(captureController) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFFAFAFA))
                                .border(
                                    2.dp,
                                    Color(0xFFE2E8F0),
                                    RoundedCornerShape(12.dp)
                                )
                                .pointerInput(Unit) {
                                    detectDragGestures(
                                        onDragStart = { offset ->
                                            currentPath = listOf(offset.x to offset.y)
                                        },
                                        onDrag = { change, _ ->
                                            currentPath = currentPath + (change.position.x to change.position.y)
                                        },
                                        onDragEnd = {
                                            paths = paths + listOf(currentPath)
                                            currentPath = emptyList()
                                        }
                                    )
                                }
                        ) {
                            SignaturePad(
                                modifier = Modifier.fillMaxSize(),
                                paths = paths,
                                currentPath = currentPath
                            )

                            // Refresh icon in top right corner
                            IconButton(
                                onClick = {
                                    currentPath = emptyList()
                                    paths = emptyList()
                                    showBarcode = false
                                    qrSignature = null
                                },
                                modifier = Modifier.align(Alignment.TopEnd)
                            ) {
                                Icon(
                                    Icons.Default.Refresh,
                                    contentDescription = "Clear",
                                    tint = Color(0xFF718096)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Action Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Delete Button
                        Button(
                            onClick = {
                                currentPath = emptyList()
                                paths = emptyList()
                                showBarcode = false
                                qrSignature = null
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFF6B6B)
                            ),
                            shape = RoundedCornerShape(25.dp)
                        ) {
                            Text(
                                "Delete",
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        // Generate Button
                        Button(
                            onClick = {
                                captureController.capture { bmp ->
                                    bitmapToSave = bmp
                                    pilihFolder.launch("signature_${System.currentTimeMillis()}.png")
                                }
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4A5568)
                            ),
                            shape = RoundedCornerShape(25.dp)
                        ) {
                            Text(
                                "Generate",
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            // Barcode Section
            if (showBarcode && qrSignature != null) {
                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 20.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(30.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Barcode berhasil dibuat!",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2d3748),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // QR Code Display
                        Box(
                            modifier = Modifier
                                .size(200.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.White)
                                .border(
                                    1.dp,
                                    Color(0xFFE2E8F0),
                                    RoundedCornerShape(12.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                bitmap = qrSignature!!.asImageBitmap(),
                                contentDescription = "QR Code",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Barcode ini dapat berdasarkan tanda tangan anda.\nJangan lupa simpan barcode anda dan gunakan\ndengan bijak.",
                            fontSize = 12.sp,
                            color = Color(0xFF718096),
                            textAlign = TextAlign.Center,
                            lineHeight = 16.sp
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = { /* Handle save barcode */ },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4A5568)
                            ),
                            shape = RoundedCornerShape(25.dp)
                        ) {
                            Text(
                                "📷 Simpan gambar",
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            // Bottom Button
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { /* Handle selanjutnya */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00BCD4)
                ),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(
                    "selanjutnya",
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun SignaturePad(
    modifier: Modifier = Modifier,
    paths: List<List<Pair<Float, Float>>>,
    currentPath: List<Pair<Float, Float>>
) {
    Canvas(modifier = modifier) {
        paths.forEach { path ->
            for (i in 0 until path.size - 1) {
                drawLine(
                    color = Color.Black,
                    start = Offset(path[i].first, path[i].second),
                    end = Offset(path[i + 1].first, path[i + 1].second),
                    strokeWidth = 4f
                )
            }
        }
        for (i in 0 until currentPath.size - 1) {
            drawLine(
                color = Color.Black,
                start = Offset(currentPath[i].first, currentPath[i].second),
                end = Offset(currentPath[i + 1].first, currentPath[i + 1].second),
                strokeWidth = 4f
            )
        }
    }
}