package com.example.mylsp.navigation

sealed class Screen(val route: String) {
    data object Login: Screen("login")
    data object Register: Screen("register")
    data object Main: Screen("main")
    data object Profile: Screen("profile")
    data object Barcode: Screen("barcode")
    data object DetailEvent: Screen("detail_event")
    data object Events: Screen("events")
    data object DashboardAsesor: Screen("dashboardAsesor")
    data object DetailAssesment: Screen("detail_assessment/{id}"){
        fun createRoute(id: Int) = "detail_assessment/$id"
    }
    data object Apl01: Screen("apl_01")
    data object Apl02: Screen("apl02/{id}"){
        fun createRoute(id: Int) = "apl02/$id"
    }
    data object Ak01: Screen("ak01")
    data object Ak02: Screen("ak02")
    data object Ak03: Screen("ak03")
    data object Ak04: Screen("ak04")
    data object Ak05: Screen("ak05")
    data object Ia06a: Screen("ia06a")
    data object Ia01: Screen("ia01/{id}"){
        fun createRoute(id: Int) = "ia01/$id"
    }

    data object WaitingApproval: Screen("waiting_approval/{route}/{status}"){
        fun createRoute(status: String, form: String) = "waiting_approval/$form/$status"
    }
    data object AssessmentList: Screen("assessmentList")

    data object Congrats: Screen("congrats")
    data object KelengkapanDataAsesor: Screen("kelengkapanDataAsesor")
}