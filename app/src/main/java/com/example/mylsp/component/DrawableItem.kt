package com.example.mylsp.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mylsp.screen.main.ItemBar
import com.example.mylsp.util.AppFont

@Composable
fun DrawableItem(navController: NavController,item: ItemBar, isExpanded: Boolean) {
    Card(
        onClick = {
            navController.navigate(item.route)
        }

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (isExpanded) Arrangement.Start else Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Icon(
                item.icon,
                contentDescription = item.title,
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )

            if (isExpanded) {
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    item.title,
                    fontFamily = AppFont.Poppins,
                    fontSize = 14.sp,
                    color = Color.White,
                    modifier = Modifier.animateContentSize()
                )
            }
        }
    }

}