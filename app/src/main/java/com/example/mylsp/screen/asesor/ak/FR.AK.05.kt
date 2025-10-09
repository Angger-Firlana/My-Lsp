package com.example.mylsp.screen.asesor.ak

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mylsp.component.HeaderForm
import com.example.mylsp.component.SkemaSertifikasi
import com.example.mylsp.model.api.assesment.Ak05SubmissionRequest
import com.example.mylsp.util.AppFont
import com.example.mylsp.util.assesment.AssesmentAsesiManager
import com.example.mylsp.viewmodel.assesment.AK05ViewModel
import com.example.mylsp.viewmodel.assesment.AssesmentAsesiViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FRAK05(
    modifier: Modifier = Modifier,
    viewModel: AK05ViewModel,
    assesmentAsesiViewModel: AssesmentAsesiViewModel,
) {
    val context = LocalContext.current
    val assesmentManager = remember { AssesmentAsesiManager(context) }

    // State untuk form
    var keputusan by remember { mutableStateOf("") }
    var keterangan by remember { mutableStateOf("") }
    var aspekPositif by remember { mutableStateOf("") }
    var aspekNegatif by remember { mutableStateOf("") }
    var penolakanHasil by remember { mutableStateOf("") }
    var saranPerbaikan by remember { mutableStateOf("") }
    var ttdAsesor by remember { mutableStateOf("") }

    // State dari ViewModel
    val loading by viewModel.loading.collectAsState()
    val state by viewModel.state.collectAsState()
    val message by viewModel.message.collectAsState()
    val submission by viewModel.submission.collectAsState()

    // Ambil data assesment asesi
    val assesmentAsesi = remember { assesmentManager.getAssesmentAsesi() }
    val assesmentAsesiId = remember { assesmentManager.getAssesmentId() }

    // Fetch existing data saat pertama kali load
    LaunchedEffect(assesmentAsesiId) {
        if (assesmentAsesiId != -1) {
            viewModel.getSubmission(assesmentAsesiId)
        }
    }

    // Update form dengan data yang sudah ada
    LaunchedEffect(submission) {
        submission?.data?.firstOrNull()?.let { data ->
            keputusan = data.keputusan
            keterangan = data.keterangan ?: ""
            aspekPositif = data.aspekPositif ?: ""
            aspekNegatif = data.aspekNegatif ?: ""
            penolakanHasil = data.penolakanHasil ?: ""
            saranPerbaikan = data.saranPerbaikan ?: ""
            ttdAsesor = data.ttdAsesor
        }
    }

    // Show snackbar untuk response
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state, message) {
        if (state != null && message.isNotEmpty()) {
            snackbarHostState.showSnackbar(
                message = message,
                actionLabel = "OK"
            )
        }
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

            SkemaSertifikasi()

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
                                    selected = (keputusan == option),
                                    onClick = { keputusan = option },
                                    role = Role.RadioButton
                                )
                                .padding(vertical = 4.dp)
                        ) {
                            RadioButton(
                                selected = (keputusan == option),
                                onClick = null
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

                // Keterangan
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

                // Tanda Tangan Asesor
                OutlinedTextField(
                    value = ttdAsesor,
                    onValueChange = { ttdAsesor = it },
                    label = {
                        Text(
                            "Tanda Tangan Asesor",
                            fontFamily = AppFont.Poppins
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            "Masukkan nama asesor untuk tanda tangan",
                            fontFamily = AppFont.Poppins,
                            color = Color.Gray
                        )
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Submit Button
                Button(
                    onClick = {
                        if (assesmentAsesiId != -1) {
                            val request = Ak05SubmissionRequest(
                                assesmentAsesiId = assesmentAsesiId,
                                keputusan = if(keputusan == "KOMPETEN") "k" else "bk",
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
                    enabled = !loading && keputusan.isNotEmpty() && ttdAsesor.isNotEmpty()
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
    }
}