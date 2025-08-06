package com.example.mylsp.screen.asesor

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
<<<<<<< HEAD
import androidx.compose.material3.Button
=======
>>>>>>> origin/master
import androidx.compose.material3.OutlinedTextField
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
                placeholder = { "Nama Lengkap" },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )
<<<<<<< HEAD

            Button(
                onClick = {
                    navController.navigate("tanda_tangan_asesor")
                }
            ) {
                Text("Kirim Data")
            }
=======
>>>>>>> origin/master
        }
    }
}