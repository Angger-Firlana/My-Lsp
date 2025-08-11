package com.example.mylsp.api

import com.example.mylsp.model.api.AsesiRequest
import com.example.mylsp.model.api.CreateAsesiResponse
import com.example.mylsp.model.api.Jurusan
import com.example.mylsp.model.api.LoginRequest
import com.example.mylsp.model.api.LoginResponse
import com.example.mylsp.model.api.RegisterRequest
import com.example.mylsp.model.api.RegisterResponse
import com.example.mylsp.model.api.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface APIService {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterResponse>
    @GET("user")
    suspend fun getUser():Response<User>
    @Multipart
    @POST("assesment/formapl01")
    suspend fun createApl01(
        @Header("Accept") accept: String = "application/json",
        @Part("nama_lengkap") namaLengkap: RequestBody,
        @Part("no_ktp") nik: RequestBody,
        @Part("tanggal_lahir") tglLahir: RequestBody,
        @Part("tempat_lahir") tempatLahir: RequestBody,
        @Part("jenis_kelamin") jenisKelamin: RequestBody,
        @Part("kebangsaan") kebangsaan: RequestBody,
        @Part("alamat_rumah") alamatRumah: RequestBody,
        @Part("kode_pos") kodePos: RequestBody,
        @Part("no_telepon_rumah") noTeleponRumah: RequestBody,
        @Part("no_telepon_kantor") noTeleponKantor: RequestBody,
        @Part("no_telepon") noTelepon: RequestBody,
        @Part("email") email: RequestBody,
        @Part("kualifikasi_pendidikan") kualifikasiPendidikan: RequestBody,
        @Part("nama_institusi") namaInstitusi: RequestBody,
        @Part("jabatan") jabatan: RequestBody,
        @Part("alamat_kantor") alamatKantor: RequestBody,
        @Part("kode_pos_kantor") kodePosKantor: RequestBody,
        @Part("fax_kantor") faxKantor: RequestBody,
        @Part("email_kantor") emailKantor: RequestBody,
        @Part("status") status: RequestBody,
        @Part attachments: List<MultipartBody.Part>
    ): Response<CreateAsesiResponse>
    @GET("jurusan")
    suspend fun getJurusans():Response<List<Jurusan>>
}