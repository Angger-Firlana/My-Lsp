package com.example.mylsp.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylsp.util.AppFont

@Composable
fun SkemaSertifikasi(
    modifier: Modifier = Modifier,
    judulUnit: String,
    kodeUnit: String,
    TUK: String,
    namaAsesor: String?= null,
    namaAsesi: String?= null,
    tanggalAsesmen: String?= null
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            "Skema Sertfikasi",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = AppFont.Poppins
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column {
                Text("Judul Unit", fontSize = 12.sp, fontFamily = AppFont.Poppins)
                Text("Kode Unit", fontSize = 12.sp, fontFamily = AppFont.Poppins)
                Text("TUK", fontSize = 12.sp, fontFamily = AppFont.Poppins)
                Text("Nama Asesor", fontSize = 12.sp, fontFamily = AppFont.Poppins)
                Text("Nama Asesi", fontSize = 12.sp, fontFamily = AppFont.Poppins)
                Text("Tanggal Asesmen", fontSize = 12.sp, fontFamily = AppFont.Poppins)
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(":", fontSize = 12.sp, fontFamily = AppFont.Poppins)
                Text(":", fontSize = 12.sp, fontFamily = AppFont.Poppins)
                Text(":", fontSize = 12.sp, fontFamily = AppFont.Poppins)
                Text(":", fontSize = 12.sp, fontFamily = AppFont.Poppins)
                Text(":", fontSize = 12.sp, fontFamily = AppFont.Poppins)
                Text(":", fontSize = 12.sp, fontFamily = AppFont.Poppins)
            }
            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(judulUnit, fontSize = 12.sp, fontFamily = AppFont.Poppins)
                Text(kodeUnit, fontSize = 12.sp, fontFamily = AppFont.Poppins)
                Text(TUK, fontSize = 12.sp, fontFamily = AppFont.Poppins)
                Text(namaAsesor ?: "", fontSize = 12.sp, fontFamily = AppFont.Poppins)
                Text(namaAsesi ?: "", fontSize = 12.sp, fontFamily = AppFont.Poppins)
                Text(tanggalAsesmen ?: "", fontSize = 12.sp, fontFamily = AppFont.Poppins)
            }
        }
    }
}