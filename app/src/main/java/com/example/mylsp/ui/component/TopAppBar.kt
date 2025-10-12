package com.example.mylsp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylsp.R
import com.example.mylsp.util.AppFont

@Composable
fun TopAppBar() {
    Column(
        modifier = Modifier.statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.logoheader),
                contentDescription = "App Logo",
                modifier = Modifier.height(32.dp), // Ukuran logo yang lebih reasonable
                contentScale = ContentScale.Fit // Pastikan logo proporsional
            )

            Text(
                text = "MyLSP",
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 20.sp,
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 12.dp) // Spacing yang lebih reasonable
            )
        }

        HorizontalDivider(
            thickness = 1.dp, // Thickness yang lebih subtle
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), // Warna divider yang lebih soft
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}