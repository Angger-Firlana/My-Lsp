package com.example.mylsp.ui.screen.asesor.approve

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylsp.data.model.api.Apl01
import com.example.mylsp.navigation.Screen
import com.example.mylsp.util.AppFont
import com.example.mylsp.data.local.assesment.AssesmentAsesiManager
import com.example.mylsp.viewmodel.AK01ViewModel
import com.example.mylsp.viewmodel.assesment.apl.APL02ViewModel
import com.example.mylsp.viewmodel.AssesmentViewModel
import com.example.mylsp.viewmodel.assesment.ak.AK02ViewModel
import com.example.mylsp.viewmodel.assesment.ak.AK03ViewModel
import com.example.mylsp.viewmodel.assesment.ak.AK05ViewModel
import com.example.mylsp.viewmodel.assesment.ak.Ak04ViewModel
import com.example.mylsp.viewmodel.assesment.IA01ViewModel


data class ApprovalItem(
    val code: String,
    val title: String,
    val nis: String,
    val approved: Boolean?, // true=Approved, false=Unapproved aktif, null=belum
    val route: String
)

@Composable
fun ApprovedUnapprovedScreen(
    modifier: Modifier = Modifier,
    apl02ViewModel: APL02ViewModel,
    ia01ViewModel:IA01ViewModel,
    ak01ViewModel:AK01ViewModel,
    ak02ViewModel: AK02ViewModel,
    ak03ViewModel: AK03ViewModel,
    ak04ViewModel: Ak04ViewModel,
    ak05ViewModel: AK05ViewModel,
    assesmentViewModel: AssesmentViewModel,
    apl01:Apl01,
    navigateToForm: (String) -> Unit
) {
    val assesmentAsesiManager = AssesmentAsesiManager(LocalContext.current)

    val assesmentAsesi = assesmentAsesiManager.getAssesmentAsesi()
    val assesment by assesmentViewModel.assesment.collectAsState()
    val apl02Submission by apl02ViewModel.apl02Submission.collectAsState()
    val ia01Submission by ia01ViewModel.submissions.collectAsState()
    val ak01Submission by ak01ViewModel.submission.collectAsState()
    val ak02Submission by ak02ViewModel.submission.collectAsState()
    val ak03Submission by ak03ViewModel.submissions.collectAsState()
    val ak04Submission by ak04ViewModel.submissions.collectAsState()
    val ak05Submission by ak05ViewModel.submission.collectAsState()

    // Gradient full-bleed
    val bgGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFF9B55), // orange atas
            Color(0xFFFFD1A3), // transisi
            Color(0xFFFFFFFF)  // putih bawah
        )
    )

    LaunchedEffect(Unit) {
        Log.d("ApprovedUnapprovedScreen", assesment.toString())
        assesmentViewModel.getAssesmentById(assesmentAsesi?.assesment_id?: 0)
        apl02ViewModel.getSubmissionByAsesi(assesmentAsesi?.id?: 0)
        ak04ViewModel.getAk04ByAsesi(assesmentAsesi?.id?: 0)
        ak01ViewModel.getSubmission(assesmentAsesi?.id?: 0)
        ak02ViewModel.getSubmission(assesmentAsesi?.id?:0)
        ak03ViewModel.getAK03ByAsesi(assesmentAsesi?.id?:0)
        ak05ViewModel.getSubmission(assesmentAsesi?.id?:0)
        ia01ViewModel.getIA01ByAsesi(assesmentAsesi?.id?:0)
    }

    // Auto-refresh ketika ada perubahan data dari submission forms
    LaunchedEffect(apl02Submission, ak01Submission, ak02Submission, ak03Submission, ak05Submission, ia01Submission) {
        Log.d("ApprovedUnapprovedScreen", "Data updated - refreshing list")
    }

    val items = listOf(
        ApprovalItem("FR.APL.02", "ASESMEN MANDIRI", "NIS: 8880", apl02Submission?.data?.get(0)?.ttd_assesor == "approved", Screen.Apl02.createRoute(assesment?.schema?.id?: 0)),
        ApprovalItem("FR.IA.01.CL", "CEKLIST OBSERVASI AKTIVITAS DI TEMPAT KERJA/SIMULASI", "NIS: 8880", if (ia01Submission!= null) true else null, Screen.Ia01.createRoute(assesment?.schema?.id?: 0)),
        ApprovalItem(
            "FR.AK.01",
            "PERSETUJUAN ASESMEN DAN KERAHASIAAN",
            "NIS: 8880",
            if (ak01Submission != null &&
                ak01Submission!!.data?.isNotEmpty() == true &&
                ak01Submission!!.data?.get(0)?.ttd_assesor == 1 &&
                ak01Submission!!.data?.get(0)?.ttd_asesi  == 1
            ) {
                true
            } else {
                null
            },
            Screen.Ak01.createRoute("assesor")
        ),
        ApprovalItem("FR.AK.02", "REKAMAN ASESMEN KOMPETENSI", "NIS: 8880", ak02Submission?.ttd_asesi == "sudah" && ak02Submission?.ttd_asesor == "sudah", Screen.Ak02.createRoute(assesment?.schema?.id?: 0)),
        ApprovalItem("FR.AK.03", "UMPAN BALIK DAN CATATAN ASESMEN", "NIS: 8880", ak03Submission != null, Screen.Ak03.route),
        ApprovalItem("FR.AK.04", "BANDING ASESMEN", "NIS: 8880", if (ak04Submission != null) true else null, Screen.Ak04.route),
        ApprovalItem("FR.AK.05", "LAPORAN ASESMEN", "NIS: 8880", ak05Submission?.data?.get(0)?.ttdAsesor == "sudah", Screen.Ak05.route),
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
                text = apl01.nama_lengkap?:"Asesi",
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