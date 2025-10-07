package com.example.mylsp.screen.asesor.ak

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import com.example.mylsp.component.SkemaSertifikasi
import com.example.mylsp.model.api.assesment.AK01
import com.example.mylsp.model.api.assesment.AK01Submission
import com.example.mylsp.model.api.assesment.AttachmentAk01
import com.example.mylsp.model.api.assesment.GetAK01Response
import com.example.mylsp.util.AppFont
import com.example.mylsp.util.assesment.AssesmentAsesiManager
import com.example.mylsp.util.user.AsesiManager
import com.example.mylsp.util.user.UserManager
import com.example.mylsp.viewmodel.AK01ViewModel

@Composable
fun FRAK01(
    modifier: Modifier = Modifier,
    aK01ViewModel: AK01ViewModel,
    nextForm: () -> Unit
) {
    // Collect states from ViewModel
    val context = LocalContext.current
    val loading by aK01ViewModel.loading.collectAsState()
    val state by aK01ViewModel.state.collectAsState()
    val message by aK01ViewModel.message.collectAsState()
    val submission by aK01ViewModel.submission.collectAsState()
    val assesmentAsesiManager = AssesmentAsesiManager(context)
    val asesiManager = AsesiManager(context)
    val assesmentAsesiId = assesmentAsesiManager.getAssesmentId()

    // State untuk dialog
    var showSuccessDialog by remember { mutableStateOf(false) }

    // Load existing data jika ada
    LaunchedEffect(assesmentAsesiId) {
        aK01ViewModel.getSubmission(assesmentAsesiId)
        Log.d(
            "AK01GetForm",
            submission.toString()
        )
    }

    // Handle successful submission
    LaunchedEffect(state) {
        if (state == true) {
            showSuccessDialog = true
        } else if (state == false && message.isNotEmpty()) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
        aK01ViewModel.clearState()
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        HeaderForm(
            "FR.AK.01",
            "PERSETUJUAN ASESMEN DAN KERAHASIAAN"
        )
        submission?.let { submission ->

                // Tampilan untuk data yang sudah ada
            if (!submission.data.isNullOrEmpty()){

                        ExistingSubmissionView(
                            submissionData = submission.data.first(),
                            loading = loading,
                            onApprove = {
                                // Logic untuk approve (untuk asesi)
                                // Bisa tambah endpoint khusus untuk approve
                                aK01ViewModel.approveAk01(submission.data.first().id)
                                nextForm()
                            }
                        )


            }else{
                EmptyFormView(
                    loading = false,
                    state = null,
                    message = "",
                    onSubmit = {
                        aK01ViewModel.sendSubmission(it)
                    }
                )
            }


        }?:run {
            EmptyFormView(
                loading = false,
                state = null,
                message = "",
                onSubmit = {

                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun ExistingSubmissionView(
    submissionData: AK01, // Ganti dengan tipe data yang sesuai
    loading: Boolean,
    onApprove: () -> Unit
) {
    Column {
        // Status Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "📋 Form FR.AK.01 Sudah Diisi",
                            fontSize = 14.sp,
                            fontFamily = AppFont.Poppins,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Status: ${submissionData.status} • ${submissionData.submission_date}",
                            fontSize = 11.sp,
                            fontFamily = AppFont.Poppins,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Status badge
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = when(submissionData.status) {
                                "pending" -> Color(0xFFFFF3CD)
                                "approved" -> Color(0xFFD1E7DD)
                                "rejected" -> Color(0xFFF8D7DA)
                                else -> Color.Gray.copy(alpha = 0.2f)
                            }
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = when(submissionData.status) {
                                "pending" -> "⏳ Menunggu"
                                "approved" -> "✅ Disetujui"
                                "rejected" -> "❌ Ditolak"
                                else -> "📝 Draft"
                            },
                            fontSize = 10.sp,
                            fontFamily = AppFont.Poppins,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            color = when(submissionData.status) {
                                "pending" -> Color(0xFF856404)
                                "approved" -> Color(0xFF0F5132)
                                "rejected" -> Color(0xFF842029)
                                else -> Color.Black
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Summary Card - Tampilkan ringkasan data yang sudah diisi
        SubmissionSummaryCard(submissionData)

        Spacer(modifier = Modifier.height(16.dp))

        // Action buttons berdasarkan role dan status
        ActionButtonsSection(
            submissionData = submissionData,
            loading = loading,
            onApprove = onApprove
        )
    }
}

@Composable
private fun SubmissionSummaryCard(submissionData: AK01) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "📄 Ringkasan Persetujuan",
                fontSize = 14.sp,
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Parse dan tampilkan data dari attachments
            val attachments = submissionData.attachments
            val evidenceList = mutableListOf<String>()
            var scheduleInfo = ""
            var agreementInfo = ""

            attachments.forEach { attachment ->
                when {
                    attachment.description.contains("Hasil ") -> {
                        evidenceList.add(attachment.description)
                    }
                    attachment.description.contains("Jadwal") ||
                            attachment.description.contains("Lokasi") -> {
                        scheduleInfo += "${attachment.description}\n"
                    }
                    attachment.description.contains("Persetujuan") -> {
                        agreementInfo += "✅ ${attachment.description.split(":")[0]}\n"
                    }
                }
            }

            // Bukti yang dipilih
            if (evidenceList.isNotEmpty()) {
                SummarySection(
                    title = "📋 Bukti yang Dikumpulkan:",
                    content = evidenceList.joinToString("\n") { "• ${it.replace("Hasil ", "")}" }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Jadwal asesmen
            if (scheduleInfo.isNotEmpty()) {
                SummarySection(
                    title = "📅 Jadwal Asesmen:",
                    content = scheduleInfo.trim()
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Status persetujuan
            if (agreementInfo.isNotEmpty()) {
                SummarySection(
                    title = "✅ Status Persetujuan:",
                    content = agreementInfo.trim()
                )
            }
        }
    }
}

@Composable
private fun SummarySection(title: String, content: String) {
    Column {
        Text(
            text = title,
            fontSize = 12.sp,
            fontFamily = AppFont.Poppins,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = content,
            fontSize = 11.sp,
            fontFamily = AppFont.Poppins,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 14.sp
        )
    }
}

@Composable
private fun ActionButtonsSection(
    submissionData: AK01,
    loading: Boolean,
    onApprove: () -> Unit
) {
    val context = LocalContext.current
    val asesiManager = AsesiManager(context)
    val userManager = UserManager(context)
    val userRole = userManager.getUserRole() // Asumsi ada method untuk get role

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        when (userRole) {
            "asesi", "assesi" -> {
                // Button untuk asesi
                when (submissionData.status) {
                    "pending", "draft" -> {
                        Button(
                            onClick = onApprove,
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !loading,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            if (loading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    strokeWidth = 2.dp,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                            Text(
                                text = "✅ Setujui & Lanjutkan",
                                fontSize = 14.sp,
                                fontFamily = AppFont.Poppins,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    "approved" -> {
                        Button(
                            onClick = onApprove,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(
                                text = "➡️ Lanjut ke Form Berikutnya",
                                fontSize = 14.sp,
                                fontFamily = AppFont.Poppins,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }


            }

            "asesor", "assesor" -> {
                // Button untuk asesor
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onApprove,
                        modifier = Modifier.weight(1f),
                        enabled = !loading
                    ) {
                        if (loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                        Text(
                            text = "➡️ Lanjut",
                            fontSize = 14.sp,
                            fontFamily = AppFont.Poppins,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Info text
        Text(
            text = when (userRole) {
                "asesi" -> "💡 Pastikan semua informasi sudah benar sebelum menyetujui"
                "asesor" -> "💡 Form ini telah diisi, Anda dapat melanjutkan atau mengeditnya"
                else -> ""
            },
            fontSize = 11.sp,
            fontFamily = AppFont.Poppins,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun EmptyFormView(
    loading: Boolean,
    state: Boolean?,
    message: String,
    onSubmit: (AK01Submission) -> Unit
) {
    val context = LocalContext.current
    val assesmentAsesiManager = AssesmentAsesiManager(context)
    val assesmentAsesiId = assesmentAsesiManager.getAssesmentId()

    // Form states
    var evidenceCheckedStates by remember {
        mutableStateOf(
            mutableMapOf(
                0 to false, 1 to false, 2 to false, 3 to false,
                4 to false, 5 to false, 6 to false
            )
        )
    }

    var dayDate by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var tuk by remember { mutableStateOf("") }

    var asesiAgreement by remember { mutableStateOf(false) }
    var asesorAgreement by remember { mutableStateOf(false) }
    var confidentialityAgreement by remember { mutableStateOf(false) }

    Column {
        Text(
            "Persetujuan Asesmen ini untuk menjamin bahwa Asesi telah diberi arahan secara rinci tentang perancangan dan proses asesmen.",
            fontSize = 14.sp,
            fontFamily = AppFont.Poppins,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        SkemaSertifikasi()

        Spacer(modifier = Modifier.height(16.dp))

        // Bukti Section
        BuktiSection(
            checkedStates = evidenceCheckedStates,
            onCheckedChange = { index, checked ->
                evidenceCheckedStates = evidenceCheckedStates.toMutableMap().apply {
                    set(index, checked)
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Pelaksanaan Asesmen Section
        PelaksanaanAsesmenSection(
            dayDate = dayDate,
            onDayDateChange = { dayDate = it },
            time = time,
            onTimeChange = { time = it },
            tuk = tuk,
            onTukChange = { tuk = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Pernyataan Section
        PernyataanSection(
            asesiAgreement = asesiAgreement,
            onAsesiAgreementChange = { asesiAgreement = it },
            asesorAgreement = asesorAgreement,
            onAsesorAgreementChange = { asesorAgreement = it },
            confidentialityAgreement = confidentialityAgreement,
            onConfidentialityAgreementChange = { confidentialityAgreement = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Submit Button
        Button(
            onClick = {
                val attachments = buildList {
                    val evidenceItems = listOf(
                        "Hasil verifikasi Portofolio",
                        "Hasil Observasi Langsung",
                        "Hasil Pertanyaan Lisan",
                        "Hasil review produk",
                        "Hasil kegiatan Terstruktur",
                        "Hasil Pertanyaan Tertulis",
                        "Hasil Pertanyaan wawancara"
                    )

                    evidenceCheckedStates.forEach { (index, isChecked) ->
                        if (isChecked && index < evidenceItems.size) {
                            add(AttachmentAk01("${evidenceItems[index]}"))
                        }
                    }

                    if (dayDate.isNotEmpty()) add(AttachmentAk01("Jadwal Hari/Tanggal: $dayDate"))
                    if (time.isNotEmpty()) add(AttachmentAk01("Jadwal Waktu: $time"))
                    if (tuk.isNotEmpty()) add(AttachmentAk01("Lokasi TUK: $tuk"))

                    if (asesiAgreement) {
                        add(AttachmentAk01("Persetujuan Asesi: Telah mendapat penjelasan hak dan prosedur banding - SETUJU"))
                    }
                    if (asesorAgreement) {
                        add(AttachmentAk01("Persetujuan Asesor: Menjaga kerahasiaan hasil asesmen - SETUJU"))
                    }
                    if (confidentialityAgreement) {
                        add(AttachmentAk01("Persetujuan Kerahasiaan Asesi: Mengikuti asesmen dengan pemahaman penggunaan informasi - SETUJU"))
                    }
                }

                if (attachments.isEmpty()) {
                    Toast.makeText(context, "Harap isi setidaknya satu data sebelum submit", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                val submission = AK01Submission(
                    assesmentAsesiId = assesmentAsesiId,
                    attachments = attachments
                )

                onSubmit(submission)
            },
            enabled = !loading && isFormValid(
                evidenceCheckedStates, dayDate, time, tuk,
                asesiAgreement, asesorAgreement, confidentialityAgreement
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                "Simpan & Lanjut",
                fontSize = 14.sp,
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Bold
            )
        }

        // Error message
        if (state == false && message.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "❌ $message",
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                fontFamily = AppFont.Poppins,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun FormStatusIndicator(submission: GetAK01Response?) {
    submission?.let { response ->
        if (response.success && !response.data.isNullOrEmpty()) {
            val data = response.data.first()
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ℹ️",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Column {
                        Text(
                            text = "Form sudah pernah disubmit",
                            fontSize = 12.sp,
                            fontFamily = AppFont.Poppins,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Status: ${data.status} • Tanggal: ${data.submission_date}",
                            fontSize = 10.sp,
                            fontFamily = AppFont.Poppins,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
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
                    text = "Persetujuan Asesmen dan Kerahasiaan berhasil disimpan",
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
private fun BuktiSection(
    checkedStates: MutableMap<Int, Boolean>,
    onCheckedChange: (Int, Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                "Bukti yang akan dikumpulkan:",
                fontSize = 14.sp,
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            val evidenceItems = listOf(
                "Hasil verifikasi Portofolio",
                "Hasil Observasi Langsung",
                "Hasil Pertanyaan Lisan",
                "Hasil review produk",
                "Hasil kegiatan Terstruktur",
                "Hasil Pertanyaan Tertulis",
                "Hasil Pertanyaan wawancara"
            )

            evidenceItems.forEachIndexed { index, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = checkedStates[index] ?: false,
                        onCheckedChange = { isChecked ->
                            onCheckedChange(index, isChecked)
                        }
                    )
                    Text(
                        text = item,
                        fontSize = 12.sp,
                        fontFamily = AppFont.Poppins,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            // Selected count indicator
            val selectedCount = checkedStates.values.count { it }
            if (selectedCount > 0) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "✓ Dipilih: $selectedCount dari ${evidenceItems.size} bukti",
                    fontSize = 11.sp,
                    fontFamily = AppFont.Poppins,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun PelaksanaanAsesmenSection(
    dayDate: String,
    onDayDateChange: (String) -> Unit,
    time: String,
    onTimeChange: (String) -> Unit,
    tuk: String,
    onTukChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                "Pelaksanaan asesmen disepakati pada:",
                fontSize = 14.sp,
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = dayDate,
                onValueChange = onDayDateChange,
                label = { Text("Hari/Tanggal", fontSize = 12.sp) },
                placeholder = { Text("Contoh: Senin, 25 September 2023", fontSize = 11.sp) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = time,
                onValueChange = onTimeChange,
                label = { Text("Waktu", fontSize = 12.sp) },
                placeholder = { Text("Contoh: 09:00 - 12:00 WIB", fontSize = 11.sp) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = tuk,
                onValueChange = onTukChange,
                label = { Text("TUK (Tempat Uji Kompetensi)", fontSize = 12.sp) },
                placeholder = { Text("Contoh: Lab Komputer SMK", fontSize = 11.sp) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun PernyataanSection(
    asesiAgreement: Boolean,
    onAsesiAgreementChange: (Boolean) -> Unit,
    asesorAgreement: Boolean,
    onAsesorAgreementChange: (Boolean) -> Unit,
    confidentialityAgreement: Boolean,
    onConfidentialityAgreementChange: (Boolean) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Pernyataan Persetujuan:",
            fontSize = 14.sp,
            fontFamily = AppFont.Poppins,
            fontWeight = FontWeight.Medium
        )

        // Asesi Agreement 1
        AgreementCard(
            title = "Asesi:",
            content = "Bahwa saya telah mendapatkan penjelasan terkait hak dan prosedur banding asesmen dari asesor.",
            isChecked = asesiAgreement,
            onCheckedChange = onAsesiAgreementChange
        )

        // Asesor Agreement
        AgreementCard(
            title = "Asesor:",
            content = "Menyatakan tidak akan membuka hasil pekerjaan yang peroleh karena penguasaan saya sebagai Asesor dalam pekerjaan Asesmen kepada siapapun atau organisasi manapun selain kepada pihak yang berwenang sehubung dengan kewajiban saya sebagai Asesor yang ditugaskan oleh LSP.",
            isChecked = asesorAgreement,
            onCheckedChange = onAsesorAgreementChange
        )

        // Asesi Agreement 2 (Confidentiality)
        AgreementCard(
            title = "Asesi:",
            content = "Saya setuju mengikuti Asesmen dengan pemahaman bahwa informasi yang dikumpulkan hanya digunakan untuk pengembangan profesional dan hanya dapat diakses oleh orang tertentu saja.",
            isChecked = confidentialityAgreement,
            onCheckedChange = onConfidentialityAgreementChange
        )
    }
}

@Composable
private fun AgreementCard(
    title: String,
    content: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isChecked)
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
            else
                Color.White
        ),
        border = BorderStroke(
            1.dp,
            if (isChecked)
                MaterialTheme.colorScheme.primary
            else
                Color.Gray.copy(alpha = 0.3f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = content,
                fontSize = 12.sp,
                fontFamily = AppFont.Poppins,
                color = Color.Black,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = onCheckedChange
                )
                Text(
                    text = "Saya setuju",
                    fontSize = 12.sp,
                    fontFamily = AppFont.Poppins,
                    fontWeight = if (isChecked) FontWeight.Medium else FontWeight.Normal,
                    color = if (isChecked) MaterialTheme.colorScheme.primary else Color.Black
                )
            }
        }
    }
}

// Helper function untuk validasi form
private fun isFormValid(
    evidenceStates: MutableMap<Int, Boolean>,
    dayDate: String,
    time: String,
    tuk: String,
    asesiAgreement: Boolean,
    asesorAgreement: Boolean,
    confidentialityAgreement: Boolean
): Boolean {
    val hasSelectedEvidence = evidenceStates.values.any { it }
    val hasScheduleInfo = dayDate.isNotEmpty() && time.isNotEmpty() && tuk.isNotEmpty()
    val hasAllAgreements = asesiAgreement && asesorAgreement && confidentialityAgreement

    return hasSelectedEvidence && hasScheduleInfo && hasAllAgreements
}