package com.example.mylsp.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mylsp.screen.main.ItemBar
import com.example.mylsp.util.user.UserManager

@Composable
fun BottomPillNav(bottomBar: List<ItemBar>, orange: Color, modifier: Modifier = Modifier, navController: NavController) {
    val context = LocalContext.current

    Box(
        modifier = modifier.fillMaxWidth().height(54.dp).padding(horizontal = 22.dp),
        contentAlignment = Alignment.Center
    ) {
        // Background surface
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = orange,
            shape = RoundedCornerShape(24.dp),
            shadowElevation = 6.dp
        ) {
            Row(
                modifier = Modifier.fillMaxSize().padding(horizontal = 22.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Home/Apps icon
                NavIcon(
                    modifier = Modifier,
                    icon = Icons.Default.Apps,
                    onClick = {
                        if(UserManager(context).getUserRole() == "asesor"){
                            navController.navigate("dashboardAsesor")
                        } else {
                            navController.navigate("main")
                        }
                    }
                )

                // Other navigation items (excluding barcode)
                bottomBar.forEach { item ->
                    if (item.route != "barcode") {
                        NavIcon(
                            Modifier,
                            icon = item.icon,
                            onClick = { navController.navigate(item.route) }
                        )
                    }
                }
            }
        }

        // Prominent barcode button positioned above the surface
        bottomBar.find { it.route == "barcode" }?.let { barcodeItem ->
            Surface(
                modifier = Modifier
                    .size(64.dp)
                    .offset(y = (-10).dp), // Posisi sedikit ke atas dari surface
                color = orange,
                shape = CircleShape,
                shadowElevation = 8.dp,
                border = BorderStroke(3.dp, Color.White) // Border putih untuk kontras
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { navController.navigate(barcodeItem.route) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = barcodeItem.icon,
                        contentDescription = "Barcode Scanner",
                        modifier = Modifier.size(32.dp),
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun NavIcon(modifier: Modifier,icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    IconButton(
        onClick = {
            onClick()
        }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(22.dp)
        )
    }

}
