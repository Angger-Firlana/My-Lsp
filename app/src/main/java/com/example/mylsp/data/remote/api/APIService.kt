package com.example.mylsp.data.remote.api

import com.example.mylsp.data.api.assesment.AK01SubmissionResponse
import com.example.mylsp.data.api.assesment.Apl02
import com.example.mylsp.data.api.assesment.IA01Request
import com.example.mylsp.data.api.assesment.IA01Response
import com.example.mylsp.model.api.JurusanResponse
import com.example.mylsp.data.api.auth.LoginRequest
import com.example.mylsp.data.api.auth.LoginResponse
import com.example.mylsp.data.api.auth.RegisterRequest
import com.example.mylsp.data.api.auth.RegisterResponse
import com.example.mylsp.model.api.ResponseSubmission
import com.example.mylsp.data.model.api.Skemas
import com.example.mylsp.model.api.SubmissionGroup
import com.example.mylsp.model.api.User
import com.example.mylsp.data.api.asesi.AssesmentAsesiResponse
import com.example.mylsp.data.api.asesi.PatchStatusReq
import com.example.mylsp.data.api.asesi.PatchStatusResponse
import com.example.mylsp.data.api.asesi.PostAssesmentAsesiReq
import com.example.mylsp.data.api.asesi.PostAssesmentAsesiResponse
import com.example.mylsp.data.api.assesment.AK01Submission
import com.example.mylsp.data.api.assesment.AK04
import com.example.mylsp.data.api.assesment.Ak02GetResponse
import com.example.mylsp.data.api.assesment.Ak02Request
import com.example.mylsp.data.api.assesment.Ak02Response
import com.example.mylsp.data.api.assesment.Ak05SubmissionRequest
import com.example.mylsp.data.api.assesment.Ak05SubmissionResponse
import com.example.mylsp.data.api.assesment.Apl02Response
import com.example.mylsp.data.api.assesment.Assessment
import com.example.mylsp.data.api.assesment.GetAK01Response
import com.example.mylsp.data.api.assesment.GetAK03Response
import com.example.mylsp.data.api.assesment.GetAK04QuestionResponse
import com.example.mylsp.data.api.assesment.GetAK04Response
import com.example.mylsp.data.api.assesment.GetAPL02Response
import com.example.mylsp.data.api.assesment.GetAk05Response
import com.example.mylsp.data.api.assesment.GetAssesmentResponse
import com.example.mylsp.data.api.assesment.IA01GetResponse
import com.example.mylsp.data.api.assesment.KomponenResponse
import com.example.mylsp.data.api.assesment.PostAK03Request
import com.example.mylsp.data.api.assesment.PostAK03Response
import com.example.mylsp.data.api.assesment.PostAK04Response
import com.example.mylsp.data.api.assesment.PostApproveIa01Response
import com.example.mylsp.data.api.assesment.PostApproveRequest
import com.example.mylsp.data.api.assesment.PostApproveResponse
import com.example.mylsp.data.api.assesment.QuestionResponse
import com.example.mylsp.data.model.api.Apl01
import com.example.mylsp.data.model.api.AsesiResponse
import com.example.mylsp.data.model.api.CreateAsesiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Streaming

interface APIService {
    //Auth
    @POST("auth/login")
    suspend fun login(
        @Body request: com.example.mylsp.data.api.auth.LoginRequest
    ): Response<com.example.mylsp.data.api.auth.LoginResponse>
    @POST("auth/register")
    suspend fun register(
        @Header("Accept") accept: String = "application/json",
        @Body registerRequest: RegisterRequest
    ): Response<RegisterResponse>

    //User
    @GET("user")
    suspend fun getUser():Response<User>

    // Apl 01
    @Multipart
    @POST("assesment/formapl01")
    suspend fun createApl01(
        @Header("Accept") accept: String = "application/json",
        @Part("nama_lengkap") namaLengkap: RequestBody?,
        @Part("no_ktp") nik: RequestBody?,
        @Part("tanggal_lahir") tglLahir: RequestBody?,
        @Part("tempat_lahir") tempatLahir: RequestBody?,
        @Part("jenis_kelamin") jenisKelamin: RequestBody?,
        @Part("kebangsaan") kebangsaan: RequestBody?,
        @Part("alamat_rumah") alamatRumah: RequestBody?,
        @Part("kode_pos") kodePos: RequestBody?,
        @Part("no_telepon_rumah") noTeleponRumah: RequestBody?,
        @Part("no_telepon_kantor") noTeleponKantor: RequestBody?,
        @Part("no_telepon") noTelepon: RequestBody?,
        @Part("email") email: RequestBody?,
        @Part("kualifikasi_pendidikan") kualifikasiPendidikan: RequestBody?,
        @Part("nama_institusi") namaInstitusi: RequestBody?,
        @Part("jabatan") jabatan: RequestBody?,
        @Part("alamat_kantor") alamatKantor: RequestBody?,
        @Part("kode_pos_kantor") kodePosKantor: RequestBody?,
        @Part("fax_kantor") faxKantor: RequestBody?,
        @Part("email_kantor") emailKantor: RequestBody?,
        @Part("status") status: RequestBody?,
        @Part("tujuan_asesmen") tujuan_assesment: RequestBody?,
        @Part("schema_id") schema_id: RequestBody?,
        @Part attachments: List<MultipartBody.Part>
    ): Response<CreateAsesiResponse>

    @Multipart
    @POST("assesment/formapl01/{id}")
    suspend fun updateApl01(
        @Header("Accept") accept: String = "application/json",
        @Path("id") id: Int,
        @Part("nama_lengkap") namaLengkap: RequestBody?,
        @Part("no_ktp") nik: RequestBody?,
        @Part("tanggal_lahir") tglLahir: RequestBody?,
        @Part("tempat_lahir") tempatLahir: RequestBody?,
        @Part("jenis_kelamin") jenisKelamin: RequestBody?,
        @Part("kebangsaan") kebangsaan: RequestBody?,
        @Part("alamat_rumah") alamatRumah: RequestBody?,
        @Part("kode_pos") kodePos: RequestBody?,
        @Part("no_telepon_rumah") noTeleponRumah: RequestBody?,
        @Part("no_telepon_kantor") noTeleponKantor: RequestBody?,
        @Part("no_telepon") noTelepon: RequestBody?,
        @Part("email") email: RequestBody?,
        @Part("kualifikasi_pendidikan") kualifikasiPendidikan: RequestBody?,
        @Part("nama_institusi") namaInstitusi: RequestBody?,
        @Part("jabatan") jabatan: RequestBody?,
        @Part("alamat_kantor") alamatKantor: RequestBody?,
        @Part("kode_pos_kantor") kodePosKantor: RequestBody?,
        @Part("fax_kantor") faxKantor: RequestBody?,
        @Part("email_kantor") emailKantor: RequestBody?,
        @Part("status") status: RequestBody?,
        @Part("tujuan_asesmen") tujuan_assesment: RequestBody?,
        @Part("schema_id") schema_id: RequestBody?,
        @Part attachments: List<MultipartBody.Part>
    ): Response<CreateAsesiResponse>


    @GET("assesment")
    suspend fun getAssesments(): Response<com.example.mylsp.data.api.assesment.AssessmentResponse>

    @GET("assesment/{id}")
    suspend fun getAssesmentById(@Path("id") id: Int): Response<com.example.mylsp.data.api.assesment.GetAssesmentResponse>
    //SHOW Approve and data apl 01
    @GET("show/approvement/assesment/formapl01/{id}")
    suspend fun getFormApl01Status(@Path("id") id:Int): Response<Apl01>

    @GET("user/show/approvement/assesment/formapl01/{id}")
    suspend fun getFormApl01StatusByUser(@Path("id") id:Int): Response<Apl01>

    //ASESI
    @GET("assesi")
    suspend fun getAssesis():Response<AsesiResponse>

    //Assesment ASESI
    @GET("asesi/assesment-asesi/{id}")
    suspend fun getAssesmentAsesiByAsesi(@Path("id") id: Int): Response<AssesmentAsesiResponse>

    @GET("assesor/assesment-asesi/{id}")
    suspend fun getAssesmentAsesiByAssesment(@Path("id") id: Int): Response<AssesmentAsesiResponse>

    @POST("asesi/assesment-asesi")
    suspend fun postAssesmentAsesi(
        @Header("Accept") accept: String = "application/json",
        @Body request: PostAssesmentAsesiReq
    ):Response<PostAssesmentAsesiResponse>

    @PATCH("assesment/asesi/{id}/updateStatus")
    suspend fun patchStatusAssesmentAsesi(
        @Header("Accept") accept: String = "application/json",
        @Path("id") id: Int,
        @Body request: PatchStatusReq
    ):Response<PatchStatusResponse>

    //APL02
    @GET("apl02/{id}")
    suspend fun getAPL02(@Path("id") id: Int): Response<Apl02>

    @POST("assesment/formapl02")
    suspend fun sendSubmission(
        @Header("Accept") accept: String = "application/json",
        @Body submissionRequest: SubmissionGroup
    ): Response<ResponseSubmission>

    @GET("apl02/assesi/{id}")
    suspend fun getApl02ByAsesi(
        @Header("Accept") accept: String = "application/json",
        @Path("id") id: Int
    ):Response<GetAPL02Response>

    @POST("approvement/assesment/formapl02/{id}")
    suspend fun postApproveApl02(

        @Header("Accept") accept: String = "application/json",
        @Path("id") id: Int,
        @Body request: PostApproveRequest
    ):Response<PostApproveResponse>

    //AK01
    @POST("assesment/formak01")
    suspend fun sendSubmissionAk01(
        @Header("Accept") accept: String = "application/json",
        @Body bodyRequestBody: AK01Submission
    ):Response<AK01SubmissionResponse>

    @GET("assesment/formak01/{id}")
    suspend fun getAk01ByAsesi(
        @Header("Accept") accept: String = "application/json",
        @Path("id") id: Int
    ):Response<GetAK01Response>

    @POST("user/assesment/formak01/{id}")
    suspend fun postApproveAk01(
        @Header("Accept") accept: String = "application/json",
        @Path("id") id: Int,
    ):Response<PostApproveResponse>

    //AK02
    @POST("assesment/formak02")
    suspend fun postSubmissionAk02(
        @Header("Accept") accept: String = "application/json",
        @Body bodyRequestBody: Ak02Request
    ):Response<Ak02Response>

    @GET("assesment/formak02/{id}")
    suspend fun getAK02ByAsesi(
        @Header("Accept") accept: String = "application/json",
        @Path("id") id:Int
    ):Response<Ak02GetResponse>

    @POST("user/assesment/formak02/{id}")
    suspend fun updateStatusAsesi(
        @Header("Accept") accept: String = "application/json",
        @Path("id") id: Int,
    ):Response<Ak02Response>

    //AK03
    @POST("assesment/formak03")
    suspend fun postSubmissionAk03(
        @Header("Accept") accept: String = "application/json",
        @Body bodyRequestBody: PostAK03Request
    ):Response<PostAK03Response>

    @GET("assesment/formak03/{id}")
    suspend fun getAk03ByAsesi(
        @Header("Accept") accept: String = "application/json",
        @Path("id") id: Int
    ):Response<GetAK03Response>

    //AK04
    @GET("ak04/questions")
    suspend fun getAk04Questions():Response<GetAK04QuestionResponse>

    @GET("assesment/formak04/{id}")
    suspend fun getAk04ByAsesi(
        @Header("Accept") accept: String = "application/json",
        @Path("id") id: Int
    ):Response<GetAK04Response>

    @POST("assesment/formak04")
    suspend fun postAk04(
        @Header("Accept") accept: String = "application/json",
        @Body bodyRequestBody: AK04
    ):Response<PostAK04Response>

    //AK05
    @POST("assesment/formak05")
    suspend fun postAk05(
        @Header("Accept") accept: String = "application/json",
        @Body bodyRequestBody: Ak05SubmissionRequest

    ):Response<Ak05SubmissionResponse>

    @GET("assesment/formak05/{id}")
    suspend fun getAk05(
        @Header("Accept") accept: String = "application/json",
        @Path("id") id: Int
    ):Response<GetAk05Response>

    //IA01
    @POST("assesment/formia01")
    suspend fun postSubmissionIa01(
        @Header("Accept") accept: String = "application/json",
        @Body submissionRequest: com.example.mylsp.data.api.assesment.IA01Request
    ):Response<com.example.mylsp.data.api.assesment.IA01Response>

    @GET("assesment/formia01/{id}")
    suspend fun getIA01ByAsesi(
        @Header("Accept") accept: String = "application/json",
        @Path("id") id: Int
    ):Response<com.example.mylsp.data.api.assesment.IA01GetResponse>

    @POST("/approvement/assesment/formia01/{id}")
    suspend fun postApproveIa01ByAsesi(
        @Header("Accept") accept: String = "application/json",
        @Path("id") id: Int
    ):Response<PostApproveIa01Response>

    //Komponen
    @GET("komponen")
    suspend fun getKomponens():Response<com.example.mylsp.data.api.assesment.KomponenResponse>
    //getApl01
    @GET("apl01ByUser/{id}")
    suspend fun getApl01ByUser(@Path("id") id: Int): Response<Apl01>

    //Jurusan
    @GET("jurusan")
    suspend fun getJurusans():Response<JurusanResponse>

    //Asesi
    @GET("asesi")
    suspend fun getDataAsesi():Response<Apl01>

    //Skema
    @GET("schema")
    suspend fun getListSkema():Response<com.example.mylsp.data.model.api.Skemas>

    //Question
    @GET("questionFiles/skema/{id}")
    suspend fun getQuestionsBySkema(id:Int):Response<QuestionResponse>

    @GET("questionFiles/{id}")
    @Streaming
    suspend fun downloadQuestion(@Path("id") id:Int):Response<ResponseBody>
}