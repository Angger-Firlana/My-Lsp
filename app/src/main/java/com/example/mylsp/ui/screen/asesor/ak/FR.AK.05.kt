package com.example.mylsp.ui.screen.asesor.ak

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylsp.common.enums.TypeAlert
import com.example.mylsp.common.enums.TypeDialog
import com.example.mylsp.data.api.assesment.Ak05SubmissionRequest
import com.example.mylsp.data.api.assesment.ElemenAPL02
import com.example.mylsp.data.api.assesment.IA01Detail
import com.example.mylsp.data.api.assesment.KriteriaUntukKerja
import com.example.mylsp.ui.component.form.HeaderForm
import com.example.mylsp.ui.component.form.SkemaSertifikasi
import com.example.mylsp.util.AppFont
import com.example.mylsp.data.local.assesment.AssesmentAsesiManager
import com.example.mylsp.ui.component.alert.AlertCard
import com.example.mylsp.ui.component.dialog.StatusDialog
import com.example.mylsp.viewmodel.assesment.ak.AK05ViewModel
import com.example.mylsp.viewmodel.assesment.AssesmentAsesiViewModel
import com.example.mylsp.viewmodel.assesment.IA01ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FRAK05(
    modifier: Modifier = Modifier,
    ia01ViewModel: IA01ViewModel,
    viewModel: AK05ViewModel,
    assesmentAsesiViewModel: AssesmentAsesiViewModel,
    nextForm: ()-> Unit
) {
    val context = LocalContext.current
    val assesmentManager = remember { AssesmentAsesiManager(context) }

    // State untuk form
    var keputusan by remember { mutableStateOf("") }
    var keterangan by remember { mutableStateOf("") }
    var aspekPositif by remember { mutableStateOf("tidak ada") }
    var aspekNegatif by remember { mutableStateOf("tidak ada") }
    var penolakanHasil by remember { mutableStateOf("") }
    var saranPerbaikan by remember { mutableStateOf("") }

    // State dari ViewModel
    val loading by viewModel.loading.collectAsState()
    val state by viewModel.state.collectAsState()
    val message by viewModel.message.collectAsState()
    val submission by viewModel.submission.collectAsState()
    val ia01Submission by ia01ViewModel.submissions.collectAsState()

    var isKompeten by remember { mutableStateOf<Boolean?>(null) }
    var ia01Details by remember { mutableStateOf<List<IA01Detail>>(emptyList()) }
    var dataFromIA01Loaded by remember { mutableStateOf(false) }

    // Ambil data assesment asesi
    val assesmentAsesi = remember { assesmentManager.getAssesmentAsesi() }
    val assesmentAsesiId = remember { assesmentManager.getAssesmentId() }
    var showAlertDialog by remember { mutableStateOf(false)}
    var isSuccess by remember { mutableStateOf(false)}

    // Fetch existing data saat pertama kali load
    LaunchedEffect(assesmentAsesiId) {
        if (assesmentAsesiId != -1) {
            viewModel.getSubmission(assesmentAsesiId)
            ia01ViewModel.getIA01ByAsesi(assesmentAsesiId)
        }
    }

    // Process IA01 data dan auto-fill keputusan & keterangan
    LaunchedEffect(ia01Submission) {
        ia01Submission?.let { ia01Submissions ->
            val kompeten = !ia01Submissions.details.any { it.skkni == "tidak" }
            isKompeten = kompeten
            ia01Details = ia01Submissions.details.filter { it.skkni != "ya" }
            // Hanya set jika belum ada data dari submission (first load)
            if (!dataFromIA01Loaded && keputusan.isEmpty()) {
                keputusan = if (kompeten) "KOMPETEN" else "BELUM KOMPETEN"

                keterangan = when {
                    kompeten -> "Semua unit kompetensi telah terpenuhi"
                    ia01Details.isNotEmpty() -> {
                        val elements = mutableListOf<ElemenAPL02>()
                        val kuks = mutableListOf<KriteriaUntukKerja>()
                        ia01Details.forEach { ia01Detail->
                            elements.add(ia01Detail.element)
                        }

                        elements.forEach {
                            it.kriteria_untuk_kerja.forEach { kuk->
                                kuks.add(kuk)
                            }
                        }
                        val unitList = ia01Details.joinToString(""){"Kode Unit : ${it.kode_unit} \r\nKriteria Untuk Kerja : ${kuks.find {kuk-> kuk.id == it.kuk_id  }?.deskripsi_kuk}\r\n\r\n"}
                        "Unit kompetensi yang belum terpenuhi \r\n\r\n$unitList"
                    }
                    else -> "Belum ada pengisian dari form sebelumnya"
                }

                dataFromIA01Loaded = true
            }
        }
    }

    // Update form dengan data yang sudah ada dari server
    LaunchedEffect(submission) {
        submission?.data?.firstOrNull()?.let { data ->
            // Data dari server akan override auto-fill
            keputusan = if (data.keputusan == "k") "KOMPETEN" else "BELUM KOMPETEN"
            keterangan = data.keterangan ?: ""
            aspekPositif = data.aspekPositif ?: ""
            aspekNegatif = data.aspekNegatif ?: ""
            penolakanHasil = data.penolakanHasil ?: ""
            saranPerbaikan = data.saranPerbaikan ?: ""

            dataFromIA01Loaded = true // Prevent IA01 from overwriting
        }
    }

    // Show snackbar untuk response
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state, message) {
        if (state != null && message.isNotEmpty()) {
            isSuccess = state == true
            showAlertDialog = true
        }
        viewModel.clearState()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            HeaderForm(
                title = "FORMULIR ASESMEN KOMPETEN (AK-05)",
                subTitle = "Keputusan Asesmen"
            )

            if (submission != null){
                AlertCard(
                    "AK05 Sudah diisi oleh asesor",
                    TypeAlert.Info,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

            }

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                SkemaSertifikasi()
            }


            Spacer(modifier = Modifier.height(16.dp))

            // Form Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                // Keputusan Asesmen
                Text(
                    text = "Keputusan Asesmen",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = AppFont.Poppins,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Column(
                    modifier = Modifier.selectableGroup()
                ) {
                    listOf("KOMPETEN", "BELUM KOMPETEN").forEach { option ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = keputusan == option,
                                    onClick = {
                                        keputusan = option
                                        Log.d("FRAK05", "Keputusan changed to: $option")
                                    },
                                    role = Role.RadioButton
                                )
                                .padding(vertical = 4.dp)
                        ) {
                            RadioButton(
                                selected = keputusan == option,
                                onClick = null // onClick handled by selectable
                            )
                            Text(
                                text = option,
                                fontFamily = AppFont.Poppins,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = keterangan,
                    onValueChange = { keterangan = it },
                    label = {
                        Text(
                            "Keterangan",
                            fontFamily = AppFont.Poppins
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    maxLines = 5
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Aspek Positif
                OutlinedTextField(
                    value = aspekPositif,
                    onValueChange = { aspekPositif = it },
                    label = {
                        Text(
                            "Aspek Positif",
                            fontFamily = AppFont.Poppins
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    maxLines = 5
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Aspek Negatif
                OutlinedTextField(
                    value = aspekNegatif,
                    onValueChange = { aspekNegatif = it },
                    label = {
                        Text(
                            "Aspek Negatif",
                            fontFamily = AppFont.Poppins
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    maxLines = 5
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Penolakan Hasil (jika belum kompeten)
                if (keputusan == "BELUM KOMPETEN") {
                    OutlinedTextField(
                        value = penolakanHasil,
                        onValueChange = { penolakanHasil = it },
                        label = {
                            Text(
                                "Penolakan Hasil",
                                fontFamily = AppFont.Poppins
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        maxLines = 5
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Saran Perbaikan
                    OutlinedTextField(
                        value = saranPerbaikan,
                        onValueChange = { saranPerbaikan = it },
                        label = {
                            Text(
                                "Saran Perbaikan",
                                fontFamily = AppFont.Poppins
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        maxLines = 5
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }



                // Submit Button
                Button(
                    onClick = {
                        if (assesmentAsesiId != -1) {
                            val request = Ak05SubmissionRequest(
                                assesmentAsesiId = assesmentAsesiId,
                                keputusan = if (keputusan == "KOMPETEN") "k" else "bk",
                                keterangan = keterangan.ifEmpty { null },
                                aspekPositif = aspekPositif.ifEmpty { null },
                                aspekNegatif = aspekNegatif.ifEmpty { null },
                                penolakanHasil = penolakanHasil.ifEmpty { null },
                                saranPerbaikan = saranPerbaikan.ifEmpty { null },
                                ttdAsesor = "sudah"
                            )
                            viewModel.sendSubmission(request)
                            assesmentAsesiViewModel.updateStatusAssesmentAsesi(
                                assesmentAsesiId,
                                keputusan.lowercase()
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    enabled = !loading && keputusan.isNotEmpty()
                ) {
                    if (loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text(
                            text = "SUBMIT ASESMEN",
                            fontFamily = AppFont.Poppins,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Informasi Status
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Informasi",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = AppFont.Poppins
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "• Pilih keputusan asesmen (KOMPETEN/BELUM KOMPETEN)\n" +
                                    "• Isi aspek positif dan negatif dari asesmen\n" +
                                    "• Jika BELUM KOMPETEN, isi penolakan hasil dan saran perbaikan\n" +
                                    "• Tanda tangan asesor wajib diisi",
                            fontSize = 12.sp,
                            fontFamily = AppFont.Poppins,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }

        if (showAlertDialog){
            StatusDialog(
                message,
                type = if(isSuccess)TypeDialog.Success else TypeDialog.Failed,
                onClick = {
                    showAlertDialog = false
                    nextForm()
                },
                onDismiss = {
                    showAlertDialog = false
                }
            )
        }
    }
}