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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mylsp.util.AppFont
import com.example.mylsp.util.assesment.AssesmentAsesiManager
import com.example.mylsp.viewmodel.AssesmentViewModel

@Composable
fun SkemaSertifikasi(
    modifier: Modifier = Modifier,

    // label kolom pertama dibuat opsional
    labelJudulUnit: String? = "Judul Unit",
    labelKodeUnit: String? = "Kode Unit",
    labelTUK: String? = "TUK",
    labelNamaAsesor: String? = "Nama Asesor",
    labelNamaAsesi: String? = "Nama Asesi",
    labelTanggalAsesmen: String? = "Tanggal Asesmen"
) {
    val context = LocalContext.current
    val assesmentAsesiManager = AssesmentAsesiManager(context)
    val assesmentViewModel:AssesmentViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as android.app.Application)
    )
    val assesment by assesmentViewModel.assesment.collectAsState()
    var skema = assesment?.schema
    val assesmentAsesi = assesmentAsesiManager.getAssesmentAsesi()
    LaunchedEffect(assesmentAsesi) {
        assesmentViewModel.getAssesmentById(assesmentAsesi?.assesment_id?: 0)
    }
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
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp, bottom = 16.dp)
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
                Text(skema?.judul_skema?: "unknown judul schema", fontSize = 12.sp, fontFamily = AppFont.Poppins)
                Text(skema?.nomor_skema?: "nomor skema", fontSize = 12.sp, fontFamily = AppFont.Poppins)
                Text(assesment?.tuk?: "", fontSize = 12.sp, fontFamily = AppFont.Poppins)
                 Text(assesment?.assesor?.nama_lengkap?: "", fontSize = 12.sp, fontFamily = AppFont.Poppins)
                Text(assesmentAsesi?.asesi?.nama_lengkap?: "", fontSize = 12.sp, fontFamily = AppFont.Poppins)
               Text(assesment?.tanggal_assesment?: "", fontSize = 12.sp, fontFamily = AppFont.Poppins)
            }

        }

        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

    }
}

