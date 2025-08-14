package com.example.mylsp.screen.asesi

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mylsp.model.api.AsesiRequest
import com.example.mylsp.util.AppFont
import com.example.mylsp.viewmodel.AsesiViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

data class FileUpload(
    val name: String,
    val description: String,
    var file: MultipartBody.Part? = null,
    var fileName: String? = null
)

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF1A1A1A),
        modifier = Modifier.padding(bottom = 16.dp, top = 8.dp)
    )
}

@Composable
fun AsesiFormScreen(
    viewModel: AsesiViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    val message by viewModel.message.collectAsState()

    var namaLengkap by remember { mutableStateOf("Budi Santoso") }
    var nik by remember { mutableStateOf("3201010101010001") }
    var tanggalLahir by remember { mutableStateOf("1998-05-05") }
    var tempatLahir by remember { mutableStateOf("Jakarta") }
    var jenisKelamin by remember { mutableStateOf("Laki-laki") }
    var kebangsaan by remember { mutableStateOf("Indonesia") }
    var alamatRumah by remember { mutableStateOf("Jl. Melati No. 10, Jakarta Barat") }
    var kodePos by remember { mutableStateOf("11510") }
    var noTeleponRumah by remember { mutableStateOf("0215551234") }
    var noTeleponKantor by remember { mutableStateOf("0215551234") }
    var noTelepon by remember { mutableStateOf("081234567890") }
    var email by remember { mutableStateOf("budi.santoso@example.com") }
    var kualifikasiPendidikan by remember { mutableStateOf("S1 Teknik Informatika") }
    var namaInstitusi by remember { mutableStateOf("Universitas Indonesia") }
    var jabatan by remember { mutableStateOf("Software Engineer") }
    var alamatKantor by remember { mutableStateOf("Jl. Sudirman No. 25, Jakarta Pusat") }
    var kodePosKantor by remember { mutableStateOf("10210") }
    var faxKantor by remember { mutableStateOf("0215554321") }
    var emailKantor by remember { mutableStateOf("budi.santoso@kantor.com") }


    var fileUploads by remember {
        mutableStateOf(
            listOf(
                FileUpload("Kartu Pelajar", "Upload kartu pelajar yang masih berlaku"),
                FileUpload("Kartu Keluarga/KTP", "Upload kartu keluarga atau KTP"),
                FileUpload("Pas Foto 3x4", "Pas foto 3x4 berwarna merah 2 lembar"),
                FileUpload("Raport SMK", "Raport SMK semester 1 s.d. 5"),
                FileUpload("Sertifikat PKL", "Sertifikat Praktek Kerja Lapangan")
            )
        )
    }

    var currentUploadIndex by remember { mutableStateOf(-1) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            if (currentUploadIndex >= 0) {
                val file = uriToFile(it, context)
                val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                val part = MultipartBody.Part.createFormData("${fileUploads[currentUploadIndex].name.lowercase().replace(" ", "_")}[]", file.name, requestFile)

                fileUploads = fileUploads.toMutableList().apply {
                    this[currentUploadIndex] = this[currentUploadIndex].copy(
                        file = part,
                        fileName = file.name
                    )
                }
            }
        }
    }

    LaunchedEffect(state) {
        state?.let { success ->
            if(success){
                navController.navigate("main")
            }else{
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
            viewModel.resetState()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF8F9FA),
                        Color(0xFFE3F2FD)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            // Header
            Text(
                text = "Lengkapi Identitas Anda",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            )

            // Personal Information Section
            SectionHeader("Data Pribadi")

            ModernTextField(
                value = namaLengkap,
                onValueChange = { namaLengkap = it },
                placeholder = "Nama Lengkap",
                label = "Nama Lengkap"
            )

            Spacer(modifier = Modifier.height(16.dp))

            ModernTextField(
                value = nik,
                onValueChange = { nik = it },
                placeholder = "NIK",
                label = "NIK"
            )

            Spacer(modifier = Modifier.height(16.dp))

            ModernTextField(
                value = tanggalLahir,
                onValueChange = { tanggalLahir = it },
                placeholder = "16 Februari 2008",
                label = "Tanggal Lahir"
            )

            Spacer(modifier = Modifier.height(16.dp))

            ModernTextField(
                value = tempatLahir,
                onValueChange = { tempatLahir = it },
                placeholder = "Tempat Lahir",
                label = "Tempat Lahir"
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Gender Selection
            Text(
                text = "Jenis Kelamin",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1A1A1A),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                GenderOption(
                    text = "Laki-laki",
                    selected = jenisKelamin == "Laki-laki",
                    onSelect = { jenisKelamin = "Laki-laki" }
                )
                GenderOption(
                    text = "Perempuan",
                    selected = jenisKelamin == "Perempuan",
                    onSelect = { jenisKelamin = "Perempuan" }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            ModernTextField(
                value = kebangsaan,
                onValueChange = { kebangsaan = it },
                placeholder = "Kebangsaan",
                label = "Kebangsaan"
            )

            Spacer(modifier = Modifier.height(16.dp))

            ModernTextField(
                value = alamatRumah,
                onValueChange = { alamatRumah = it },
                placeholder = "Alamat lengkap tempat tinggal",
                label = "Alamat Rumah",
                minLines = 3
            )

            Spacer(modifier = Modifier.height(16.dp))

            ModernTextField(
                value = kodePos,
                onValueChange = { kodePos = it },
                placeholder = "Kode Pos",
                label = "Kode Pos"
            )

            Spacer(modifier = Modifier.height(16.dp))

            ModernTextField(
                value = noTeleponRumah,
                onValueChange = { noTeleponRumah = it },
                placeholder = "No Telepon Rumah",
                label = "No Telepon Rumah"
            )

            Spacer(modifier = Modifier.height(16.dp))

            ModernTextField(
                value = noTelepon,
                onValueChange = { noTelepon = it },
                placeholder = "No Telepon/HP",
                label = "No Telepon"
            )

            Spacer(modifier = Modifier.height(16.dp))

            ModernTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "Email",
                label = "Email"
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Education Section
            SectionHeader("Data Pendidikan")

            ModernTextField(
                value = kualifikasiPendidikan,
                onValueChange = { kualifikasiPendidikan = it },
                placeholder = "Kualifikasi Pendidikan",
                label = "Kualifikasi Pendidikan"
            )

            Spacer(modifier = Modifier.height(16.dp))

            ModernTextField(
                value = namaInstitusi,
                onValueChange = { namaInstitusi = it },
                placeholder = "Nama Institusi/Sekolah",
                label = "Nama Institusi"
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Work Information Section
            SectionHeader("Data Pekerjaan")

            ModernTextField(
                value = jabatan,
                onValueChange = { jabatan = it },
                placeholder = "Jabatan",
                label = "Jabatan"
            )

            Spacer(modifier = Modifier.height(16.dp))

            ModernTextField(
                value = alamatKantor,
                onValueChange = { alamatKantor = it },
                placeholder = "Alamat Kantor",
                label = "Alamat Kantor",
                minLines = 2
            )

            Spacer(modifier = Modifier.height(16.dp))

            ModernTextField(
                value = kodePosKantor,
                onValueChange = { kodePosKantor = it },
                placeholder = "Kode Pos Kantor",
                label = "Kode Pos Kantor"
            )

            Spacer(modifier = Modifier.height(16.dp))

            ModernTextField(
                value = faxKantor,
                onValueChange = { faxKantor = it },
                placeholder = "Fax Kantor",
                label = "Fax Kantor"
            )

            Spacer(modifier = Modifier.height(16.dp))

            ModernTextField(
                value = emailKantor,
                onValueChange = { emailKantor = it },
                placeholder = "Email Kantor",
                label = "Email Kantor"
            )

            Spacer(modifier = Modifier.height(32.dp))

            // File Upload Section
            Text(
                text = "Dokumen Pendukung",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1A1A1A),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            fileUploads.forEachIndexed { index, fileUpload ->
                FileUploadCard(
                    fileUpload = fileUpload,
                    onClick = {
                        currentUploadIndex = index
                        launcher.launch("*/*")
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Submit Button
            Button(
                onClick = {
                    val attachments = fileUploads.mapNotNull { it.file }
                    val request = AsesiRequest(
                        nama_lengkap = namaLengkap,
                        nik = nik,
                        tgl_lahir = tanggalLahir,
                        tempat_lahir = tempatLahir,
                        jenis_kelamin = jenisKelamin,
                        kebangsaan = kebangsaan,
                        alamat_rumah = alamatRumah,
                        kode_pos = kodePos,
                        no_telepon_rumah = noTeleponRumah,
                        no_telepon_kantor = noTeleponKantor,
                        no_telepon = noTelepon,
                        email = email,
                        kualifikasi_pendidikan = kualifikasiPendidikan,
                        nama_institusi = namaInstitusi,
                        jabatan = jabatan,
                        alamat_kantor = alamatKantor,
                        kode_pos_kantor = kodePosKantor,
                        fax_kantor = faxKantor,
                        email_kantor = emailKantor,
                        status = "pending",
                        attachments = attachments
                    )
                    viewModel.createDataAsesi(request)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "🔗 Simpan data",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            message.takeIf { it.isNotEmpty() }?.let {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (state == true) Color(0xFFE8F5E8) else Color(0xFFFFEBEE)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = it,
                        color = if (state == true) Color(0xFF2E7D32) else Color(0xFFC62828),
                        modifier = Modifier.padding(16.dp),
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }

        // Decorative elements (bottom wave)
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(200.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFFF9800).copy(alpha = 0.3f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    label: String? = null,
    minLines: Int = 1
) {
    Column {
        label?.let {
            Text(
                text = it,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1A1A1A),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    placeholder,
                    color = Color(0xFF9E9E9E),
                    fontSize = 14.sp
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF2196F3),
                unfocusedBorderColor = Color(0xFFE0E0E0),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp),
            minLines = minLines,
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 14.sp,
                color = Color(0xFF1A1A1A)
            )
        )
    }
}

@Composable
fun GenderOption(
    text: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onSelect() }
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(
                selectedColor = Color(0xFF2196F3)
            )
        )
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color(0xFF1A1A1A),
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun FileUploadCard(
    fileUpload: FileUpload,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        if (fileUpload.file != null) Color(0xFFE8F5E8) else Color(0xFFF5F5F5),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (fileUpload.file != null) Icons.Default.Check else Icons.Default.AttachFile,
                    contentDescription = null,
                    tint = if (fileUpload.file != null) Color(0xFF4CAF50) else Color(0xFF757575),
                    modifier = Modifier.size(20.dp)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = fileUpload.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF1A1A1A)
                )
                Text(
                    text = fileUpload.fileName ?: fileUpload.description,
                    fontSize = 12.sp,
                    color = if (fileUpload.file != null) Color(0xFF4CAF50) else Color(0xFF757575),
                    maxLines = 2
                )
            }

            if (fileUpload.file != null) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove file",
                    tint = Color(0xFF757575),
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            // Handle file removal
                        }
                )
            }
        }
    }
}

// Utility functions (keep existing ones)
fun uriToFile(uri: Uri, context: Context): File {
    val inputStream = context.contentResolver.openInputStream(uri)
    val file = File(context.cacheDir, context.contentResolver.getFileName(uri))
    val outputStream = FileOutputStream(file)
    inputStream?.copyTo(outputStream)
    inputStream?.close()
    outputStream.close()
    return file
}

fun ContentResolver.getFileName(uri: Uri): String {
    var name = ""
    val cursor = query(uri, null, null, null, null)
    cursor?.use {
        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (it.moveToFirst()) {
            name = it.getString(nameIndex)
        }
    }
    return name
}

