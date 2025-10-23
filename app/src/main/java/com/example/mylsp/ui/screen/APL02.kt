package com.example.mylsp.ui.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.compose.runtime.DisposableEffect
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.mylsp.common.enums.TypeAlert
import com.example.mylsp.data.api.assesment.DataApl02
import com.example.mylsp.data.api.assesment.ElemenAPL02
import com.example.mylsp.data.api.assesment.GetAPL02Response
import com.example.mylsp.data.api.assesment.PostApproveRequest
import com.example.mylsp.data.api.assesment.UnitApl02
import com.example.mylsp.data.api.assesment.UnitGetResponse
import com.example.mylsp.ui.component.form.HeaderForm
import com.example.mylsp.ui.component.LoadingScreen
import com.example.mylsp.ui.component.form.SkemaSertifikasi
import com.example.mylsp.data.model.api.Attachment
import com.example.mylsp.util.AppFont
import com.example.mylsp.data.local.assesment.AssesmentAsesiManager
import com.example.mylsp.data.local.assesment.JawabanManager
import com.example.mylsp.data.local.user.AsesiManager
import com.example.mylsp.data.local.user.UserManager
import com.example.mylsp.ui.component.alert.AlertCard
import com.example.mylsp.viewmodel.assesment.apl.APL01ViewModel
import com.example.mylsp.viewmodel.assesment.apl.APL02ViewModel
import com.example.mylsp.viewmodel.assesment.AssesmentAsesiViewModel

@Composable
fun APL02(
    modifier: Modifier = Modifier,
    id: Int,
    assesmentAsesiViewModel: AssesmentAsesiViewModel,
    apl01ViewModel: APL01ViewModel,
    userManager: UserManager,
    apL02ViewModel: APL02ViewModel,
    nextForm: () -> Unit,
    ajuBanding: ()-> Unit
) {
    val context = LocalContext.current
    val asesiManager = AsesiManager(context)
    val assesmentAsesiManager = AssesmentAsesiManager(context)
    val jawabanManager = remember { JawabanManager() }

    // Collect states
    val apl02 by apL02ViewModel.apl02.collectAsState()
    val apl02Submissions by apL02ViewModel.apl02Submission.collectAsState()
    val message = ""
    val state by apL02ViewModel.state.collectAsState()
    val apl01Data by apl01ViewModel.formData.collectAsState()
    val isLoadingSubmission by apL02ViewModel.isLoadingSubmission.collectAsState()

    var isReadOnly by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var cachedSubmission by remember { mutableStateOf<GetAPL02Response?>(null) }

    // OPSI 1: Detect screen lifecycle - reload when screen is focused
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    Log.d("APL02_MAIN", "Screen resumed - reloading data")
                    apL02ViewModel.getAPL02(id)
                    apL02ViewModel.getSubmissionByAsesi(asesiManager.getId())
                }
                else -> {}
            }
        }
        lifecycle.addObserver(observer)

        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(Unit) {
        if(userManager.getUserRole() == "asesi" || userManager.getUserRole() == "assesi") {
            assesmentAsesiViewModel.updateStatusAssesmentAsesi(
                assesmentAsesiManager.getAssesmentId(),
                "APL-02"
            )
        }
    }

    // Monitor loading state
    LaunchedEffect(isLoadingSubmission) {
        if (isLoadingSubmission) {
            Log.d("APL02_MAIN", "Loading submission data...")
        } else {
            Log.d("APL02_MAIN", "Submission loading finished. Data: ${apl02Submissions?.data?.size ?: 0} units")
        }
    }

    // Debug submission data
    LaunchedEffect(apl02Submissions) {
        apl02Submissions?.let { submission ->
            Log.d("APL02_MAIN", "=== APL02 SUBMISSION DATA ===")
            Log.d("APL02_MAIN", "Status: ${submission.status}")
            Log.d("APL02_MAIN", "Message: ${submission.message}")
            Log.d("APL02_MAIN", "Data Count: ${submission.data.size}")

            submission.data.forEachIndexed { unitIndex, unitResponse ->
                Log.d("APL02_MAIN", "Unit Response $unitIndex:")
                Log.d("APL02_MAIN", "  ID: ${unitResponse.id}")
                Log.d("APL02_MAIN", "  Submission Date: ${unitResponse.submission_date}")
                Log.d("APL02_MAIN", "  Details Count: ${unitResponse.details.size}")

                unitResponse.details.forEachIndexed { detailIndex, detail ->
                    Log.d("APL02_MAIN", "  Detail $detailIndex:")
                    Log.d("APL02_MAIN", "    Unit Ke: ${detail.unit_ke}")
                    Log.d("APL02_MAIN", "    Kode Unit: ${detail.kode_unit}")
                    Log.d("APL02_MAIN", "    Elemen ID: ${detail.elemen_id}")
                    Log.d("APL02_MAIN", "    Kompetensinitas: ${detail.kompetensinitas}")
                    Log.d("APL02_MAIN", "    Attachments Count: ${detail.attachments.size}")

                    detail.attachments.forEachIndexed { attIndex, attachment ->
                        Log.d("APL02_MAIN", "      Attachment $attIndex: ${attachment.bukti.nama_dokumen}")
                    }
                }
            }

            val isAssesi = userManager.getUserRole() == "assesi"
            val hasSubmission = submission.data.isNotEmpty() &&
                    submission.data.any { it.details.isNotEmpty() }
            isReadOnly = isAssesi && hasSubmission
            cachedSubmission = submission

            Log.d("APL02_MAIN", "isReadOnly updated: $isReadOnly")
        } ?: run {
            Log.d("APL02_MAIN", "No submission data found")
            if (cachedSubmission != null) {
                Log.d("APL02_MAIN", "Using cached submission data")
                isReadOnly = true
            } else {
                isReadOnly = false
            }
        }
    }

    LaunchedEffect(state) {
        state?.let { success ->
            if (success) {
                Log.d("APL02_MAIN", "Form submitted successfully, showing dialog")
                showSuccessDialog = true
            }
        }
    }

    // Success Dialog
    if (showSuccessDialog) {
        SuccessDialog(
            onDismiss = {
                showSuccessDialog = false
                apL02ViewModel.resetState()
            },
            onNextForm = {
                showSuccessDialog = false
                apL02ViewModel.resetState()
                nextForm()
            },
            userRole = userManager.getUserRole() ?: "assesi"
        )
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        apl02?.let { data ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeaderForm(
                    title = "FR.APL.02.ASESMEN MANDIRI"
                )

                SkemaSertifikasi()

                if (message.isNotEmpty()) {
                    AlertCard(
                        message,
                        TypeAlert.Error,
                        Modifier.padding(horizontal = 16.dp)
                    )
                }

                // Show loading indicator
                if (isLoadingSubmission) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }

                // Display submission status
                (apl02Submissions ?: cachedSubmission)?.data?.firstOrNull()?.let { firstSubmission ->
                    SubmissionStatusCard(firstSubmission, userManager.getUserRole()!!)

                    if(firstSubmission.ttd_assesor == "rejected") {
                        AlertCard(
                            "APL 02 Ditolak oleh asesor, silahkan aju banding agar dapat melakukan asesmen ulang",
                            type = TypeAlert.Error,
                            Modifier.padding(horizontal = 16.dp)
                        )

                        Button(
                            onClick = {
                                ajuBanding()
                            },
                            modifier = Modifier.padding(8.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(
                                "Aju Banding",
                                fontFamily = AppFont.Poppins
                            )
                        }
                    }
                }

                InstructionCard()

                UnitsSection(
                    assesmentAsesiId = assesmentAsesiManager.getAssesmentId(),
                    data = data,
                    listAttachment = apl01Data?.attachments ?: emptyList(),
                    jawabanManager = jawabanManager,
                    submission = apl02Submissions ?: cachedSubmission,
                    isReadOnly = isReadOnly
                )

                SubmitButton(
                    apL02ViewModel = apL02ViewModel,
                    assesmentAsesiViewModel = assesmentAsesiViewModel,
                    nextForm = nextForm,
                    asesiId = assesmentAsesiManager.getAssesmentId(),
                    titleButton = "Setuju",
                    role = userManager.getUserRole()!!
                )
            }
        } ?: run {
            LoadingScreen()
        }
    }
}

// Imports yang perlu ditambah:



@Composable
private fun SuccessDialog(
    onDismiss: () -> Unit,
    onNextForm: () -> Unit,
    userRole: String
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "✓",
                    fontSize = 48.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Berhasil!",
                    fontFamily = AppFont.Poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = when(userRole) {
                        "assesi", "asesi" -> "Jawaban APL02 berhasil dikirim"
                        else -> "Persetujuan APL02 berhasil disimpan"
                    },
                    fontFamily = AppFont.Poppins,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onNextForm,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Kembali ke halaman sebelumnya",
                        fontFamily = AppFont.Poppins,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = onDismiss
                ) {
                    Text(
                        text = "Tutup",
                        fontFamily = AppFont.Poppins,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// Update SubmissionStatusCard untuk UnitGetResponse
@Composable
private fun SubmissionStatusCard(
    submission: UnitGetResponse,
    userRole: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (userRole == "assesi")
                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            else
                MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = if (userRole == "assesi") "Status: Sudah Disubmit" else "Menunggu Persetujuan",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Tanggal Submit: ${submission.submission_date}",
                fontFamily = AppFont.Poppins,
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
private fun UnitCompetensiSection(
    assesmentAsesiId: Int,
    unit: UnitApl02,
    listAttachment: List<Attachment>,
    jawabanManager: JawabanManager,
    submission: GetAPL02Response?,
    isReadOnly: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp
            ),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Unit Kompetensi ${unit.unit_ke}",
            fontFamily = AppFont.Poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black
        )

        Column(
            modifier = Modifier.padding(start = 20.dp)
        ) {
            Text(
                text = "Kode Unit : ${unit.kode_unit}",
                fontFamily = AppFont.Poppins,
                fontSize = 12.sp
            )
            Text(
                text = "Judul Unit : ${unit.judul_unit}",
                fontFamily = AppFont.Poppins,
                fontSize = 12.sp
            )
        }
    }

    Text(
        text = "Dapatkah Saya?",
        fontFamily = AppFont.Poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )

    unit.elements.forEach { elemen ->
        // Find existing data for this element dari semua unit responses
        val existingDetail = submission?.data?.flatMap { it.details }?.find { detail ->
            detail.unit_ke == unit.unit_ke &&
                    detail.kode_unit == unit.kode_unit &&
                    detail.elemen_id == elemen.id
        }

        // Extract existing bukti names
        val existingBuktiNames = existingDetail?.attachments?.map { attachment ->
            attachment.bukti.description
        } ?: emptyList()

        // Debug log
        LaunchedEffect(existingDetail) {
            if (existingDetail != null) {
                Log.d("APL02_AUTO_FILL", "Found existing data for Unit ${unit.unit_ke}, Elemen ${elemen.elemen_index}")
                Log.d("APL02_AUTO_FILL", "Kompetensinitas: ${existingDetail.kompetensinitas}")
                Log.d("APL02_AUTO_FILL", "Bukti count: ${existingBuktiNames.size}")
                existingBuktiNames.forEach { bukti ->
                    Log.d("APL02_AUTO_FILL", "Bukti: $bukti")
                }
            }
        }

        ElemenCard(
            elemen = elemen,
            buktiList = listAttachment,
            existingKompetensinitas = existingDetail?.kompetensinitas,
            existingBuktiNames = existingBuktiNames,
            isReadOnly = isReadOnly,
            onSubmit = { buktiRelevans, kompetensinitas ->
                if (!isReadOnly) {
                    jawabanManager.upsertElemen(
                        skemaId = assesmentAsesiId,
                        unitKe = unit.unit_ke,
                        kodeUnit = unit.kode_unit,
                        elemenId = elemen.id,
                        kompetensinitas = kompetensinitas,
                        buktiList = buktiRelevans
                    )
                }
            }
        )
    }
}

@Composable
private fun ElemenCard(
    elemen: ElemenAPL02,
    buktiList: List<Attachment>,
    existingKompetensinitas: String?,
    existingBuktiNames: List<String>,
    isReadOnly: Boolean,
    onSubmit: (List<String>, String) -> Unit
) {
    val context = LocalContext.current
    val userManager = UserManager(context)
    // State untuk radio button dan checkbox
    var selectedOption by remember { mutableStateOf("") }
    var buktiSelected by remember { mutableStateOf(setOf<String>()) }

    // Key untuk memaksa recomposition ketika data berubah
    val dataKey = remember(existingKompetensinitas, existingBuktiNames.joinToString()) {
        "${existingKompetensinitas}_${existingBuktiNames.joinToString(",")}"
    }

    // Auto-fill data dari submission yang sudah ada
    LaunchedEffect(dataKey) {
        Log.d("APL02_AUTO_FILL", "LaunchedEffect triggered for elemen ${elemen.elemen_index}")
        Log.d("APL02_AUTO_FILL", "Existing kompetensinitas: $existingKompetensinitas")
        Log.d("APL02_AUTO_FILL", "Existing bukti: $existingBuktiNames")

        // Set radio button
        selectedOption = existingKompetensinitas ?: ""

        // Set checkbox selections berdasarkan bukti.description yang match
        val matchingBuktiDescriptions = buktiList.filter { attachment ->
            existingBuktiNames.any { existingBukti ->
                // Match berdasarkan description atau nama dokumen
                attachment.description.equals(existingBukti, ignoreCase = true) ||
                        existingBukti.contains(attachment.description, ignoreCase = true) ||
                        attachment.description.contains(existingBukti, ignoreCase = true)
            }
        }.map { it.description }

        buktiSelected = matchingBuktiDescriptions.toSet()

        Log.d("APL02_AUTO_FILL", "Matched bukti descriptions: $matchingBuktiDescriptions")

        // Auto submit jika ada data existing
        if (existingKompetensinitas != null || existingBuktiNames.isNotEmpty()) {
            Log.d("APL02_AUTO_FILL", "Auto-submitting existing data")
            onSubmit(buktiSelected.toList(), selectedOption)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isReadOnly)
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            else
                MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Elemen ${elemen.elemen_index}: ${elemen.nama_elemen}",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Kriteria Untuk Kerja:",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 8.dp)
            )

            elemen.kriteria_untuk_kerja.forEach { kuk ->
                Text(
                    text = "${elemen.elemen_index}.${kuk.urutan}.${kuk.deskripsi_kuk}",
                    fontFamily = AppFont.Poppins,
                    fontSize = 12.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }
    }

    // Radio Button Section
    RadioButtonGroup(
        options = listOf("k", "bk"),
        selectedOption = selectedOption,
        isReadOnly = isReadOnly,
        onOptionSelected = { option ->
            selectedOption = option
            onSubmit(buktiSelected.toList(), selectedOption)
        }
    )

    // Bukti Relevan Section
    if (userManager.getUserRole()!! == "assesi" || userManager.getUserRole()!! == "asesi"){
        BuktiRelevanSection(
            buktiList = buktiList,
            selectedBukti = buktiSelected.toList(),
            isReadOnly = isReadOnly,
            onBuktiChanged = { bukti, isChecked ->
                buktiSelected = if (isChecked) {
                    buktiSelected + bukti
                } else {
                    buktiSelected - bukti
                }
                onSubmit(buktiSelected.toList(), selectedOption)
            }
        )
    }


    // Show existing custom bukti
    val standardBuktiDescriptions = buktiList.map { it.description }.toSet()
    val customBukti = existingBuktiNames.filter { it !in standardBuktiDescriptions }
    if (customBukti.isNotEmpty()) {
        ExistingCustomBuktiSection(customBukti)
    }
}

@Composable
private fun SubmissionStatusCard(
    submission: GetAPL02Response,
    userRole: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (userRole == "assesi")
                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            else
                MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = if (userRole == "assesi") "Status: Sudah Disubmit" else "Menunggu Persetujuan",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Tanggal Submit: ${submission.data[0].submission_date}",
                fontFamily = AppFont.Poppins,
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
private fun SchemaSection(data: DataApl02) {
    Text(
        text = "Skema Sertifikasi",
        fontFamily = AppFont.Poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )

    Column(
        modifier = Modifier.padding(start = 20.dp)
    ) {
        Text(
            text = "Judul Skema : ${data.judul_skema.orEmpty()}",
            fontFamily = AppFont.Poppins,
            fontSize = 12.sp,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Text(
            text = "Nomor Skema : ${data.nomor_skema.orEmpty()}",
            fontFamily = AppFont.Poppins,
            fontSize = 12.sp,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun InstructionCard() {
    Card(
        modifier = Modifier
            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary.copy(0.7f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "PANDUAN ASSESMEN MANDIRI",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )

            Text(
                text = "Instruksi",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                color = Color(0xFF666666),
                lineHeight = 16.sp
            )

            val instructions = listOf(
                "Baca setiap pertanyaan di kolom sebelah kiri.",
                "Beri tanda centang pada kotak jika Anda yakin dapat melakukan tugas yang dijelaskan.",
                "Isi kolom di sebelah kanan dengan mendaftar bukti yang Anda miliki untuk menunjukkan bahwa Anda melakukan tugas-tugas ini."
            )

            instructions.forEach { instruction ->
                Text(
                    text = instruction,
                    fontFamily = AppFont.Poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
private fun UnitsSection(
    assesmentAsesiId: Int,
    data: DataApl02,
    listAttachment: List<Attachment>,
    jawabanManager: JawabanManager,
    submission: GetAPL02Response?,
    isReadOnly: Boolean
) {
    data.units.forEach { unit ->
        UnitCompetensiSection(
            assesmentAsesiId = assesmentAsesiId,
            listAttachment = listAttachment,
            unit = unit,
            jawabanManager = jawabanManager,
            submission = submission,
            isReadOnly = isReadOnly
        )
    }
}


@Composable
private fun ExistingCustomBuktiSection(customBukti: List<String>) {
    Text(
        text = "Bukti Relevan",
        fontFamily = AppFont.Poppins,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp)
    ) {
        customBukti.forEach { bukti ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Checkbox(
                    checked = true,
                    onCheckedChange = null,
                    enabled = false
                )
                Text(
                    text = bukti,
                    fontFamily = AppFont.Poppins,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun RadioButtonGroup(
    options: List<String>,
    selectedOption: String,
    isReadOnly: Boolean,
    onOptionSelected: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                RadioButton(
                    selected = option == selectedOption,
                    onClick = { if (!isReadOnly) onOptionSelected(option) },
                    enabled = !isReadOnly
                )
                Text(
                    text = option.uppercase(),
                    fontFamily = AppFont.Poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = if (isReadOnly) Color.Gray else Color.Black
                )
            }
        }
    }
}

@Composable
private fun BuktiRelevanSection(
    buktiList: List<Attachment>,
    selectedBukti: List<String>,
    isReadOnly: Boolean,
    onBuktiChanged: (String, Boolean) -> Unit
) {
    Text(
        text = "Bukti Relevan",
        fontFamily = AppFont.Poppins,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp)
    ) {
        buktiList.forEach { bukti ->
            BuktiCheckboxItem(
                bukti = bukti,
                isChecked = bukti.description in selectedBukti,
                isReadOnly = isReadOnly,
                onCheckedChange = { isChecked ->
                    onBuktiChanged(bukti.description, isChecked)
                }
            )
        }
    }
}

@Composable
private fun BuktiCheckboxItem(
    bukti: Attachment,
    isChecked: Boolean,
    isReadOnly: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .let { modifier ->
                if (!isReadOnly) {
                    modifier.clickable {
                        onCheckedChange(!isChecked)
                    }
                } else {
                    modifier
                }
            }
            .padding(vertical = 4.dp)
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { checked ->
                if (!isReadOnly) onCheckedChange(checked)
            },
            enabled = !isReadOnly
        )
        Text(
            text = bukti.description,
            fontFamily = AppFont.Poppins,
            color = if (isReadOnly) Color.Gray else Color.Black,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
private fun SubmitButton(
    apL02ViewModel: APL02ViewModel,
    assesmentAsesiViewModel: AssesmentAsesiViewModel,
    nextForm: () -> Unit,
    asesiId:Int,
    titleButton: String = "Kirim Jawaban",
    role:String = "asesi"
) {
    if(role == "asesi" || role == "assesi" ){
        Button(
            onClick = {
                val jawaban = com.example.mylsp.util.Util.jawabanApl02.value
                apL02ViewModel.sendApl02()
                Log.d("APL02", "Jawaban: $jawaban")
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Kirim Jawaban",
                fontFamily = AppFont.Poppins
            )
        }
    }else{
        Button(
            onClick = {
                apL02ViewModel.approveApl02(asesiId,
                    PostApproveRequest(
                        "approved"
                    )
                )
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = titleButton,
                fontFamily = AppFont.Poppins
            )
        }
        Button(
            onClick = {
                apL02ViewModel.approveApl02(asesiId,
                    PostApproveRequest(
                        "rejected"
                    )
                )
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            )
        ) {
            Text(
                text = "Tolak",
                fontFamily = AppFont.Poppins
            )
        }
    }
}