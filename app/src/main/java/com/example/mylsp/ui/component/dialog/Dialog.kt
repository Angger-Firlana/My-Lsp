package com.example.mylsp.ui.component.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.mylsp.util.AppFont
import com.example.mylsp.common.enums.TypeDialog

@Composable
fun Dialog(title: String, text: String,onDismiss: ()-> Unit) {

}

@Composable
fun FailedDialog(modifier: Modifier = Modifier) {
    
}

@Composable
fun ValidationDialog(modifier: Modifier = Modifier) {
    
}

@Composable
fun StatusDialog(text: String, type: TypeDialog, onClick:()-> Unit, onDismiss: () -> Unit, modifier: Modifier = Modifier) {
    val isSuccess = type == TypeDialog.Success
    val isFailed = type == TypeDialog.Failed
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                androidx.compose.material3.Icon(
                    imageVector = if(isSuccess)Icons.Default.CheckCircle else Icons.Default.Error,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = Color(0xFF10B981)
                )

                Spacer(Modifier.height(16.dp))

                androidx.compose.material3.Text(
                    if(isSuccess) "Berhasil" else "Gagal",
                    fontFamily = AppFont.Poppins,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111827)
                )

                Spacer(Modifier.height(8.dp))

                androidx.compose.material3.Text(
                    text,
                    fontFamily = AppFont.Poppins,
                    fontSize = 14.sp,
                    color = Color(0xFF6B7280),
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(24.dp))


                androidx.compose.material3.Button(
                    onClick = onClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = if(isSuccess) androidx.compose.material3.ButtonDefaults.buttonColors(Color(0xFF10B981)) else ButtonDefaults.buttonColors(Color(0xFFEF4444))
                ) {
                    androidx.compose.material3.Text(
                        if(isSuccess) "Lanjutkan" else "Kembali",
                        fontFamily = AppFont.Poppins,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}