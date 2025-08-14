package com.example.mylsp.screen.asesi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mylsp.component.HeaderForm
import com.example.mylsp.component.LoadingScreen
import com.example.mylsp.screen.BarcodeScannerScreen
import com.example.mylsp.util.AppFont
import com.example.mylsp.util.RequestCameraPermission
import kotlinx.coroutines.launch

@Composable
fun AsesiBarcodeScanner(modifier: Modifier = Modifier, navController: NavController) {
    var code by remember { mutableStateOf<String?>(null) }

    if(code == null){
        RequestCameraPermission {
            BarcodeScannerScreen(navController = navController, onScanned = {
                code = it
            })
        }
    }
    else{
        Box(modifier = Modifier.fillMaxSize()){
            Column(
                modifier = modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeaderForm(
                    title = "Tampilan Barcode"
                )

                Text(
                    "Hasil Scan: $code",
                    fontFamily = AppFont.Poppins
                )
            }
        }
    }



}