package com.example.mylsp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mylsp.component.BottomPillNav
import com.example.mylsp.component.TopAppBar
import com.example.mylsp.screen.main.ItemBar
import com.example.mylsp.util.UserManager

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val userManager = UserManager(context)
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val startDestination = if (UserManager(context).getUserId() != null){
        if (UserManager(context).getUserRole() == "assesor"){
            Screen.DashboardAsesor.route
        }else{
            Screen.Main.route
        }
    }else{
        Screen.Login.route
    }
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavItems = listOf(
        ItemBar(Icons.Default.QrCode, "Barcode", "barcode"),
        ItemBar(Icons.Default.AccountCircle, "Profil", "profile"),
    )

    var showNavigation by remember { mutableStateOf(false) }
    var showTopBar by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            if (showTopBar) {
                TopAppBar()
            }
        }
    ) { innerPadding ->
        Box(Modifier.fillMaxWidth().padding(innerPadding)){
            SetupNavGraph(
                modifier = Modifier.padding(innerPadding),
                userManager = userManager,
                navController = navController,
                startDestination = startDestination,
                showBottomBar = { check ->
                    showNavigation = check
                },
                showTopBar = { check ->
                    showTopBar = check
                }
            )

            if (showNavigation) {
                Column(
                    modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BottomPillNav(
                        bottomBar = bottomNavItems,
                        orange = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier
                            .padding(bottom = 18.dp),
                        navController = navController
                    )
                }
            }
        }

    }
}




