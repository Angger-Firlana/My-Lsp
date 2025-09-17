package com.example.mylsp.screen.asesor

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mylsp.util.barcode.rememberCaptureController

@Composable
fun SignatureScreen(context: Context, navController: NavController) {
    val paths = remember { mutableStateOf(emptyList<List<Pair<Float, Float>>>()) }
    val currentPath = remember { mutableStateOf(emptyList<Pair<Float, Float>>()) }
    var bitmapToSave by remember { mutableStateOf<Bitmap?>(null) }
    var qrSignature by remember { mutableStateOf<Bitmap?>(null) }
    var secureId by remember { mutableStateOf("") }
    var showBarcode by remember { mutableStateOf(false) }

    val captureController = rememberCaptureController()


    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(10.dp))

        BarcodeSection(
            qrBitmap = qrSignature!!,
            onSave = { /* save logic */ },
            onNext = { navController.navigate("skemaList") }
        )

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

