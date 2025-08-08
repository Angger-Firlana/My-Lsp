package com.example.mylsp.screen.asesi

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mylsp.R
import com.example.mylsp.util.AppFont

@Composable
fun APL01(modifier: Modifier = Modifier, navController: NavController) {
    var namaLengkap by remember { mutableStateOf("") }
    var tanggalLahir by remember { mutableStateOf("") }
    var kelas by remember { mutableStateOf("") }
    var jurusan by remember { mutableStateOf("") }
    var kebangsaan by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }
    var jenkel by remember { mutableStateOf("") }
    var judulSkema by remember { mutableStateOf("") }
    var nomorSkema by remember { mutableStateOf("") }
    var tujuanAssesment by remember { mutableStateOf("") }

    var fotoKartu by remember { mutableStateOf<Uri?>(null) }
    var fotoDiri by remember { mutableStateOf<Uri?>(null) }
    var fotoRapot by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var suratPKL by remember { mutableStateOf<Uri?>(null) }

    val kartuPicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        fotoKartu = uri
    }
    val fotoDiriPicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        fotoDiri = uri
    }
    val rapotPicker = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
        if (uris.isNotEmpty()) fotoRapot = uris
    }
    val suratPicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        suratPKL = uri
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Lengkapi identitas anda",
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )

            Spacer(Modifier.height(20.dp))

            // ====== Field Text ======
            FieldLabel("Nama Lengkap")
            OutlinedTextField(
                value = namaLengkap,
                onValueChange = { namaLengkap = it },
                placeholder = { FieldPlaceholder("Nama Lengkap") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            FieldLabel("Tanggal Lahir")
            OutlinedTextField(
                value = tanggalLahir,
                onValueChange = { tanggalLahir = it },
                placeholder = { FieldPlaceholder("dd/mm/yyyy") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            FieldLabel("Kelas")
            OutlinedTextField(
                value = kelas,
                onValueChange = { kelas = it },
                placeholder = { FieldPlaceholder("Kelas") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            FieldLabel("Jurusan")
            OutlinedTextField(
                value = jurusan,
                onValueChange = { jurusan = it },
                placeholder = { FieldPlaceholder("Jurusan") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            FieldLabel("Kebangsaan")
            OutlinedTextField(
                value = kebangsaan,
                onValueChange = { kebangsaan = it },
                placeholder = { FieldPlaceholder("Kebangsaan") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            FieldLabel("Alamat")
            OutlinedTextField(
                value = alamat,
                onValueChange = { alamat = it },
                placeholder = { FieldPlaceholder("Alamat") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                shape = RoundedCornerShape(16.dp)
            )

            FieldLabel("Jenis Kelamin")
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = jenkel == "pria",
                    onClick = { jenkel = "pria" }
                )
                Text("Pria", fontFamily = AppFont.Poppins)
                Spacer(Modifier.width(16.dp))
                RadioButton(
                    selected = jenkel == "wanita",
                    onClick = { jenkel = "wanita" }
                )
                Text("Wanita", fontFamily = AppFont.Poppins)
            }

            // ====== Upload File ======
            FieldLabel("Foto Kartu Identitas")
            Button(onClick = { kartuPicker.launch("image/*") }) {
                Text(fotoKartu?.lastPathSegment ?: "Pilih Foto Kartu Identitas")
            }

            FieldLabel("Foto Diri")
            Button(onClick = { fotoDiriPicker.launch("image/*") }) {
                Text(fotoDiri?.lastPathSegment ?: "Pilih Foto Diri")
            }

            FieldLabel("Foto Rapot Semester 1-5")
            Button(onClick = { rapotPicker.launch("image/*") }) {
                Text(
                    if (fotoRapot.isEmpty()) "Pilih Foto Rapot (multi)"
                    else "Sudah pilih ${fotoRapot.size} file"
                )
            }

            FieldLabel("Surat Keterangan PKL")
            Button(onClick = { suratPicker.launch("*/*") }) {
                Text(suratPKL?.lastPathSegment ?: "Pilih File Surat PKL")
            }

            // ====== Lanjut Field Text ======
            FieldLabel("Judul Skema Sertifikasi")
            OutlinedTextField(
                value = judulSkema,
                onValueChange = { judulSkema = it },
                placeholder = { FieldPlaceholder("Judul Skema Sertifikasi") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            FieldLabel("Nomor Skema Sertifikasi")
            OutlinedTextField(
                value = nomorSkema,
                onValueChange = { nomorSkema = it },
                placeholder = { FieldPlaceholder("Nomor Skema Sertifikasi") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            FieldLabel("Tujuan Assessment")
            Column(modifier = Modifier.fillMaxWidth()) {
                listOf(
                    "sertifikasi" to "Sertifikasi",
                    "pkt" to "Pengakuan Kompetensi Terkini (PKT)",
                    "rpl" to "Rekognisi Pembelajaran Lampau",
                    "lainnya" to "Lainnya"
                ).forEach { (value, label) ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = tujuanAssesment == value,
                            onClick = { tujuanAssesment = value }
                        )
                        Text(label, fontFamily = AppFont.Poppins)
                    }
                }
            }

            Spacer(Modifier.height(30.dp))

            Button(
                onClick = { navController.navigate("") },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Simpan Data", fontFamily = AppFont.Poppins)
            }
        }
    }
}


@Composable
private fun FieldLabel(text: String) {
    Text(
        text,
        fontFamily = AppFont.Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp)
    )
}

@Composable
private fun FieldPlaceholder(text: String) {
    Text(
        text,
        fontFamily = AppFont.Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )
}