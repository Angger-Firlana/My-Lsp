package com.example.mylsp.screen.asesor.ia

import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.window.Dialog
import com.example.mylsp.component.HeaderForm
import com.example.mylsp.component.LoadingScreen
import com.example.mylsp.model.api.assesment.*
import com.example.mylsp.util.AppFont
import com.example.mylsp.util.assesment.AssesmentAsesiManager
import com.example.mylsp.util.assesment.IA01SubmissionManager
import com.example.mylsp.util.user.AsesiManager
import com.example.mylsp.viewmodel.APL02ViewModel
import com.example.mylsp.viewmodel.AssesmentViewModel
import com.example.mylsp.viewmodel.assesment.IA01ViewModel
import com.google.gson.Gson

@Composable
fun FRIA01(
    modifier: Modifier = Modifier,
    idAssesment: Int,
    assesmentViewModel: AssesmentViewModel,
    ia01ViewModel: IA01ViewModel,
    apL02ViewModel: APL02ViewModel,
    nextForm: () -> Unit
) {
    val context = LocalContext.current
    val asesiManager = AsesiManager(context)
    val assesmentAsesiManager = AssesmentAsesiManager(context)
    val assesmentAsesiId by remember { mutableStateOf(assesmentAsesiManager.getAssesmentId()) }
    val iA01SubmissionManager = remember { IA01SubmissionManager(context) }

    val apl02 by apL02ViewModel.apl02.collectAsState()
    val ia01SubmissionData by ia01ViewModel.submissions.collectAsState()
    val pilihan = listOf("IYA", "TIDAK")

    var showSuccessDialog by remember { mutableStateOf(false) }
    var showValidationDialog by remember { mutableStateOf(false) }
    var validationMessage by remember { mutableStateOf("") }
    var showAlreadySubmittedDialog by remember { mutableStateOf(false) }

    // State untuk tracking apakah form sudah pernah disubmit
    var isFormSubmitted by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        apL02ViewModel.getAPL02(idAssesment)
        ia01ViewModel.getIA01ByAsesi(asesiManager.getId())
        Log.d("IA01InScreen", ia01SubmissionData.toString())
    }

    // LaunchedEffect untuk memantau perubahan data submission dari ViewModel
    LaunchedEffect(ia01SubmissionData) {
        ia01SubmissionData?.details?.let { submissions ->
            if (submissions.isNotEmpty()) {
                Log.d("IA01Sync", "Syncing ${submissions.size} submissions from server")

                // Sync semua data dari server ke SubmissionManager
                submissions.forEach { detail ->
                    iA01SubmissionManager.saveIA01Answer(
                        assesmentAsesiId = assesmentAsesiId,
                        unitKe = detail.unit_ke,
                        kodeUnit = detail.kode_unit,
                        elemenId = detail.elemen_id,
                        kukId = detail.kuk_id,
                        hasilObservasi = detail.skkni,
                        catatanAsesor = detail.teks_penilaian
                    )
                }

                // Set form sebagai sudah disubmit
                isFormSubmitted = true
                Log.d("IA01Sync", "Successfully synced all data to local storage")
                Log.d("IA01Status", "Form has been previously submitted")
            }
        }
    }

    val ia01Submissions = ia01SubmissionData?.details

    if (showSuccessDialog) {
        SuccessDialog(
            onDismiss = {
                showSuccessDialog = false
                ia01ViewModel.resetState()
            },
            onNextForm = {
                showSuccessDialog = false
                ia01ViewModel.resetState()
                nextForm()
            }
        )
    }

    if (showValidationDialog) {
        ValidationDialog(
            message = validationMessage,
            onDismiss = { showValidationDialog = false }
        )
    }

    if (showAlreadySubmittedDialog) {
        AlreadySubmittedDialog(
            onDismiss = { showAlreadySubmittedDialog = false },
            onNextForm = {
                showAlreadySubmittedDialog = false
                nextForm()
            }
        )
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        apl02?.let { apl02Data ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeaderForm(
                    title = "FR.IA.01.CL",
                    subTitle = "CEKLIS OBSERVASI AKTIVITAS DI TEMPAT KERJA ATAU TEMPAT KERJA SIMULASI"
                )

                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                // Info banner jika form sudah disubmit
                if (isFormSubmitted) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "ℹ️",
                                fontSize = 24.sp,
                                modifier = Modifier.padding(end = 12.dp)
                            )
                            Column {
                                Text(
                                    text = "Form Sudah Terisi",
                                    fontFamily = AppFont.Poppins,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Text(
                                    text = "Data penilaian sudah pernah dikirim sebelumnya",
                                    fontFamily = AppFont.Poppins,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }
                }

                SchemaInfoSection(apl02Data)
                InstructionsCard()

                UnitsSection(
                    units = apl02Data.data.units,
                    pilihan = pilihan,
                    iA01SubmissionManager = iA01SubmissionManager,
                    assesmentAsesiId = assesmentAsesiId,
                    ia01SubmissionsFromVM = ia01Submissions,
                    isFormSubmitted = isFormSubmitted
                )

                SubmitButtonIa01(
                    ia01ViewModel = ia01ViewModel,
                    assesmentAsesiId = assesmentAsesiId,
                    iA01SubmissionManager = iA01SubmissionManager,
                    apl02Data = apl02Data,
                    isFormSubmitted = isFormSubmitted,
                    onShowSuccessDialog = { showSuccessDialog = true },
                    onShowValidationDialog = { message ->
                        validationMessage = message
                        showValidationDialog = true
                    },
                    onShowAlreadySubmittedDialog = { showAlreadySubmittedDialog = true }
                )
            }
        } ?: run {
            LoadingScreen()
        }
    }
}

@Composable
private fun AlreadySubmittedDialog(
    onDismiss: () -> Unit,
    onNextForm: () -> Unit
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
                    text = "ℹ️",
                    fontSize = 48.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Form Sudah Diisi",
                    fontFamily = AppFont.Poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Penilaian IA01 sudah pernah dikirim sebelumnya. Anda dapat melanjutkan ke form berikutnya.",
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
                        text = "Lanjut ke Form Berikutnya",
                        fontFamily = AppFont.Poppins,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onDismiss) {
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

@Composable
private fun ValidationDialog(
    message: String,
    onDismiss: () -> Unit
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
                    text = "⚠️",
                    fontSize = 48.sp,
                    color = MaterialTheme.colorScheme.error
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Validasi Gagal",
                    fontFamily = AppFont.Poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = message,
                    fontFamily = AppFont.Poppins,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Mengerti",
                        fontFamily = AppFont.Poppins,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun SuccessDialog(
    onDismiss: () -> Unit,
    onNextForm: () -> Unit
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
                    text = "Penilaian IA01 berhasil dikirim",
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
                        text = "Lanjut ke Form Berikutnya",
                        fontFamily = AppFont.Poppins,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onDismiss) {
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

@Composable
private fun SchemaInfoSection(apl02Data: Apl02) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Skema Sertifikasi",
            fontFamily = AppFont.Poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = "Judul Skema: ${apl02Data.judul_skema ?: ""}",
                fontFamily = AppFont.Poppins,
                fontSize = 12.sp,
                color = Color.Black
            )
            Text(
                text = "Nomor Skema: ${apl02Data.nomor_skema ?: ""}",
                fontFamily = AppFont.Poppins,
                fontSize = 12.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
private fun InstructionsCard() {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary.copy(0.7f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "PANDUAN ASESMEN MANDIRI",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Instruksi:",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                color = Color(0xFF666666)
            )

            val instructions = listOf(
                "1. Baca setiap pertanyaan di kolom sebelah kiri.",
                "2. Beri tanda centang pada kotak jika Anda yakin dapat melakukan tugas yang dijelaskan.",
                "3. Isi kolom di sebelah kanan dengan mendaftar bukti yang Anda miliki untuk menunjukkan bahwa Anda melakukan tugas-tugas ini."
            )

            instructions.forEach { instruction ->
                Text(
                    text = instruction,
                    fontFamily = AppFont.Poppins,
                    fontSize = 12.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
private fun UnitsSection(
    units: List<UnitApl02>?,
    pilihan: List<String>,
    iA01SubmissionManager: IA01SubmissionManager,
    assesmentAsesiId: Int,
    ia01SubmissionsFromVM: List<IA01Detail>?,
    isFormSubmitted: Boolean
) {
    units?.forEach { unit ->
        UnitCard(
            unit = unit,
            pilihan = pilihan,
            iA01SubmissionManager = iA01SubmissionManager,
            assesmentAsesiId = assesmentAsesiId,
            ia01SubmissionsFromVM = ia01SubmissionsFromVM,
            isFormSubmitted = isFormSubmitted
        )
    }
}

@Composable
private fun UnitCard(
    unit: UnitApl02,
    pilihan: List<String>,
    iA01SubmissionManager: IA01SubmissionManager,
    assesmentAsesiId: Int,
    ia01SubmissionsFromVM: List<IA01Detail>?,
    isFormSubmitted: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Unit Kompetensi ${unit.unit_ke}",
            fontFamily = AppFont.Poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black
        )

        Column(modifier = Modifier.padding(start = 16.dp, top = 4.dp)) {
            Text(
                text = "Kode Unit: ${unit.kode_unit}",
                fontFamily = AppFont.Poppins,
                fontSize = 12.sp,
                color = Color.Black
            )
            Text(
                text = "Judul Unit: ${unit.judul_unit}",
                fontFamily = AppFont.Poppins,
                fontSize = 12.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Observasi Aktivitas",
            fontFamily = AppFont.Poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        unit.elements.forEach { elemen ->
            ElementCard(
                elemen = elemen,
                pilihan = pilihan,
                iA01SubmissionManager = iA01SubmissionManager,
                assesmentAsesiId = assesmentAsesiId,
                kodeUnit = unit.kode_unit,
                unitKe = unit.unit_ke,
                ia01SubmissionsFromVM = ia01SubmissionsFromVM,
                isFormSubmitted = isFormSubmitted
            )
        }
    }
}

@Composable
private fun ElementCard(
    elemen: ElemenAPL02,
    pilihan: List<String>,
    iA01SubmissionManager: IA01SubmissionManager,
    assesmentAsesiId: Int,
    kodeUnit: String,
    unitKe: Int,
    ia01SubmissionsFromVM: List<IA01Detail>?,
    isFormSubmitted: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
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
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Kriteria Untuk Kerja:",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = Color.Black
            )

            elemen.kriteria_untuk_kerja.forEach { kukItem ->
                KUKItem(
                    kukItem = kukItem,
                    elemen = elemen,
                    pilihan = pilihan,
                    assesmentAsesiId = assesmentAsesiId,
                    kodeUnit = kodeUnit,
                    unitKe = unitKe,
                    submissionManager = iA01SubmissionManager,
                    ia01SubmissionsFromVM = ia01SubmissionsFromVM,
                    isFormSubmitted = isFormSubmitted
                )
            }
        }
    }
}

@Composable
private fun KUKItem(
    kukItem: KriteriaUntukKerja,
    elemen: ElemenAPL02,
    pilihan: List<String>,
    assesmentAsesiId: Int,
    kodeUnit: String,
    unitKe: Int,
    submissionManager: IA01SubmissionManager,
    ia01SubmissionsFromVM: List<IA01Detail>?,
    isFormSubmitted: Boolean
) {
    val displayToApiValue = mapOf("IYA" to "ya", "TIDAK" to "tidak")
    val apiToDisplayValue = mapOf("ya" to "IYA", "tidak" to "TIDAK")

    // Cari data dari ViewModel terlebih dahulu
    val savedFromVM = ia01SubmissionsFromVM?.find {
        it.unit_ke == unitKe &&
                it.kode_unit == kodeUnit &&
                it.elemen_id == elemen.id &&
                it.kuk_id == kukItem.id
    }

    // Fallback ke SubmissionManager jika tidak ada di ViewModel
    val savedFromManager = submissionManager.getKUKAnswer(
        assesmentAsesiId = assesmentAsesiId,
        unitKe = unitKe,
        kodeUnit = kodeUnit,
        elemenId = elemen.id,
        kukId = kukItem.id
    )

    // Prioritaskan data dari ViewModel
    val savedData = savedFromVM

    var selectedOption by remember(savedData) {
        mutableStateOf(apiToDisplayValue[savedData?.skkni] ?: savedFromManager?.skkni?: "")
    }

    var textFieldValue by remember(savedData) {
        mutableStateOf(savedData?.teks_penilaian ?: savedFromManager?.teks_penilaian?:"")
    }

    // Sync data dari ViewModel ke SubmissionManager saat ada perubahan
    LaunchedEffect(savedFromVM) {
        savedFromVM?.let { data ->
            submissionManager.saveIA01Answer(
                assesmentAsesiId = assesmentAsesiId,
                unitKe = unitKe,
                kodeUnit = kodeUnit,
                elemenId = elemen.id,
                kukId = kukItem.id,
                hasilObservasi = data.skkni,
                catatanAsesor = data.teks_penilaian
            )
            // Update state dengan data terbaru
            selectedOption = apiToDisplayValue[data.skkni] ?: data.skkni.uppercase()
            textFieldValue = data.teks_penilaian
            Log.d("KUKItem", "Synced from VM - KUK ID: ${kukItem.id}, Value: ${data.skkni}")
        }
    }

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = "${elemen.id}.${kukItem.urutan}. ${kukItem.deskripsi_kuk}",
            fontFamily = AppFont.Poppins,
            fontSize = 12.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            pilihan.forEach { option ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = option == selectedOption,
                        onClick = {
                            if (!isFormSubmitted) {
                                selectedOption = option
                                val apiValue = displayToApiValue[option] ?: option.lowercase()
                                submissionManager.saveIA01Answer(
                                    assesmentAsesiId = assesmentAsesiId,
                                    unitKe = unitKe,
                                    kodeUnit = kodeUnit,
                                    elemenId = elemen.id,
                                    kukId = kukItem.id,
                                    hasilObservasi = apiValue,
                                    catatanAsesor = textFieldValue
                                )
                                Log.d("KUKItem", "Selected - KUK ID: ${kukItem.id}, Value: $apiValue")
                            }
                        },
                        enabled = !isFormSubmitted
                    )
                    Text(
                        text = option,
                        fontFamily = AppFont.Poppins,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 4.dp),
                        color = if (isFormSubmitted) Color.Gray else Color.Black
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = textFieldValue,
            onValueChange = {
                if (!isFormSubmitted) {
                    textFieldValue = it
                    val apiValue = displayToApiValue[selectedOption] ?: selectedOption.lowercase()
                    submissionManager.saveIA01Answer(
                        assesmentAsesiId = assesmentAsesiId,
                        unitKe = unitKe,
                        kodeUnit = kodeUnit,
                        elemenId = elemen.id,
                        kukId = kukItem.id,
                        hasilObservasi = apiValue,
                        catatanAsesor = it
                    )
                }
            },
            label = {
                Text("Catatan Asesor", fontFamily = AppFont.Poppins, fontSize = 12.sp)
            },
            placeholder = {
                Text(
                    "Tulis catatan observasi...",
                    fontFamily = AppFont.Poppins,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            minLines = 2,
            enabled = !isFormSubmitted,
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = Color.Gray,
                disabledBorderColor = Color.LightGray,
                disabledLabelColor = Color.Gray
            )
        )
    }
}

@Composable
private fun SubmitButtonIa01(
    ia01ViewModel: IA01ViewModel,
    assesmentAsesiId: Int,
    iA01SubmissionManager: IA01SubmissionManager,
    apl02Data: Apl02,
    isFormSubmitted: Boolean,
    onShowSuccessDialog: () -> Unit,
    onShowValidationDialog: (String) -> Unit,
    onShowAlreadySubmittedDialog: () -> Unit
) {
    val context = LocalContext.current
    val state by ia01ViewModel.state.collectAsState()
    val message by ia01ViewModel.message.collectAsState()

    LaunchedEffect(state) {
        state?.let { success ->
            if (success) {
                onShowSuccessDialog()
            } else {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
            ia01ViewModel.resetState()
        }
    }

    Button(
        onClick = {
            // Cek apakah form sudah pernah disubmit
            if (isFormSubmitted) {
                onShowAlreadySubmittedDialog()
                return@Button
            }

            val ia01Submission = iA01SubmissionManager.getAllSubmissions(assesmentAsesiId)

            Log.d("SubmitValidation", "Total submissions: ${ia01Submission.size}")

            // Hitung total KUK dari APL02
            val allKUKs = mutableListOf<Triple<Int, String, Int>>() // (unitKe, kodeUnit, kukId)
            apl02Data.data.units?.forEach { unit ->
                unit.elements.forEach { elemen ->
                    elemen.kriteria_untuk_kerja.forEach { kuk ->
                        allKUKs.add(Triple(unit.unit_ke, unit.kode_unit, kuk.id))
                    }
                }
            }

            val totalKUK = allKUKs.size
            Log.d("SubmitValidation", "Total KUK expected: $totalKUK")

            // Validasi 1: Cek apakah ada submission
            if (ia01Submission.isEmpty()) {
                onShowValidationDialog("Harap isi setidaknya satu penilaian sebelum mengirim")
                return@Button
            }

            // Validasi 2: Cek setiap KUK secara detail
            val missingKUKs = mutableListOf<String>()
            val unselectedKUKs = mutableListOf<String>()
            val emptyNotesKUKs = mutableListOf<String>()

            apl02Data.data.units?.forEachIndexed { unitIndex, unit ->
                unit.elements.forEachIndexed { elemenIndex, elemen ->
                    elemen.kriteria_untuk_kerja.forEachIndexed { kukIndex, kuk ->
                        // Cari submission untuk KUK ini
                        val unitSubmission = ia01Submission.find {
                            it.unit_ke == unit.unit_ke && it.kode_unit == unit.kode_unit
                        }

                        val elemenSubmission = unitSubmission?.elemen?.find {
                            it.elemen_id == elemen.id
                        }

                        val kukSubmission = elemenSubmission?.kuk?.find {
                            it.kuk_id == kuk.id
                        }

                        val kukLabel = "Unit ${unit.unit_ke} - Elemen ${elemen.elemen_index}.${kuk.urutan}"

                        if (kukSubmission == null) {
                            missingKUKs.add(kukLabel)
                            Log.d("SubmitValidation", "Missing KUK: $kukLabel")
                        } else {
                            // Cek pilihan IYA/TIDAK
                            if (kukSubmission.skkni.isBlank() ||
                                (kukSubmission.skkni != "ya" && kukSubmission.skkni != "tidak")) {
                                unselectedKUKs.add(kukLabel)
                                Log.d("SubmitValidation", "Unselected KUK: $kukLabel, value: ${kukSubmission.skkni}")
                            }

                            // Cek catatan
                            if (kukSubmission.teks_penilaian.isBlank()) {
                                emptyNotesKUKs.add(kukLabel)
                                Log.d("SubmitValidation", "Empty note KUK: $kukLabel")
                            }
                        }
                    }
                }
            }

            // Tampilkan error berdasarkan prioritas
            when {
                missingKUKs.isNotEmpty() -> {
                    val details = if (missingKUKs.size <= 5) {
                        "\n\nYang belum diisi:\n${missingKUKs.joinToString("\n")}"
                    } else {
                        "\n\nYang belum diisi:\n${missingKUKs.take(5).joinToString("\n")}\n... dan ${missingKUKs.size - 5} lainnya"
                    }
                    onShowValidationDialog(
                        "Masih ada ${missingKUKs.size} dari $totalKUK penilaian yang belum diisi.$details"
                    )
                    return@Button
                }

                unselectedKUKs.isNotEmpty() -> {
                    val details = if (unselectedKUKs.size <= 5) {
                        "\n\nYang belum dipilih:\n${unselectedKUKs.joinToString("\n")}"
                    } else {
                        "\n\nYang belum dipilih:\n${unselectedKUKs.take(5).joinToString("\n")}\n... dan ${unselectedKUKs.size - 5} lainnya"
                    }
                    onShowValidationDialog(
                        "Masih ada ${unselectedKUKs.size} KUK yang belum dipilih IYA/TIDAK.$details"
                    )
                    return@Button
                }

                emptyNotesKUKs.isNotEmpty() -> {
                    val details = if (emptyNotesKUKs.size <= 5) {
                        "\n\nYang catatannya kosong:\n${emptyNotesKUKs.joinToString("\n")}"
                    } else {
                        "\n\nYang catatannya kosong:\n${emptyNotesKUKs.take(5).joinToString("\n")}\n... dan ${emptyNotesKUKs.size - 5} lainnya"
                    }
                    onShowValidationDialog(
                        "Masih ada ${emptyNotesKUKs.size} catatan asesor yang kosong.$details"
                    )
                    return@Button
                }
            }

            // Jika lolos semua validasi, kirim data
            Log.d("SubmitValidation", "All validations passed!")
            ia01ViewModel.SendSubmissionIA01(
                IA01Request(
                    assesment_asesi_id = assesmentAsesiId,
                    ia01Submission
                )
            )
            Log.d("ia01Submission", "Sending: ${ia01Submission}")
        },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isFormSubmitted)
                MaterialTheme.colorScheme.secondary
            else
                MaterialTheme.colorScheme.primary
        )
    ) {
        Text(
            text = if (isFormSubmitted) "Form Sudah Terisi" else "Kirim Penilaian",
            fontFamily = AppFont.Poppins,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
    }
}