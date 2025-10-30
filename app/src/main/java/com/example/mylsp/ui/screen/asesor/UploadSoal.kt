package com.example.mylsp.ui.screen.asesor

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylsp.common.enums.TypeDialog
import com.example.mylsp.data.api.assesment.Answer
import com.example.mylsp.data.api.assesment.PostAnswerRequest
import com.example.mylsp.data.api.assesment.Question
import com.example.mylsp.data.local.assesment.AssesmentAsesiManager
import com.example.mylsp.data.local.user.UserManager
import com.example.mylsp.ui.component.dialog.StatusDialog
import com.example.mylsp.ui.screen.asesi.uriToFile
import com.example.mylsp.util.AppFont
import com.example.mylsp.viewmodel.assesment.QuestionViewModel

@Composable
fun UploadSoal(
    idSkema: Int,
    questionViewModel: QuestionViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val assesmentAsesiManager = AssesmentAsesiManager(context)
    val userManager = UserManager(context)
    val assesmentAsesi = assesmentAsesiManager.getAssesmentAsesi()
    val userRole = userManager.getUserRole()
    val isAsesi = userRole == "asesi" || userRole == "assesi"

    var fileName by remember { mutableStateOf<String?>(null) }
    val listAnswer by questionViewModel.listAnswer.collectAsState()
    val questions by questionViewModel.listQuestion.collectAsState()
    val state by questionViewModel.state.collectAsState()
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showFailedDialog by remember { mutableStateOf(false) }

    LaunchedEffect(state) {
        state?.let {
            if (it) {
                showSuccessDialog = true
            } else {
                showFailedDialog = true
            }
            questionViewModel.clearState()
        }
    }

    val createDocumentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/octet-stream")
    ) { uri ->
        if (uri != null) {
            questionViewModel.downloadQuestionToUri(context, questionViewModel.currentDownloadId, uri)
        }
    }

    val createDocumentAnswerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/octet-stream")
    ) { uri ->
        if (uri != null) {
            questionViewModel.downloadAnswerToUri(context, questionViewModel.currentDownloadId, uri)
        }
    }

    var selectedQuestionId by remember { mutableStateOf<Int?>(null) }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val file = uriToFile(it, context)
            fileName = file.name
            questionViewModel.uploadQuestion(
                request = PostAnswerRequest(
                    question_id = selectedQuestionId ?: 0,
                    assesment_asesi_id = assesmentAsesi?.id ?: 0,
                    files = file
                )
            )
        }
    }

    LaunchedEffect(Unit) {
        questionViewModel.getQuestionBySkema(idSkema)
        questionViewModel.getAnswer()
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {


            // Upload Section
            if(isAsesi){
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Upload Soal",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = AppFont.Poppins,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        if (questions.isNotEmpty()) {
                            questions.forEach { question ->
                                CardUploadSoal(question = question) {
                                    selectedQuestionId = question.id
                                    filePickerLauncher.launch("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                                }
                            }
                        } else {
                            Text(
                                text = "Tidak ada soal tersedia",
                                style = MaterialTheme.typography.bodyMedium,
                                fontFamily = AppFont.Poppins,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }

                        Button(
                            onClick = {},
                            enabled = fileName != null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Simpan Soal",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = AppFont.Poppins
                            )
                        }
                    }
                }
            }


            // Download Soal Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Link Pengunduhan Soal",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = AppFont.Poppins,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    if (questions.isNotEmpty()) {
                        questions.forEach { question ->
                            CardLinkDownloadSoal(
                                question = question,
                                onDownloadClick = { q ->
                                    questionViewModel.currentDownloadId = q.id
                                    createDocumentLauncher.launch(q.file_path ?: "soal.docx")
                                }
                            )
                        }
                    } else {
                        Text(
                            text = "Tidak ada soal untuk diunduh",
                            style = MaterialTheme.typography.bodyMedium,
                            fontFamily = AppFont.Poppins,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }

            // Jawaban Asesi Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Jawaban Asesi",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = AppFont.Poppins,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    if (listAnswer.isNotEmpty()) {
                        val listAnswerAsesi = listAnswer.filter { it.assesment_asesi_id == assesmentAsesi?.id }
                        listAnswerAsesi.forEach { answer ->
                            CardLinkDownloadJawaban(answer = answer) {
                                questionViewModel.currentDownloadId = answer.question_id
                                createDocumentAnswerLauncher.launch(answer.files)
                            }


                        }
                    } else {
                        Text(
                            text = "Belum ada jawaban",
                            style = MaterialTheme.typography.bodyMedium,
                            fontFamily = AppFont.Poppins,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }

    // Dialogs
    if (showSuccessDialog) {
        StatusDialog(
            "Jawaban berhasil dikirim",
            type = TypeDialog.Success,
            onDismiss = { showSuccessDialog = false },
            onClick = { showSuccessDialog = false }
        )
    }

    if (showFailedDialog) {
        StatusDialog(
            "Jawaban gagal dikirim",
            type = TypeDialog.Failed,
            onDismiss = { showFailedDialog = false },
            onClick = { showFailedDialog = false }
        )
    }
}

@Composable
fun CardUploadSoal(question: Question, onClick: () -> Unit) {
    val isUploaded = question.file_path != null

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isUploaded)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = if (isUploaded)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isUploaded) Icons.Default.Check else Icons.Default.AttachFile,
                    contentDescription = if (isUploaded) "File uploaded" else "Attach file",
                    tint = if (isUploaded)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (isUploaded) "File sudah terupload" else "Masukkan file word soal anda",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = if (isUploaded) FontWeight.Medium else FontWeight.Normal,
                    fontFamily = AppFont.Poppins,
                    color = if (isUploaded)
                        MaterialTheme.colorScheme.onPrimary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (isUploaded) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = question.file_path ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Normal,
                        fontFamily = AppFont.Poppins,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

@Composable
fun CardLinkDownloadSoal(
    question: Question,
    onDownloadClick: (Question) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onDownloadClick(question) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Download,
                contentDescription = "Download",
                tint = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier.size(20.dp)
            )

            Text(
                text = question.file_path ?: "Soal",
                style = MaterialTheme.typography.bodyMedium,
                textDecoration = TextDecoration.Underline,
                fontFamily = AppFont.Poppins,
                color = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun CardNilaiJawaban(checked:Boolean, onChecked: (Boolean) -> Unit, onValueChanged:(String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

        }
    }
}

@Composable
fun CardLinkDownloadJawaban(
    answer: Answer,
    onDownloadClick: (Answer) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onDownloadClick(answer) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Download,
                contentDescription = "Download",
                tint = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier.size(20.dp)
            )

            Text(
                text = answer.files,
                style = MaterialTheme.typography.bodyMedium,
                textDecoration = TextDecoration.Underline,
                fontFamily = AppFont.Poppins,
                color = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier.weight(1f)
            )
        }
    }
}