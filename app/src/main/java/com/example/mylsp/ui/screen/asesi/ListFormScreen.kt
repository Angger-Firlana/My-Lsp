package com.example.mylsp.ui.screen.asesi

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.mylsp.navigation.Screen
import com.example.mylsp.util.AppFont
import com.example.mylsp.data.local.user.AsesiManager
import com.example.mylsp.viewmodel.AK01ViewModel
import com.example.mylsp.viewmodel.assesment.apl.APL02ViewModel
import com.example.mylsp.viewmodel.AssesmentViewModel
import com.example.mylsp.viewmodel.assesment.ak.AK02ViewModel
import com.example.mylsp.viewmodel.assesment.ak.AK03ViewModel
import com.example.mylsp.viewmodel.assesment.ak.AK05ViewModel
import com.example.mylsp.viewmodel.assesment.ak.Ak04ViewModel
import com.example.mylsp.viewmodel.assesment.AssesmentAsesiViewModel


data class ApprovalItem(
    val code: String,
    val title: String,
    val nis: String,
    val approved: Boolean?, // true=Approved, false=Unapproved aktif, null=belum
    val route: String
)

@Composable
fun ListFormScreen(
    modifier: Modifier = Modifier,
    asesiManager: AsesiManager,
    assesmentAsesiViewModel: AssesmentAsesiViewModel,
    apl02ViewModel: APL02ViewModel,
    ak01ViewModel: AK01ViewModel,
    ak02ViewModel: AK02ViewModel,
    ak03ViewModel: AK03ViewModel,
    ak04ViewModel: Ak04ViewModel,
    ak05ViewModel: AK05ViewModel,
    assesmentViewModel: AssesmentViewModel,
    navigateToForm: (String) -> Unit
) {
    val context = LocalContext.current
    val asesi = asesiManager.getAsesi(asesiManager.getId())

    // Collect states from ViewModels
    val assesmentAsesi by assesmentAsesiViewModel.assesmentAsesi.collectAsState()
    val assesment by assesmentViewModel.assesment.collectAsState()
    val apl02Submission by apl02ViewModel.apl02Submission.collectAsState()
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

    // Initial data fetch
    LaunchedEffect(Unit) {
        Log.d("ListFormScreen", "Fetching data for asesi: ${asesiManager.getId()}")
        assesmentAsesiViewModel.getAssesmentAsesiByAsesi(asesiManager.getId())

        assesmentAsesi?.let { asesiData ->
            assesmentViewModel.getAssesmentById(asesiData.assesment_id ?: 0)
            apl02ViewModel.getSubmissionByAsesi(asesiData.assesi_id ?: 0)
            ak01ViewModel.getSubmission(asesiData.id ?: 0)
            ak02ViewModel.getSubmission(asesiData.id ?: 0)
            ak03ViewModel.getAK03ByAsesi(asesiData.id ?: 0)
            ak04ViewModel.getAk04ByAsesi(asesiData.id ?: 0)
            ak05ViewModel.getSubmission(asesiData.id ?: 0)
        }
    }

    // Auto-refresh when data changes
    LaunchedEffect(apl02Submission, ak01Submission, ak02Submission, ak03Submission, ak04Submission, ak05Submission) {
        Log.d("ListFormScreen", "Data updated - refreshing list")
    }

    // Determine approval status for each form
    val items = listOf(
        ApprovalItem(
            code = "FR.APL.02",
            title = "ASESMEN MANDIRI",
            nis = "NIS: ${asesi?.no_ktp ?: "N/A"}",
            // APPROVED jika asesor sudah approve
            approved = apl02Submission?.data?.get(0)?.ttd_assesor == "approved",
            route = Screen.Apl02.createRoute(assesment?.schema?.id ?: 0)
        ),
        ApprovalItem(
            code = "FR.AK.01",
            title = "PERSETUJUAN ASESMEN DAN KERAHASIAAN",
            nis = "NIS: ${asesi?.no_ktp ?: "N/A"}",
            // APPROVED jika asesi & asesor sudah TTD
            // UNAPPROVED jika asesi sudah TTD tapi asesor belum
            // null jika asesi belum TTD
            approved = if (ak01Submission != null &&
                ak01Submission!!.data?.isNotEmpty() == true
            ) {
                val data = ak01Submission!!.data!![0]
                when {
                    data.ttd_asesi == 1 && data.ttd_assesor == 1 -> true
                    data.ttd_asesi == 1 && data.ttd_assesor == 0 -> false
                    else -> null
                }
            } else {
                null
            },
            route = Screen.Ak01.createRoute("asesi")
        ),
        ApprovalItem(
            code = "FR.AK.02",
            title = "REKAMAN ASESMEN KOMPETENSI",
            nis = "NIS: ${asesi?.no_ktp ?: "N/A"}",
            // APPROVED jika kedua pihak sudah TTD
            // UNAPPROVED jika asesi sudah TTD tapi asesor belum
            // null jika asesi belum TTD
            approved = when {
                ak02Submission == null -> null
                ak02Submission?.ttd_asesi == "sudah" && ak02Submission?.ttd_asesor == "sudah" -> true
                ak02Submission?.ttd_asesi == "sudah" && ak02Submission?.ttd_asesor == "belum" -> false
                else -> null
            },
            route = Screen.Ak02.createRoute(assesment?.schema?.id ?: 0)
        ),
        ApprovalItem(
            code = "FR.AK.03",
            title = "UMPAN BALIK DAN CATATAN ASESMEN",
            nis = "NIS: ${asesi?.no_ktp ?: "N/A"}",
            // APPROVED jika asesor sudah isi (read-only untuk asesi)
            // null jika belum ada
            approved = if (ak03Submission != null) true else null,
            route = Screen.Ak03.route
        ),
        ApprovalItem(
            code = "FR.AK.04",
            title = "BANDING ASESMEN",
            nis = "NIS: ${asesi?.no_ktp ?: "N/A"}",
            // APPROVED jika asesi sudah submit banding
            // null jika belum (form ini optional)
            approved = if (ak04Submission != null) true else null,
            route = Screen.Ak04.route
        )
    )

    // Background with gradient
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
            .statusBarsPadding()
            .then(modifier)
    ) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = asesi?.nama_lengkap ?: "Asesi",
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
                ApprovalCard(item, navigateToForm = { navigateToForm(item.route) })
            }

            // Button untuk batal uji sertifikasi
            item {
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = {
                        navigateToForm(Screen.AssessmentList.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE53935)
                    )
                ) {
                    Text(
                        text = "Batal Melakukan Uji Sertifikasi Kompetensi?",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = AppFont.Poppins
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

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
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