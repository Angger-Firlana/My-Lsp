package com.example.mylsp.screen.asesor

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylsp.component.HeaderForm
import com.example.mylsp.component.SkemaSertifikasi
import com.example.mylsp.util.AppFont

@Composable
fun FRAK01(modifier: Modifier = Modifier, nextForm:()-> Unit) {
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

        Text(
            "Persetujuan Asesmen ini untuk menjamin bahwa Asesi telah diberi arahan secara rinci tentang perancangan dan proses asesmen.",
            fontSize = 14.sp,
            fontFamily = AppFont.Poppins
        )

        SkemaSertifikasi(
            judulUnit = "Okupasi Junior Custom Made",
            kodeUnit = "SKM.TBS.OJCM/LSP.SMKN24/2023",
            TUK = null,
            namaAsesor = null,
            namaAsesi = null,
            labelTanggalAsesmen = null
        )

        Bukti()

        Spacer(modifier = modifier.height(8.dp))

        PelaksanaanAsesemen()

        Spacer(modifier = modifier.height(8.dp))

        PernyataanAsesiAsesor()
        Button(
            onClick = {
                nextForm()
            }
        ) {
            Text(
                "Lanjut",
                fontSize = 14.sp,
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun Bukti(modifier: Modifier = Modifier) {
    Column {
        Text(
            "Bukti yang akan dikumpulkan:",
            fontSize = 14.sp,
            fontFamily = AppFont.Poppins
        )

        val evidenceItems = listOf(
            "Hasil verifikasi Portofolio",
            "Hasil Observasi Langsung",
            "Hasil Pertanyaan Lisan",
            "Hasil review produk",
            "Hasil kegiatan Terstruktur",
            "Hasil Pertanyaan Tertulis",
            "Hasil Pertanyaan wawancara"
        )

        evidenceItems.forEach { item ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = false, // You can manage state as needed
                    onCheckedChange = { /* Handle checkbox change */ }
                )
                Text(
                    text = item,
                    fontSize = 12.sp,
                    fontFamily = AppFont.Poppins
                )
            }
        }
    }
}

@Composable
fun PelaksanaanAsesemen(modifier: Modifier = Modifier) {
    Column {
        Text(
            "Pelaksanaan asesmen disepakati pada:",
            fontSize = 14.sp,
            fontFamily = AppFont.Poppins
        )

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Column {
                Text(
                    "Hari/Tanggal",
                    fontSize = 12.sp,
                    fontFamily = AppFont.Poppins
                )
                Text(
                    "Waktu",
                    fontSize = 12.sp,
                    fontFamily = AppFont.Poppins
                )
                Text(
                    "TUK",
                    fontSize = 12.sp,
                    fontFamily = AppFont.Poppins
                )
            }

            Spacer(modifier = modifier.width(8.dp))

            Column {
                Text(
                    ":",
                    fontSize = 12.sp,
                    fontFamily = AppFont.Poppins
                )
                Text(
                    ":",
                    fontSize = 12.sp,
                    fontFamily = AppFont.Poppins
                )
                Text(
                    ":",
                    fontSize = 12.sp,
                    fontFamily = AppFont.Poppins
                )
            }
        }

        Spacer(modifier = modifier.height(8.dp))

        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}

@Composable
fun PernyataanAsesiAsesor(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.3f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Asesi:",
                    fontSize = 14.sp,
                    fontFamily = AppFont.Poppins,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Bahwa saya telah mendapatkan penjelasan terkait hak dan prosedur banding asesmen dari asesor.",
                    fontSize = 12.sp,
                    fontFamily = AppFont.Poppins,
                    color = Color.Black,
                    lineHeight = 16.sp
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.3f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Asesor:",
                    fontSize = 14.sp,
                    fontFamily = AppFont.Poppins,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Menyatakan tidak akan membuka hasil pekerjaan yang peroleh karena penguasaan saya sebagai Asesor dalam pekerjaan Asesmen kepada siapapun atau organisasi manapun selain kepada pihak yang berwenang sehubung dengan kewajiban saya sebagai Asesor yang ditugaskan oleh LSP.",
                    fontSize = 12.sp,
                    fontFamily = AppFont.Poppins,
                    color = Color.Black,
                    lineHeight = 16.sp
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.3f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Asesi:",
                    fontSize = 14.sp,
                    fontFamily = AppFont.Poppins,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Saya setuju mengikuti Asesmen dengan pemahaman bahwa informasi yang dikumpulkan hanya digunakan untuk pengembangan profesional dan hanya dapat diakses oleh orang tertentu saja.",
                    fontSize = 12.sp,
                    fontFamily = AppFont.Poppins,
                    color = Color.Black,
                    lineHeight = 16.sp
                )
            }
        }
    }
}