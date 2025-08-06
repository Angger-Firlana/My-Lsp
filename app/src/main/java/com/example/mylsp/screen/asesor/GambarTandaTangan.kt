package com.example.mylsp.screen.asesor

import android.content.Context
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mylsp.R
import com.example.mylsp.util.*

@Composable
fun SignatureScreen(context: Context, navController: NavController) {
    val paths = remember { mutableStateOf(emptyList<List<Pair<Float, Float>>>()) }
    val currentPath = remember { mutableStateOf(emptyList<Pair<Float, Float>>()) }
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

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Generate Your Digital Sign", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("Gambarkan tanda tangan anda dibawah ini", fontSize = 14.sp)

        SignatureCard(
            paths = paths,
            currentPath = currentPath,
            onClear = {
                currentPath.value = emptyList()
                paths.value = emptyList()
                showBarcode = false
                qrSignature = null
            },
            captureController = captureController
        )

        Spacer(Modifier.height(10.dp))

        ActionButtons(
            onDelete = {
                currentPath.value = emptyList()
                paths.value = emptyList()
                showBarcode = false
                qrSignature = null
            },
            onGenerate = {
                captureController.capture { bmp ->
                    bitmapToSave = bmp
                    pilihFolder.launch("signature_${System.currentTimeMillis()}.png")
                }
            }
        )

        if (showBarcode && qrSignature != null) {
            BarcodeSection(
                qrBitmap = qrSignature!!,
                onSave = { /* save logic */ },
                onNext = { navController.navigate("skemaList") }
            )
        }
    }
}

@Composable
fun SignatureCard(
    paths: MutableState<List<List<Pair<Float, Float>>>>,
    currentPath: MutableState<List<Pair<Float, Float>>>,
    onClear: () -> Unit,
    captureController: CaptureController
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 20.dp)
    ) {
        Column(Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            CaptureBox(captureController) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFFAFAFA))
                        .border(2.dp, Color(0xFFE2E8F0), RoundedCornerShape(12.dp))
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { offset ->
                                    currentPath.value = listOf(offset.x to offset.y)
                                },
                                onDrag = { change, _ ->
                                    currentPath.value = currentPath.value + (change.position.x to change.position.y)
                                },
                                onDragEnd = {
                                    paths.value = paths.value + listOf(currentPath.value)
                                    currentPath.value = emptyList()
                                }
                            )
                        }
                ) {
                    SignaturePad(Modifier.fillMaxSize(), paths.value, currentPath.value)
                    IconButton(onClick = onClear, modifier = Modifier.align(Alignment.TopEnd)) {
                        Icon(Icons.Default.Refresh, contentDescription = "Clear")
                    }
                }
            }
        }
    }
}



@Composable
fun HeaderSection() {
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
}

@Composable
fun ActionButtons(onDelete: () -> Unit, onGenerate: () -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Button(
            onClick = onDelete,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6B6B)),
            shape = RoundedCornerShape(8.dp)
        ) { Text("Delete", color = Color.White, fontWeight = FontWeight.Medium) }

        Button(
            onClick = onGenerate,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A5568)),
            shape = RoundedCornerShape(8.dp)
        ) { Text("Generate", color = Color.White, fontWeight = FontWeight.Medium) }
    }
}

@Composable
fun BarcodeSection(qrBitmap: Bitmap, onSave: () -> Unit, onNext: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Barcode berhasil dibuat!", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2d3748))
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier.size(200.dp).clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(bitmap = qrBitmap.asImageBitmap(), contentDescription = "QR Code", modifier = Modifier.fillMaxSize().padding(20.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Barcode ini dapat berdasarkan tanda tangan anda.\nJangan lupa simpan barcode anda dan gunakan dengan bijak.",
                fontSize = 12.sp,
                color = Color(0xFF718096),
                textAlign = TextAlign.Center,
                lineHeight = 16.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = onSave,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A5568)),
                shape = RoundedCornerShape(16.dp)
            ) { Text("Simpan gambar", color = Color.White, fontWeight = FontWeight.Medium) }
            Spacer(Modifier.height(20.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Button(
                    onClick = onNext,
                    modifier = Modifier.height(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BCD4)),
                    shape = RoundedCornerShape(8.dp)
                ) { Text("Selanjutnya", color = Color.White, fontWeight = FontWeight.Medium, fontSize = 16.sp) }
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
    androidx.compose.foundation.Canvas(modifier = modifier) {
        fun drawLines(path: List<Pair<Float, Float>>) {
            for (i in 0 until path.size - 1) {
                drawLine(
                    color = Color.Black,
                    start = Offset(path[i].first, path[i].second),
                    end = Offset(path[i + 1].first, path[i + 1].second),
                    strokeWidth = 4f
                )
            }
        }
        paths.forEach(::drawLines)
        drawLines(currentPath)
    }
}
