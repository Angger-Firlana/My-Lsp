package com.example.mylsp.navigation

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.SupervisedUserCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mylsp.R
import com.example.mylsp.screen.asesi.APL02
import com.example.mylsp.screen.asesi.AsesiFormScreen
import com.example.mylsp.screen.asesi.DetailUSK
import com.example.mylsp.screen.asesor.KelengkapanDataAsesor
import com.example.mylsp.screen.asesor.SignatureScreen
import com.example.mylsp.screen.asesor.SkemaListScreen
import com.example.mylsp.screen.auth.LoginScreen
import com.example.mylsp.screen.auth.RegisterScreen
import com.example.mylsp.screen.main.ItemBar
import com.example.mylsp.screen.main.MainScreen
import com.example.mylsp.screen.main.WaitingApprovalScreen
import com.example.mylsp.viewmodel.AsesiViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavItems = listOf(
        ItemBar(Icons.Default.Dashboard, "Dashboard", "main"),
        ItemBar(Icons.AutoMirrored.Filled.ViewList, "List Skema", "skemaList"),
        ItemBar(Icons.Default.AccountCircle, "Profil", "apl_01")
    )

    val asesiViewModel: AsesiViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(LocalContext.current.applicationContext as Application)
    )

    val routesWithNavigation = listOf("main", "skemaList", "profil")
    val showNavigation = currentRoute in routesWithNavigation

    Scaffold(
        topBar = {
            if (showNavigation) {
                TopAppBar()
            }
        },
        bottomBar = {
            if (showNavigation) {
                BottomNavigationBar(
                    navController = navController,
                    items = bottomNavItems,
                    currentRoute = currentRoute
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login") {
                LoginScreen(navController = navController)
            }
            composable("register") {
                RegisterScreen(navController = navController)
            }
            composable("kelengkapanDataAsesor") {
                KelengkapanDataAsesor(navController = navController)
            }
            composable("tanda_tangan_asesor") {
                SignatureScreen(context, navController)
            }
            composable("skemaList") {
                SkemaListScreen(modifier = Modifier, navController = navController)
            }
            composable("detailusk") {
                DetailUSK(navController = navController)
            }
            composable("apl_01") {
                AsesiFormScreen(asesiViewModel,navController = navController)
            }
            composable("apl_02") {
                APL02(navController = navController)
            }
            composable("waiting_approval") {
                WaitingApprovalScreen(modifier = Modifier, navController)
            }
            composable("main") {
                MainScreen(modifier = Modifier, navController = navController)
            }
        }
    }
}

@Composable
private fun TopAppBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(84.dp)
            .background(MaterialTheme.colorScheme.tertiary)
            .statusBarsPadding()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "App Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
        )

        Text(
            text = "MyLSP",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )

        IconButton(
            onClick = {
                // Handle menu action
            }
        ) {
            Icon(
                Icons.Default.Menu,
                contentDescription = "Menu",
                tint = Color.White
            )
        }
    }
}

@Composable
private fun BottomNavigationBar(
    navController: NavHostController,
    items: List<ItemBar>,
    currentRoute: String?
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 12.sp
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}