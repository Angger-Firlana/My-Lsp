package com.example.mylsp.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylsp.R
import com.example.mylsp.util.AppFont

@Composable
fun TopAppBar() {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.logoheader),
                contentDescription = "App Logo",
                modifier = Modifier.height(56.dp)
            )

            Text(
                text = "MyLSP",
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 20.sp,
                fontFamily = AppFont.Poppins,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 22.dp)
            )
        }

        HorizontalDivider(
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.padding(
                horizontal = 8.dp
            )
        )
    }
}