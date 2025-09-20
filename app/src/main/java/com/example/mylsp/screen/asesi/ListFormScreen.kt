package com.example.mylsp.screen.asesi

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylsp.model.api.asesi.AssesmentAsesi
import com.example.mylsp.navigation.Screen
import com.example.mylsp.util.AppFont
import com.example.mylsp.util.user.AsesiManager


data class ApprovalItem(
    val code: String,
    val title: String,
    val nis: String,
    val approved: Boolean?, // true=Approved, false=Unapproved aktif, null=belum
    val route: String
)

@Composable
fun ListFormScreen(
    asesiManager: AsesiManager,
    modifier: Modifier = Modifier,
    navigateToForm: (String) -> Unit
) {

    val asesi = asesiManager.getAsesi(asesiManager.getId())
    // Gradient full-bleed
    val bgGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFF9B55), // orange atas
            Color(0xFFFFD1A3), // transisi
            Color(0xFFFFFFFF)  // putih bawah
        )
    )

    val items = listOf(
        ApprovalItem("FR.APL.02", "ASESMEN MANDIRI", "NIS: 8880", true, Screen.Apl02.createRoute(1)),
        ApprovalItem("FR.AK.01", "PERSETUJUAN ASESMEN DAN KERAHASIAAN", "NIS: 8880", null, Screen.Ak01.createRoute("assesor")),
        ApprovalItem("FR.AK.04", "BANDING ASESMEN", "NIS: 8880", true, Screen.Ak04.route),
        ApprovalItem("FR.IA.01.CL", "CEKLIST OBSERVASI AKTIVITAS DI TEMPAT KERJA/SIMULASI", "NIS: 8880", null, Screen.Ia01.createRoute(1)),
        ApprovalItem("FR.IA.02", "TPO TUGAS PRAKTIK DEMONSTRASI", "NIS: 8880", false, Screen.Ia02.route),
        ApprovalItem("FR.IA.03", "PERTANYAAN UNTUK MENDUKUNG OBSERVASI", "NIS: 8880", null, Screen.Ia03.route),
        ApprovalItem("FR.IA.06A.DPT", "DAFTAR PERTANYAAN TERTULIS (ESAI)", "NIS: 8880", null, Screen.Ia06a.route),
        ApprovalItem("FR.IA.06.C", "LEMBAR JAWABAN PERTANYAAN TERTULIS (ESAI)", "NIS: 8880", null, Screen.Ia06c.route),
        ApprovalItem("FR.AK.02", "REKAMAN ASESMEN KOMPETENSI", "NIS: 8880", null, Screen.Ak02.route),
        ApprovalItem("FR.AK.03", "UMPAN BALIK DAN CATATAN ASESMEN", "NIS: 8880", null, Screen.Ak03.route),
        ApprovalItem("FR.AK.05", "LAPORAN ASESMEN", "NIS: 8880", null, Screen.Ak05.route),
    )

    // Penting: background dulu, baru .then(modifier) agar padding dari parent
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
            .statusBarsPadding()
            .then(modifier)
    ) {
        // Header (ikut scroll supaya aman kalau konten panjang)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = asesi?.nama_lengkap?: "Asesi",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = AppFont.Poppins,
                color = Color.White
            )
            Spacer(Modifier.height(12.dp))
            Divider(
                modifier = Modifier.padding(horizontal = 24.dp),
                color = Color.White.copy(alpha = 0.30f),
                thickness = 1.dp
            )
        }

        // List scrollable
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items) { item ->
                ApprovalCard(item, navigateToForm ={navigateToForm(item.route)})
            }
            item {
                Button(
                    onClick = {
                        navigateToForm(
                            Screen.AssessmentList.route
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),

                ) {
                    Text(
                        "Batal Melakukan Uji Sertifikasi Kompetensi?"
                    )
                }
            }
        }
    }
}

// -----------------------------------------
// CARD & PILL
// -----------------------------------------
@Composable
private fun ApprovalCard(item: ApprovalItem, navigateToForm: () -> Unit) {
    val shape = RoundedCornerShape(12.dp)

    val approvedActive = Color(0xFF4CAF50)
    val unapprovedActive = Color(0xFFF44336)
    val pillInactive = Color(0xFF757575)

    Surface(
        onClick = {
            navigateToForm()
        },
        modifier = Modifier
            .fillMaxWidth()
            .shadow(3.dp, shape)
            .clip(shape),
        color = Color.White,
        shape = shape
    ) {
        Row(
            modifier = Modifier
                // kanan sedikit lebih lega supaya visual terlihat balance
                .padding(start = 16.dp, end = 24.dp, top = 14.dp, bottom = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.code,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = AppFont.Poppins,
                    color = Color(0xFF111111)
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = item.title,
                    fontSize = 11.sp,
                    fontFamily = AppFont.Poppins,
                    color = Color(0xFF6D6D6D),
                    lineHeight = 14.sp
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = item.nis,
                    fontSize = 10.sp,
                    fontFamily = AppFont.Poppins,
                    color = Color(0xFF9E9E9E)
                )
            }

            Spacer(Modifier.width(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                StatusPill(
                    text = "APPROVED",
                    background = if (item.approved == true) approvedActive else pillInactive
                )
                StatusPill(
                    text = "UNAPPROVED",
                    background = if (item.approved == false) unapprovedActive else pillInactive
                )
            }
        }
    }
}

@Composable
private fun StatusPill(text: String, background: Color) {
    Surface(
        color = background,
        shape = RoundedCornerShape(50),
        contentColor = Color.White
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            fontSize = 9.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = AppFont.Poppins,
            letterSpacing = 0.5.sp
        )
    }
}
