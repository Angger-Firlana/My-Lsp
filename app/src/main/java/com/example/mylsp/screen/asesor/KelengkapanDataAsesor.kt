package com.example.mylsp.screen.asesor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mylsp.R

@Composable
fun KelengkapanDataAsesor(modifier: Modifier = Modifier, navController: NavController) {
    var namaLengkap by remember { mutableStateOf("") }
    var tanggalLahir by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }
    var jenkel by remember { mutableStateOf("") }
    var pekerjaan by remember { mutableStateOf("") }
    var ktp by remember { mutableStateOf("") }
    var ijazahTerakhir by remember { mutableStateOf("") }
    var sertifikatKompetensi by remember { mutableStateOf("") }
    var foto by remember { mutableStateOf("") }

    Box(modifier = Modifier) {
        Image(
            painter = painterResource(R.drawable.img,),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Lengkapi identitas anda"
            )

            Spacer(Modifier.height(50.dp))

            Text(
                "Nama Lengkap",
                Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = namaLengkap,
                onValueChange = {
                    namaLengkap = it
                },
                placeholder = { Text("Nama Lengkap") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            Text(
                "Tanggal Lahir",
                Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = tanggalLahir,
                onValueChange = {
                    tanggalLahir = it
                },
                placeholder = { Text("Tanggal Lahir") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            Text(
                "Alamat",
                Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = alamat,
                onValueChange = {
                    alamat = it
                },
                placeholder = { "Nama Lengkap" },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                shape = RoundedCornerShape(16.dp)
            )

            Text(
                "Jenis Kelamin",
                Modifier.fillMaxWidth()
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                RadioButton(
                    selected = jenkel == "pria",
                    onClick = { jenkel = "pria" }
                )
                Text("Pria")

                RadioButton(
                    selected = jenkel == "wanita",
                    onClick = { jenkel = "wanita" }
                )
                Text("Wanita")
            }

            Text(
                "Pekerjaan",
                Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = pekerjaan,
                onValueChange = {
                    pekerjaan = it
                },
                placeholder = { Text("Pekerjaan") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            Text(
                "KTP",
                Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = ktp,
                onValueChange = {
                    ktp = it
                },
                placeholder = { Text("Masukkan foto KTP anda") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            Text(
                "Ijazah Terakhir",
                Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = ijazahTerakhir,
                onValueChange = {
                    ijazahTerakhir = it
                },
                placeholder = { Text("Masukkan foto ijazah terakhir anda") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )
            Text(
                "Sertifikat Kompetensi Keahlian",
                Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = sertifikatKompetensi,
                onValueChange = {
                    sertifikatKompetensi = it
                },
                placeholder = { Text("Masukkan foto sertifikat kompetensi keahlian anda") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )


            Text(
                "Foto 3x4",
                Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = foto,
                onValueChange = {
                    foto = it
                },
                placeholder = { Text("Masukkan foto 3x4 anda") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(Modifier.height(50.dp))

            Button(
                onClick = {
                    navController.navigate("event")
                },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.onBackground)
            ) {
                Row {
                    Text("Simpan Data")
                }
            }
        }
    }
}