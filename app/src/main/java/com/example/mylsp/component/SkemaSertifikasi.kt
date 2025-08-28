package com.example.mylsp.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
    judulUnit: String? = null,
    kodeUnit: String? = null,
    TUK: String? = null,
    namaAsesor: String? = null,
    namaAsesi: String? = null,
    tanggalAsesmen: String? = null,

    // label kolom pertama dibuat opsional
    labelJudulUnit: String? = "Judul Unit",
    labelKodeUnit: String? = "Kode Unit",
    labelTUK: String? = "TUK",
    labelNamaAsesor: String? = "Nama Asesor",
    labelNamaAsesi: String? = "Nama Asesi",
    labelTanggalAsesmen: String? = "Tanggal Asesmen"
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
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
                labelJudulUnit?.let { Text(it, fontSize = 12.sp, fontFamily = AppFont.Poppins) }
                labelKodeUnit?.let { Text(it, fontSize = 12.sp, fontFamily = AppFont.Poppins) }
                labelTUK?.let { Text(it, fontSize = 12.sp, fontFamily = AppFont.Poppins) }
                labelNamaAsesor?.let { Text(it, fontSize = 12.sp, fontFamily = AppFont.Poppins) }
                labelNamaAsesi?.let { Text(it, fontSize = 12.sp, fontFamily = AppFont.Poppins) }
                labelTanggalAsesmen?.let { Text(it, fontSize = 12.sp, fontFamily = AppFont.Poppins) }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                labelJudulUnit?.let { Text(":", fontSize = 12.sp, fontFamily = AppFont.Poppins) }
                labelKodeUnit?.let { Text(":", fontSize = 12.sp, fontFamily = AppFont.Poppins) }
                labelTUK?.let { Text(":", fontSize = 12.sp, fontFamily = AppFont.Poppins) }
                labelNamaAsesor?.let { Text(":", fontSize = 12.sp, fontFamily = AppFont.Poppins) }
                labelNamaAsesi?.let { Text(":", fontSize = 12.sp, fontFamily = AppFont.Poppins) }
                labelTanggalAsesmen?.let { Text(":", fontSize = 12.sp, fontFamily = AppFont.Poppins) }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                judulUnit?.let { Text(it, fontSize = 12.sp, fontFamily = AppFont.Poppins) }
                kodeUnit?.let { Text(it, fontSize = 12.sp, fontFamily = AppFont.Poppins) }
                TUK?.let { Text(it, fontSize = 12.sp, fontFamily = AppFont.Poppins) }
                namaAsesor?.let { Text(it, fontSize = 12.sp, fontFamily = AppFont.Poppins) }
                namaAsesi?.let { Text(it, fontSize = 12.sp, fontFamily = AppFont.Poppins) }
                tanggalAsesmen?.let { Text(it, fontSize = 12.sp, fontFamily = AppFont.Poppins) }
            }
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}
