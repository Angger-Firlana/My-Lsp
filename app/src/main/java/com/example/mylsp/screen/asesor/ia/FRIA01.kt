package com.example.mylsp.screen.asesor.ia

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.mylsp.model.api.assesment.Apl02
import com.example.mylsp.model.api.assesment.ElemenAPL02
import com.example.mylsp.model.api.assesment.IA01Request
import com.example.mylsp.model.api.assesment.IA01UnitSubmission
import com.example.mylsp.model.api.assesment.KriteriaUntukKerja
import com.example.mylsp.model.api.assesment.UnitApl02
import com.example.mylsp.util.AppFont
import com.example.mylsp.util.assesment.AssesmentAsesiManager
import com.example.mylsp.util.assesment.IA01SubmissionManager
import com.example.mylsp.util.user.AsesiManager
import com.example.mylsp.viewmodel.APL02ViewModel
import com.example.mylsp.viewmodel.AssesmentViewModel
import com.example.mylsp.viewmodel.assesment.IA01ViewModel

@Composable
fun FRIA01(
    modifier: Modifier = Modifier,
    idAssesment: Int,
    assesmentViewModel: AssesmentViewModel,
    ia01ViewModel: IA01ViewModel,
    apL02ViewModel: APL02ViewModel,
    nextForm: ()-> Unit
) {
    val assesment by assesmentViewModel.listAssessment.collectAsState()
    val context = LocalContext.current
    val asesiManager = AsesiManager(context)

    val assesmentAsesiManager = AssesmentAsesiManager(context)
    val assesmentAsesiId by remember { mutableStateOf(assesmentAsesiManager.getAssesmentId()) }
    val iA01SubmissionManager = remember { IA01SubmissionManager(context) }
    val apl02 by apL02ViewModel.apl02.collectAsState()

    val ia01Submission by ia01ViewModel.submissions.collectAsState()
    val message by apL02ViewModel.message.collectAsState()
    val pilihan = listOf("IYA", "TIDAK")

    // State untuk dialog
    var showSuccessDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        apL02ViewModel.getAPL02(idAssesment)
        ia01ViewModel.getIA01ByAsesi(asesiManager.getId())
    }

    // Success Dialog
    if (showSuccessDialog) {
        SuccessDialog(
            onDismiss = {
                showSuccessDialog = false
            },
            onNextForm = {
                showSuccessDialog = false
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

                SchemaInfoSection(apl02Data)

                InstructionsCard()

                UnitsSection(
                    units = apl02Data.data,
                    pilihan = pilihan,

                    iA01SubmissionManager = iA01SubmissionManager,
                    assesmentAsesiId = assesmentAsesiId,
                    ia01SubmissionByVM = ia01Submission
                )


                SubmitButtonIa01(
                    ia01ViewModel,
                    assesmentAsesiId,
                    iA01SubmissionManager,
                    onShowSuccessDialog = { showSuccessDialog = true }
                )
            }
        }?: kotlin.run {
            LoadingScreen()
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
    ia01SubmissionByVM:  List<IA01UnitSubmission>?
) {
    units?.forEach { unit ->
        UnitCard(
            unit = unit,
            pilihan = pilihan,
            iA01SubmissionManager = iA01SubmissionManager,
            assesmentAsesiId = assesmentAsesiId,
            ia01SubmissionsFromVM = ia01SubmissionByVM
        )
    }
}

@Composable
private fun UnitCard(
    unit: UnitApl02,
    pilihan: List<String>,
    iA01SubmissionManager: IA01SubmissionManager,
    assesmentAsesiId: Int,
    ia01SubmissionsFromVM: List<IA01UnitSubmission>?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Unit Header
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

        unit.elemen.forEach { (index, elemen) ->
            ElementCard(
                elemen = elemen,
                index = index,
                pilihan = pilihan,
                iA01SubmissionManager = iA01SubmissionManager,
                assesmentAsesiId = assesmentAsesiId,
                kodeUnit = unit.kode_unit,
                unitKe = unit.unit_ke,
                ia01SubmissionsFromVM = ia01SubmissionsFromVM
            )
        }
    }
}

@Composable
private fun ElementCard(
    elemen: ElemenAPL02,
    index: String,
    pilihan: List<String>,
    iA01SubmissionManager: IA01SubmissionManager,
    assesmentAsesiId: Int,
    kodeUnit: String,
    unitKe: Int,
    ia01SubmissionsFromVM: List<IA01UnitSubmission>? // 👈 tambahan
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

            elemen.kuk.forEach { kukItem ->
                KUKItem(
                    kukItem = kukItem,
                    elemenIndex = elemen.elemen_index,
                    pilihan = pilihan,
                    assesmentAsesiId = assesmentAsesiId,
                    kodeUnit = kodeUnit,
                    unitKe = unitKe,
                    submissionManager = iA01SubmissionManager,
                    ia01SubmissionsFromVM = ia01SubmissionsFromVM // 👈 dikirim ke KUKItem
                )
            }
        }
    }
}

@Composable
private fun KUKItem(
    kukItem: KriteriaUntukKerja,
    elemenIndex: Int,
    pilihan: List<String>,
    assesmentAsesiId: Int,
    kodeUnit: String,
    unitKe: Int,
    submissionManager: IA01SubmissionManager,
    ia01SubmissionsFromVM: List<IA01UnitSubmission>? = null // 👈 tambahan
) {
    val context = LocalContext.current

    // Ambil jawaban KUK dari ViewModel kalau ada, fallback ke SubmissionManager
    val savedKuk = ia01SubmissionsFromVM
        ?.find { it.unit_ke == unitKe && it.kode_unit == kodeUnit }
        ?.elemen?.find { it.elemen_id == elemenIndex }
        ?.kuk?.find { it.kuk_id == kukItem.id_kuk }
        ?: submissionManager.getKUKAnswer(
            assesmentAsesiId = assesmentAsesiId,
            unitKe = unitKe,
            kodeUnit = kodeUnit,
            elemenId = elemenIndex,
            kukId = kukItem.id_kuk
        )

    var selectedOption by remember { mutableStateOf(savedKuk?.skkni ?: "") }
    var textFieldValue by remember { mutableStateOf(savedKuk?.penilaian_lanjut?.get(0)?.teks_penilaian ?: "") }

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = "${elemenIndex}.${kukItem.urutan}. ${kukItem.deskripsi_kuk}",
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
                            selectedOption = option
                            submissionManager.saveIA01Answer(
                                assesmentAsesiId = assesmentAsesiId,
                                unitKe = unitKe,
                                kodeUnit = kodeUnit,
                                elemenId = elemenIndex,
                                kukId = kukItem.id_kuk,
                                hasilObservasi = option,
                                catatanAsesor = textFieldValue
                            )
                        }
                    )
                    Text(
                        text = option,
                        fontFamily = AppFont.Poppins,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = textFieldValue,
            onValueChange = {
                textFieldValue = it
                submissionManager.saveIA01Answer(
                    assesmentAsesiId = assesmentAsesiId,
                    unitKe = unitKe,
                    kodeUnit = kodeUnit,
                    elemenId = elemenIndex,
                    kukId = kukItem.id_kuk,
                    hasilObservasi = selectedOption,
                    catatanAsesor = it
                )
            },
            label = { Text("Catatan Asesor", fontFamily = AppFont.Poppins, fontSize = 12.sp) },
            placeholder = {
                Text("Tulis catatan observasi...", fontFamily = AppFont.Poppins, fontSize = 12.sp, color = Color.Gray)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            minLines = 2
        )
    }
}


@Composable
private fun SubmitButtonIa01(
    ia01ViewModel: IA01ViewModel,
    assesmentAsesiId: Int,
    iA01SubmissionManager: IA01SubmissionManager,
    onShowSuccessDialog: () -> Unit
) {
    val context = LocalContext.current
    val ia01Submission = iA01SubmissionManager.getAllSubmissions(assesmentAsesiId)
    val state by ia01ViewModel.state.collectAsState()
    val message by ia01ViewModel.message.collectAsState()

    LaunchedEffect(state) {
        state?.let { success ->
            if (success){
                onShowSuccessDialog()
            } else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Button(
        onClick = {
            // Uncomment this when you want to submit for real
            // ia01ViewModel.submitIA01(...)
            // For now, just showing success dialog
            onShowSuccessDialog()
            Log.d("ia01Submission", ia01Submission.toString())
        },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            "Kirim Penilaian",
            fontFamily = AppFont.Poppins,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
    }
}