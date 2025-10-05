package com.example.mylsp.navigation

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mylsp.model.api.Apl01
import com.example.mylsp.screen.BarcodeScreen
import com.example.mylsp.screen.CongratsScreen
import com.example.mylsp.screen.ProfileScreen
import com.example.mylsp.screen.APL02
import com.example.mylsp.screen.asesi.AsesiBarcodeScanner
import com.example.mylsp.screen.asesi.AsesiFormScreen
import com.example.mylsp.screen.asesi.DetailAssesment
import com.example.mylsp.screen.asesi.ak.FRAK03
import com.example.mylsp.screen.asesor.ak.FRAK01
import com.example.mylsp.screen.asesi.ak.FRAK04
import com.example.mylsp.screen.asesor.ak.FRAK05
import com.example.mylsp.screen.asesi.FRIA06A
import com.example.mylsp.screen.asesi.ListFormScreen
import com.example.mylsp.screen.asesi.WaitingAK01Screen
import com.example.mylsp.screen.asesor.approve.ApprovedUnapprovedScreen
import com.example.mylsp.screen.asesor.DashboardAsesor
import com.example.mylsp.screen.asesor.event.DetailEvent
import com.example.mylsp.screen.asesor.event.Events
import com.example.mylsp.screen.asesor.ia.FRIA01
import com.example.mylsp.screen.asesor.KelengkapanDataAsesor
import com.example.mylsp.screen.asesor.AssesmentListScreen
import com.example.mylsp.screen.asesor.ak.FRAK02
import com.example.mylsp.screen.asesor.ia.FRIA02
import com.example.mylsp.screen.asesor.ia.FRIA03
import com.example.mylsp.screen.asesor.ia.FRIA06C
import com.example.mylsp.screen.auth.LoginScreen
import com.example.mylsp.screen.auth.RegisterScreen
import com.example.mylsp.screen.main.MainScreen
import com.example.mylsp.screen.waiting_approval.WaitingApprovalScreen
import com.example.mylsp.util.assesment.AssessmentManager
import com.example.mylsp.util.user.AsesiManager
import com.example.mylsp.util.user.UserManager
import com.example.mylsp.viewmodel.AK01ViewModel
import com.example.mylsp.viewmodel.APL01ViewModel
import com.example.mylsp.viewmodel.APL02ViewModel
import com.example.mylsp.viewmodel.AsesiViewModel
import com.example.mylsp.viewmodel.AssesmentViewModel
import com.example.mylsp.viewmodel.SkemaViewModel
import com.example.mylsp.viewmodel.UserViewModel
import com.example.mylsp.viewmodel.assesment.AK02ViewModel
import com.example.mylsp.viewmodel.assesment.AK03ViewModel
import com.example.mylsp.viewmodel.assesment.AK05ViewModel
import com.example.mylsp.viewmodel.assesment.Ak04ViewModel
import com.example.mylsp.viewmodel.assesment.AssesmentAsesiViewModel
import com.example.mylsp.viewmodel.assesment.IA01ViewModel
import com.example.mylsp.viewmodel.assesment.KomponenViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph(modifier: Modifier, userManager: UserManager, navController: NavHostController, startDestination: String, showBottomBar: (Boolean) -> Unit, showTopBar: (Boolean) -> Unit) {
    val context = LocalContext.current

    var currentApl01InAssesorCheck by remember { mutableStateOf<Apl01?>(null) }

    val skemaViewModel: SkemaViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
    )

    val assesmentAsesiViewModel: AssesmentAsesiViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
    )

    val assessmentViewModel: AssesmentViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
    )

    val apL02ViewModel: APL02ViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(LocalContext.current.applicationContext as Application)
    )

    val asesiViewModel: AsesiViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(LocalContext.current.applicationContext as Application)
    )

    val userViewModel: UserViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(LocalContext.current.applicationContext as Application)
    )

    val apl01ViewModel: APL01ViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(LocalContext.current.applicationContext as Application)
    )
    val iA01ViewModel:IA01ViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
    )

    val ak01ViewModel:AK01ViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
    )

    val aK02ViewModel:AK02ViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
    )

    val aK03ViewModel:AK03ViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
    )

    val komponenViewModel: KomponenViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
    )

    val aK05ViewModel: AK05ViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
    )
    val ak04ViewModel:Ak04ViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
    )

    val assessmentManager = AssessmentManager(context)
    val asesiManager = AsesiManager(context)

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier
    ) {
        composable(Screen.Login.route) {
            showBottomBar(false)
            showTopBar(false)
            LoginScreen(
                userViewModel = userViewModel,
                successLogin = { role ->
                    when (role) {
                        "assesi" -> {
                            navController.navigate("main"){
                                popUpTo(navController.graph.startDestinationId){inclusive = true}
                            }
                        }
                        "asesi"->{
                            navController.navigate(Screen.Main.route){
                                popUpTo(navController.graph.startDestinationId){inclusive = true}
                            }
                        }
                        "asesor"->{
                            navController.navigate(Screen.DashboardAsesor.route){
                                popUpTo(navController.graph.startDestinationId){inclusive = true}
                            }
                        }
                        "assesor" -> {
                            navController.navigate(Screen.DashboardAsesor.route){
                                popUpTo(navController.graph.startDestinationId){inclusive = true}
                            }
                        }
                        else -> {
                            navController.navigate("main"){
                                popUpTo(navController.graph.startDestinationId){inclusive = true}
                            }
                        }
                    }
                },
                navigateRegister = {
                    navController.navigate("register")
                }
            )
        }
        composable(Screen.Register.route) {
            showBottomBar(false)
            showTopBar(false)
            RegisterScreen(
                backToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.ListFormScreen.route){
            val id = it.arguments?.getString("id")?: "0"
            showTopBar(false)
            showBottomBar(false)
            ListFormScreen(
                asesiManager = asesiManager,
                assesmentAsesiViewModel = assesmentAsesiViewModel,
                apl02ViewModel = apL02ViewModel,
                ak01ViewModel = ak01ViewModel,
                ak02ViewModel = aK02ViewModel,
                ak03ViewModel = aK03ViewModel,
                ak04ViewModel = ak04ViewModel,
                ak05ViewModel = aK05ViewModel,
                assesmentViewModel = assessmentViewModel,
                navigateToForm = { route ->
                    if (route == Screen.AssessmentList.route){
                        navController.navigate(route){
                            popUpTo(Screen.ListFormScreen.route){inclusive = true}
                        }
                    }else{
                        navController.navigate(route   )
                    }
                }
            )
        }
        composable(Screen.Ia06a.route){
            FRIA06A(navController = navController)
        }
        composable(Screen.Ak05.route){
            FRAK05(viewModel = aK05ViewModel)
        }
        composable(Screen.KelengkapanDataAsesor.route) {
            KelengkapanDataAsesor(navController = navController)
        }
        composable(Screen.AssessmentList.route) {
            showTopBar(false)
            showBottomBar(true)
            val status = it.arguments?.getString("status")?: "pending"
            AssesmentListScreen(
                modifier = Modifier,
                asesiViewModel = asesiViewModel,
                assesmentViewModel = assessmentViewModel,
                apL01ViewModel = apl01ViewModel,
                navigateToWaitingScreen = {
                    navController.navigate(Screen.WaitingApproval.createRoute(status, Screen.AssessmentList.route)){
                        popUpTo(navController.graph.startDestinationId){
                            inclusive = true
                        }
                    }
                },
                navigateToAssesment = {role, id ->
                    if(role == "assesi" || role == "asesi"){
                        navController.navigate(Screen.DetailAssesment.createRoute(id))
                    }else if (role == "assesor" || role == "asesor"){
                        navController.navigate(Screen.DetailEvent.createRoute(id))
                    }
                }
            )
        }
        composable(Screen.DetailAssesment.route) {
            val id = it.arguments?.getString("id")?: "0"
            DetailAssesment(
                userManager = userManager,
                idAssessment = id.toInt(),
                asesiViewModel = asesiViewModel,
                assesmentAsesiViewModel = assesmentAsesiViewModel,
                onClickKerjakan = { assessment ->
                    navController.navigate(Screen.ListFormScreen.createRoute(assessment))
                    assessmentManager.saveAssessmentId(assessment)
                },
                apL01ViewModel = apl01ViewModel,
                assessmentViewModel = assessmentViewModel
            )
        }
        composable(Screen.Apl01.route) {
            showTopBar(true)
            showBottomBar(true)
            AsesiFormScreen(
                asesiViewModel,
                skemaViewModel = skemaViewModel,
                apl01ViewModel = apl01ViewModel,
                successSendingData = {
                    navController.navigate("main")
                },
                ifStatusPending = {status ->
                    navController.navigate(Screen.WaitingApproval.createRoute(status, Screen.Apl01.route)){
                        popUpTo(navController.graph.startDestinationId){
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(Screen.Apl02.route) {
            showTopBar(false)
            showBottomBar(false)
            val id = it.arguments?.getString("id")?: "0"
            APL02(
                id = id.toInt(),
                assesmentAsesiViewModel = assesmentAsesiViewModel,
                apl01ViewModel = apl01ViewModel,
                userManager = userManager,
                apL02ViewModel = apL02ViewModel,
                nextForm = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.WaitingAK01.route,
            arguments = listOf(
                navArgument("formId") {
                    type = NavType.IntType
                    defaultValue = 1
                },
                navArgument("asesiName") {
                    type = NavType.StringType
                    defaultValue = "John Doe"
                }
            )
        ) {
            showTopBar(false)
            showBottomBar(false)

            val formId = it.arguments?.getInt("formId") ?: 1
            val asesiName = it.arguments?.getString("asesiName") ?: "John Doe"

            WaitingAK01Screen(
                modifier = Modifier,
                formId = formId,
                asesiName = asesiName,
                onNextStep = {
                    // Lanjut ke form berikutnya (misal AK02, AK03, dll)
                    navController.navigate(Screen.Ak04.route) {
                        popUpTo(Screen.WaitingAK01.route) { inclusive = true }
                    }
                },
                onBackToForm = {
                    // Kembali ke form AK01 untuk edit
                    navController.navigate(Screen.Apl02.createRoute(1)) {
                        popUpTo(Screen.WaitingAK01.route) { inclusive = true }
                    }
                },
                onBackToDashboard = {
                    // Kembali ke dashboard utama
                    navController.navigate(Screen.Main.route) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Ak01.route){
            showTopBar(false)
            showBottomBar(false)
            val role = it.arguments?.getString("role")?: "assesi"
            FRAK01(
                aK01ViewModel = ak01ViewModel,
                nextForm = {
                if (role == "assesi"){
                    navController.navigate(Screen.ListFormScreen.route)

                }else if(role == "assesor"){
                    navController.popBackStack()
                }
            })
        }
        composable(Screen.Ak03.route){
            showTopBar(false)
            showBottomBar(false)
            FRAK03(
                aK03ViewModel = aK03ViewModel,
                komponenViewModel = komponenViewModel,
                navController = navController
            )

        }
        composable(Screen.Ak04.route){
            showTopBar(false)
            showBottomBar(false)
            FRAK04(
                viewModel = ak04ViewModel,
                nextForm = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.Ak05.route){
            showTopBar(false)
            showBottomBar(false)
            FRAK05(viewModel = aK05ViewModel)
        }
        composable(Screen.Ia01.route){
            showTopBar(false)
            showBottomBar(false)
            val id = it.arguments?.getString("id")?: "0"
            FRIA01(
                idAssesment = id.toInt(),
                apL02ViewModel = apL02ViewModel,
                assesmentViewModel = assessmentViewModel,
                ia01ViewModel= iA01ViewModel,
                nextForm = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.WaitingApproval.route) {
            showTopBar(false)
            showBottomBar(false)
            val route = it.arguments?.getString("route")?: "apl01"
            val status = it.arguments?.getString("status")?: "pending"
            WaitingApprovalScreen(
                modifier = Modifier,
                nextStep = {
                    if (route == Screen.Apl01.route){
                        navController.navigate(Screen.Apl01.route){
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        }
                    }else if(route == "Apl02"){
                        navController.navigate(Screen.Ak01.createRoute("assesi"))
                    }else{
                        navController.navigate(Screen.Main.route)
                    }
                },
                backFillForm = {
                    navController.navigate(Screen.Apl01.route) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                },
                textButton = "Lanjut ke form selanjutnya",
                type = "jawaban",
                status = status
            )
        }

        composable(Screen.Congrats.route){
            showTopBar(false)
            showBottomBar(false)
            CongratsScreen(
                onNext = {
                    navController.navigate(Screen.Main.route)
                }
            )
        }
        composable(Screen.Profile.route){
            if (userManager.getUserRole() == "asesi"){
                showTopBar(true)
            }
            showBottomBar(false)
            ProfileScreen(modifier = Modifier, navController = navController)
        }
        composable(Screen.Main.route) {
            showTopBar(true)
            showBottomBar(true)
            MainScreen(modifier = Modifier,apl01ViewModel = apl01ViewModel,aK05ViewModel = aK05ViewModel, asesiViewModel = asesiViewModel, assesmentAsesiViewModel = assesmentAsesiViewModel, navController = navController)
        }
        composable(Screen.Events.route) {
            showBottomBar(false)
            Events(navController = navController)
        }
        composable(Screen.DashboardAsesor.route){
            showTopBar(false)
            showBottomBar(true)
            DashboardAsesor(assesmentViewModel = assessmentViewModel, navController = navController)
        }

        composable("scanningBarcode"){
            showBottomBar(false)
            showTopBar(false)
            AsesiBarcodeScanner(navController = navController)
        }

        composable(Screen.ApprovedUnapproved.route) {
            showTopBar(false)
            showBottomBar(false)

            currentApl01InAssesorCheck?.let { asesi ->
                ApprovedUnapprovedScreen(
                    modifier = Modifier,
                    apl02ViewModel = apL02ViewModel,
                    ia01ViewModel = iA1ViewModel,
                    ak01ViewModel = ak01ViewModel,
                    ak02ViewModel = aK02ViewModel,
                    ak03ViewModel = aK03ViewModel,
                    ak05ViewModel = aK05ViewModel,
                    assesmentViewModel = assessmentViewModel,
                    // Bisa ambil userName dari UserManager atau parameter lain
                    apl01 = asesi,
                    navigateToForm = {
                        navController.navigate(it)
                    }
                )
            }

        }

        // Tambahkan juga route untuk form-form yang belum ada composable
        composable(Screen.Ia02.route) {
            showTopBar(false)
            showBottomBar(false)
            // TODO: Buat screen untuk FR.IA.02 - TPO TUGAS PRAKTIK DEMONSTRASI
            FRIA02()
        }

        composable(Screen.Ia03.route) {
            showTopBar(false)
            showBottomBar(false)
            // TODO: Buat screen untuk FR.IA.03 - PERTANYAAN UNTUK MENDUKUNG OBSERVASI
            FRIA03()
        }

        composable(Screen.Ia06c.route) {
            showTopBar(false)
            showBottomBar(false)
            // TODO: Buat screen untuk FR.IA.06.C - LEMBAR JAWABAN PERTANYAAN TERTULIS
            FRIA06C(navController = navController)
        }

        composable(Screen.Ak02.route) {
            showTopBar(false)
            showBottomBar(false)
            val id = it.arguments?.getString("id")?: "0"
            // TODO: Buat screen untuk FR.AK.02 - REKAMAN ASESMEN KOMPETENSI
            FRAK02(

                navController = navController,
                aK02ViewModel = aK02ViewModel,
                idSkema = id.toInt(),
                apl02ViewModel = apL02ViewModel
            )
        }

        composable("barcode"){
            showBottomBar(false)
            BarcodeScreen(text = "*&KJDHASD&^#!DASDHDAS")
        }
        composable(Screen.DetailEvent.route) {
            val id = it.arguments?.getString("id")?: "0"
            DetailEvent(
                idAssesment = id.toInt(),
                userManager = userManager,
                assessmentViewModel = assessmentViewModel,
                assesmentAsesiViewModel = assesmentAsesiViewModel,
                onDetailAssessi = { idAssesmentAsesi, asesi ->
                    currentApl01InAssesorCheck = asesi
                    navController.navigate(Screen.ApprovedUnapproved.route)
                }
            )
        }
    }
}