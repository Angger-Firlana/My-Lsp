package com.example.mylsp.ui.screen.asesi.ak

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mylsp.ui.component.HeaderForm
import com.example.mylsp.ui.component.SkemaSertifikasi
import com.example.mylsp.data.api.assesment.KomponenData
import com.example.mylsp.data.model.api.assesment.KomponenGetReq
import com.example.mylsp.data.model.api.assesment.PostAK03Request
import com.example.mylsp.util.AppFont
import com.example.mylsp.data.local.assesment.AK03SubmissionManager
import com.example.mylsp.data.local.assesment.AssesmentAsesiManager
import com.example.mylsp.data.local.user.UserManager
import com.example.mylsp.viewmodel.assesment.ak.AK03ViewModel
import com.example.mylsp.viewmodel.assesment.KomponenViewModel

@Composable
fun FRAK03(
    modifier: Modifier = Modifier,
    aK03ViewModel: AK03ViewModel,
    komponenViewModel: KomponenViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val userManager = UserManager(context)
    val assesmentAsesiManager = AssesmentAsesiManager(context)
    val assesmentAsesi = assesmentAsesiManager.getAssesmentAsesi()

    val listKomponen by komponenViewModel.listKomponen.collectAsState()
    val aK03SubmissionManager = AK03SubmissionManager(context)
    val state by aK03ViewModel.state.collectAsState()
    val message by aK03ViewModel.message.collectAsState()
    val ak03Submissions by aK03ViewModel.submissions.collectAsState()

    // TAMBAHAN: Cek apakah user adalah asesor
    val isAsesor = userManager.getUserRole() == "asesor" || userManager.getUserRole() == "assesor"

    // UBAH: Jadikan state yang reactive
    var ak03SubmissionData by remember { mutableStateOf<List<com.example.mylsp.data.model.api.assesment.KomponenGetReq>?>(null) }
    var showDialogSuccess by remember { mutableStateOf(false) }
    var catatanTambahan by remember { mutableStateOf("") }
    var isFormSubmitted by remember { mutableStateOf(false) }

    // Check if AK03 already submitted
    LaunchedEffect(Unit) {
        komponenViewModel.getListKomponen()
        aK03ViewModel.getAK03ByAsesi(assesmentAsesi?.id?:0)

        // Tetap load data untuk asesor agar bisa melihat hasil
        // Tidak ada logic khusus yang menghalangi pemanggilan API
    }

    // PERBAIKAN: Load data yang sudah ada - UBAH jadi reactive ke ak03Submissions
    LaunchedEffect(ak03Submissions) {
        Log.d("FRAK03Debug", "ak03Submissions changed: ${ak03Submissions?.size ?: 0}")

        ak03Submissions?.let { submissions ->
            Log.d("FRAK03Debug", "Submissions received: ${submissions.size}")

            if (submissions.isNotEmpty()) {
                val data = submissions.firstOrNull {
                    it.assesment_asesi_id == (assesmentAsesi?.id ?: 0)
                }

                data?.let { submissionData ->
                    Log.d("FRAK03Debug", "Found matching submission: ${submissionData.id}")
                    ak03SubmissionData = submissionData.details
                    isFormSubmitted = true

                    // Load catatan tambahan
                    catatanTambahan = submissionData.catatan_tambahan ?: ""

                    Log.d("FRAK03Debug", "Loaded catatan: $catatanTambahan")
                    Log.d("FRAK03Debug", "Details count: ${submissionData.details?.size ?: 0}")
                }
            } else {
                Log.d("FRAK03Debug", "No submissions found, resetting form")
                isFormSubmitted = false
                ak03SubmissionData = null
                catatanTambahan = ""
            }
        }
    }

    LaunchedEffect(state) {
        state?.let { success ->
            if (success){
                showDialogSuccess = true
                navController.popBackStack()
            }else{
                Toast.makeText(
                    context,
                    message,
                    Toast.LENGTH_SHORT
                ).show()
                showDialogSuccess = false
            }
            aK03ViewModel.resetState()
        }
    }

    Box(modifier = modifier){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            HeaderForm(
                "FR.AK.03",
                "PERSETUJUAN ASESMEN DAN KERAHASIAAN"
            )

            SkemaSertifikasi()

            Text(
                "Umpan balik dari asesi (diisi oleh Asesi setelah pengambilan keputusan)",
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // TAMBAHAN: Tampilkan peringatan untuk asesor
            if (isAsesor) {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(Color(0xFFFFF3CD)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Anda hanya dapat melihat form ini.",
                            color = Color(0xFF856404),
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            // Komponen dengan parameter yang reactive
            Komponen(
                assesmentAsesiId = assesmentAsesiManager.getAssesmentId(),
                komponenList = listKomponen,
                navController = navController,
                aK03SubmissionManager = aK03SubmissionManager,
                isFormSubmitted = isFormSubmitted || isAsesor, // UBAH: Disable jika asesor
                ak03Submissions = ak03SubmissionData // PERBAIKAN: Langsung pake state yang reactive
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    CatatanField(
                        value = catatanTambahan,
                        height = 200.dp,
                        onValueChange = { catatanTambahan = it },
                        enabled = !isFormSubmitted && !isAsesor // UBAH: Disable jika asesor
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // UBAH: Sembunyikan tombol kirim untuk asesor
            if (!isAsesor) {
                KirimButtonAK03(
                    enabled = !isFormSubmitted,
                    onClick = {
                        // Validate form before submission
                        val allSubmissions = aK03SubmissionManager.getAllSubmissionAK03(
                            assesment_asesi_id = assesmentAsesiManager.getAssesmentId()
                        )

                        val missingKomponen = listKomponen.filter { komponen ->
                            val submission = allSubmissions.find { it.komponen_id == komponen.id }
                            submission?.hasil.isNullOrEmpty()
                        }

                        if (missingKomponen.isNotEmpty()) {
                            Toast.makeText(
                                context,
                                "Harap lengkapi semua komponen sebelum mengirim. ${missingKomponen.size} komponen belum dipilih.",
                                Toast.LENGTH_LONG
                            ).show()
                            return@KirimButtonAK03
                        }

                        // All validations passed, send the form
                        aK03ViewModel.sendSubmissionAK03(
                            com.example.mylsp.data.model.api.assesment.PostAK03Request(
                                assesment_asesi_id = assesmentAsesiManager.getAssesmentId(),
                                catatan_tambahan = catatanTambahan,
                                komponen = allSubmissions
                            )
                        )
                        Log.d("AK03Submission", allSubmissions.toString())
                    }
                )
            }
        }
    }
}

@Composable
fun Komponen(
    modifier: Modifier = Modifier,
    assesmentAsesiId: Int,
    komponenList: List<com.example.mylsp.data.api.assesment.KomponenData>,
    aK03SubmissionManager: AK03SubmissionManager,
    navController: NavController,
    isFormSubmitted: Boolean = false,
    ak03Submissions: List<com.example.mylsp.data.model.api.assesment.KomponenGetReq>? = null
) {

    val checkboxStates = remember { mutableStateMapOf<Int, String?>() }
    val catatanStates = remember { mutableStateMapOf<Int, String?>()}

    // Load existing submissions
    Log.d("KomponenDebug", "Loaded from server - ${ak03Submissions}")

    LaunchedEffect(komponenList,ak03Submissions) {

            ak03Submissions?.let {
                komponenList.forEach { komponen ->
                    val existingSubmission = ak03Submissions.find { it.komponen_id == komponen.id }
                    if (existingSubmission != null) {
                        catatanStates[komponen.id] = existingSubmission.catatan_asesi
                        checkboxStates[komponen.id] = existingSubmission.hasil
                        Log.d("KomponenDebug", "Loaded from server - komponen ${komponen.id}: hasil=${existingSubmission.hasil}, catatan=${existingSubmission.catatan_asesi}")
                    }
                }
            }?:run {
                val existingSubmissions = aK03SubmissionManager.getAllSubmissionAK03(assesmentAsesiId)
                komponenList.forEach { komponen ->
                    val existingSubmission = existingSubmissions.find { it.komponen_id == komponen.id }
                    if (existingSubmission != null) {
                        catatanStates[komponen.id] = existingSubmission.catatan_asesi
                        checkboxStates[komponen.id] = existingSubmission.hasil
                        Log.d("KomponenDebug", "Loaded from local - komponen ${komponen.id}: hasil=${existingSubmission.hasil}, catatan=${existingSubmission.catatan_asesi}")
                    }
                }
            }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        KomponenList(
            items = komponenList,
            checkboxStates = checkboxStates,
            catatanStates = catatanStates,
            assesmentAsesiId = assesmentAsesiId,
            aK03SubmissionManager = aK03SubmissionManager,
            isFormSubmitted = isFormSubmitted
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun KomponenList(
    items: List<com.example.mylsp.data.api.assesment.KomponenData>,
    checkboxStates: MutableMap<Int, String?>,
    catatanStates: MutableMap<Int, String?>,
    assesmentAsesiId: Int,
    aK03SubmissionManager: AK03SubmissionManager,
    isFormSubmitted: Boolean = false
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Komponen",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = AppFont.Poppins,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Box(
                    modifier = Modifier
                        .background(
                            color = if (isFormSubmitted) {
                                Color(0xFFA5D6A7)
                            } else {
                                Color(0xFFE57373)
                            },
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (isFormSubmitted) {
                            "✓ Sudah dikirim"
                        } else {
                            "× Belum dikirim"
                        },
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = AppFont.Poppins
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider()

            items.forEachIndexed { index, item ->
                KomponenItem(
                    komponen = item,
                    checkboxState = checkboxStates[item.id],
                    enabled = !isFormSubmitted,
                    onCheckboxChange = { hasil ->
                        if (!isFormSubmitted) {
                            checkboxStates[item.id] = hasil
                            aK03SubmissionManager.saveAK03Submission(
                                assesment_asesi_id = assesmentAsesiId,
                                komponen = item,
                                hasil = hasil,
                                catatanAsesi = catatanStates[item.id] ?: ""
                            )
                        }
                    }
                )

                CatatanField(
                    value = catatanStates[item.id] ?: "",
                    enabled = !isFormSubmitted,
                    onValueChange = {
                        if (!isFormSubmitted) {
                            catatanStates[item.id] = it
                            aK03SubmissionManager.saveAK03Submission(
                                assesment_asesi_id = assesmentAsesiId,
                                komponen = item,
                                hasil = checkboxStates[item.id] ?: "",
                                catatanAsesi = it
                            )
                        }
                    }
                )

                if (index < items.size - 1) {
                    HorizontalDivider(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun KomponenItem(
    komponen: com.example.mylsp.data.api.assesment.KomponenData,
    checkboxState: String?,
    enabled: Boolean = true,
    onCheckboxChange: (String) -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Text(
                text = "•",
                fontSize = 14.sp,
                fontFamily = AppFont.Poppins,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = komponen.komponen,
                fontSize = 12.sp,
                fontFamily = AppFont.Poppins,
                lineHeight = 16.sp,
                color = if (enabled) Color.Unspecified else Color.Gray
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            CheckboxHasilOption(
                label = "Ya",
                checked = checkboxState == "ya",
                enabled = enabled,
                onCheckedChange = { isChecked ->
                    if (isChecked && enabled) {
                        onCheckboxChange("ya")
                    }
                }
            )

            Spacer(modifier = Modifier.width(24.dp))

            CheckboxHasilOption(
                label = "Tidak",
                checked = checkboxState == "tidak",
                enabled = enabled,
                onCheckedChange = { isChecked ->
                    if (isChecked && enabled) {
                        onCheckboxChange("Tidak")
                    }
                }
            )
        }
    }
}

@Composable
fun CheckboxHasilOption(
    label: String,
    checked: Boolean,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = checked,
            onCheckedChange = if (enabled) onCheckedChange else { _ -> },
            enabled = enabled,
            modifier = Modifier.size(20.dp),
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                uncheckedColor = Color.Gray,
                disabledCheckedColor = Color.Gray,
                disabledUncheckedColor = Color.LightGray
            )
        )
        Text(
            text = label,
            fontSize = 12.sp,
            fontFamily = AppFont.Poppins,
            modifier = Modifier.padding(start = 4.dp),
            color = if (enabled) Color.Unspecified else Color.Gray
        )
    }
}

@Composable
fun CatatanField(
    value: String,
    height: Dp = 100.dp,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit
) {
    Text(
        text = "Catatan/ komentar lainnya (apabila ada)",
        fontFamily = AppFont.Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        modifier = Modifier.fillMaxWidth(),
        color = if (enabled) Color.Unspecified else Color.Gray
    )

    OutlinedTextField(
        value = value,
        onValueChange = if (enabled) onValueChange else { _ -> },
        enabled = enabled,
        placeholder = {
            Text(
                if (enabled) "Ketikkan komentar anda di sini." else "Form sudah dikirim",
                fontSize = 14.sp,
                fontFamily = AppFont.Poppins,
                color = if (enabled) Color.Gray else Color.LightGray
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(height),
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
fun KirimButtonAK03(
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        Button(
            onClick = if (enabled) onClick else { {} },
            enabled = enabled,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (enabled) MaterialTheme.colorScheme.tertiary else Color.Gray,
                disabledContainerColor = Color.LightGray
            )
        ) {
            Text(
                if (enabled) "Kirim Jawaban" else "Sudah Dikirim",
                fontSize = 14.sp,
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Bold
            )
        }
    }
}