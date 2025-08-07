package com.example.mylsp.screen.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mylsp.util.AppFont

@Composable
fun WaitingApprovalScreen(modifier: Modifier, navController: NavController) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Pendaftaran Berhasil!",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Data kamu sedang menunggu persetujuan dari admin.\nMohon tunggu beberapa saat.",
                fontFamily = AppFont.Poppins,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(30.dp))
            Button(
                onClick = { navController.navigate("main") },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Kembali ke Beranda", fontFamily = AppFont.Poppins)
            }
        }
    }
}
