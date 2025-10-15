package com.example.mylsp.navigation

sealed class Screen(val route: String) {
    data object Login: Screen("login")
    data object Register: Screen("register")
    data object Main: Screen("main")
    data object Profile: Screen("profile")
    data object Barcode: Screen("barcode")
    data object DetailEvent: Screen("detail_event/{id}"){
        fun createRoute(id:Int) = "detail_event/$id"
    }
    data object Events: Screen("events")
    data object DashboardAsesor: Screen("dashboardAsesor")
    data object DetailAssesment: Screen("detail_assessment/{id}"){
        fun createRoute(id: Int) = "detail_assessment/$id"
    }
    data object ListFormScreen: Screen("list_form/{id}"){
        fun createRoute(id:Int) = "list_form/$id"
    }
    data object Apl01: Screen("apl_01")
    data object Apl02: Screen("apl02/{id}"){
        fun createRoute(id: Int) = "apl02/$id"
    }

    data object Ak01: Screen("ak01/{role}"){
        fun createRoute(role: String) = "ak01/$role"
    }
    data object Ak02: Screen("ak02/{id}"){
        fun createRoute(id:Int) = "ak02/$id"
    }
    data object Ak03: Screen("ak03")
    data object Ak04: Screen("ak04")
    data object Ak05: Screen("ak05")
    data object Ia01: Screen("ia01/{id}"){
        fun createRoute(id: Int) = "ia01/$id"
    }
    data object Ia02: Screen("ia02")
    data object Ia03: Screen("ia03")
    data object Ia06a: Screen("ia06a")
    data object Ia06c: Screen("ia06c")
    data object WaitingAK01:Screen("waiting_ak01/{formId}/{asesiName}"){
        fun createRoute(formId: Int, asesiName: String = "Apdal Ezhar Rahma Pangestu"): String {
            return "waiting_ak01/$formId/$asesiName"
        }
    }



    object PDFViewer {
        const val route = "pdf_viewer/{pdfType}/{title}"

        fun createRoute(pdfType: String, title: String = "Dokumen PDF"): String {
            return "pdf_viewer/$pdfType/$title"
        }
    }

    data object WaitingApproval: Screen("waiting_approval/{route}/{status}"){
        fun createRoute(status: String, form: String) = "waiting_approval/$form/$status"
    }
    data object WaitingApprovalAlternative: Screen("waiting_approval_alternative/{route}/{status}"){
        fun createRoute(status: String, form: String) = "waiting_approval_alternative/$form/$status"
    }
    data object AssessmentList: Screen("assessmentList")

    // Route untuk ApprovedUnapprovedScreen
    data object ApprovedUnapproved: Screen("approved_unapproved"){
        fun createRoute(id:Int) = "approved_unapproved/$id"
    }

    data object Congrats: Screen("congrats")
    data object KelengkapanDataAsesor: Screen("kelengkapanDataAsesor")

    data object UploadSoal: Screen("upload_soal/{idSkema}"){
        fun createRoute(id:Int) = "upload_soal/$id"
    }
}