package com.example.lsp24.models

import com.google.gson.annotations.SerializedName

data class Asesi(
    @SerializedName("id_asesi") val idAsesi: Int,
    @SerializedName("id_user") val idUser: Int,
    @SerializedName("nama_lengkap") val namaLengkap: String,
    @SerializedName("no_ktp") val noKtp: String?,
    @SerializedName("tempat_lahir") val tempatLahir: String?,
    @SerializedName("tanggal_lahir") val tanggalLahir: String?,
    @SerializedName("jenis_kelamin") val jenisKelamin: String?,
    @SerializedName("alamat") val alamat: String?,
    @SerializedName("kode_pos") val kodePos: String?,
    @SerializedName("no_telepon") val noTelepon: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("kualifikasi_pendidikan") val kualifikasiPendidikan: String?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?,
    @SerializedName("id_jurusan") val idJurusan: Int?
)

data class Asesmen(
    @SerializedName("id_asesmen") val idAsesmen: Int,
    @SerializedName("id_skema") val idSkema: Int?,
    @SerializedName("id_asesi") val idAsesi: Int?,
    @SerializedName("id_asesor") val idAsesor: Int?,
    @SerializedName("tanggal_mulai") val tanggalMulai: String?,
    @SerializedName("tanggal_selesai") val tanggalSelesai: String?,
    @SerializedName("tuk") val tuk: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("hasil") val hasil: String?,
    @SerializedName("catatan") val catatan: String?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?
)

data class Asesor(
    @SerializedName("id_asesor") val idAsesor: Int,
    @SerializedName("id_user") val idUser: Int,
    @SerializedName("nama_lengkap") val namaLengkap: String,
    @SerializedName("no_registrasi") val noRegistrasi: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("no_telepon") val noTelepon: String?,
    @SerializedName("kompetensi") val kompetensi: String?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?
)

data class AuditLog(
    @SerializedName("id_log") val idLog: Int,
    @SerializedName("id_user") val idUser: Int?,
    @SerializedName("action_type") val actionType: String,
    @SerializedName("table_name") val tableName: String?,
    @SerializedName("record_id") val recordId: Int?,
    @SerializedName("old_value") val oldValue: String?,
    @SerializedName("new_value") val newValue: String?,
    @SerializedName("action_timestamp") val actionTimestamp: String,
    @SerializedName("ip_address") val ipAddress: String?
)

data class BarcodeTracking(
    @SerializedName("id_barcode") val idBarcode: Int,
    @SerializedName("id_asesmen") val idAsesmen: Int?,
    @SerializedName("kode_formulir") val kodeFormulir: String?,
    @SerializedName("barcode_value") val barcodeValue: String?,
    @SerializedName("image_path") val imagePath: String?,
    @SerializedName("created_at") val createdAt: String?
)

data class BuktiPengirimanDokumen(
    @SerializedName("id_bukti") val idBukti: Int,
    @SerializedName("id_asesmen") val idAsesmen: Int?,
    @SerializedName("jenis_dokumen") val jenisDokumen: String?,
    @SerializedName("nama_file") val namaFile: String?,
    @SerializedName("file_path") val filePath: String?,
    @SerializedName("keterangan") val keterangan: String?,
    @SerializedName("uploaded_at") val uploadedAt: String?
)

data class DokumenAsesmen(
    @SerializedName("id_dokumen") val idDokumen: Int,
    @SerializedName("id_asesmen") val idAsesmen: Int?,
    @SerializedName("jenis_dokumen") val jenisDokumen: String?,
    @SerializedName("nama_dokumen") val namaDokumen: String?,
    @SerializedName("file_path") val filePath: String?,
    @SerializedName("tanggal_upload") val tanggalUpload: String?
)

data class ElemenKompetensi(
    @SerializedName("id_elemen") val idElemen: Int,
    @SerializedName("id_unit") val idUnit: Int?,
    @SerializedName("nama_elemen") val namaElemen: String,
    @SerializedName("urutan") val urutan: Int?
)

data class Formulir(
    @SerializedName("id_formulir") val idFormulir: Int,
    @SerializedName("kode_formulir") val kodeFormulir: String?,
    @SerializedName("nama_formulir") val namaFormulir: String?,
    @SerializedName("deskripsi") val deskripsi: String?,
    @SerializedName("versi") val versi: String?,
    @SerializedName("is_active") val isActive: Boolean?,
    @SerializedName("created_at") val createdAt: String?
)
data class FormApl01(
    @SerializedName("id_apl01") val idApl01: Int,
    @SerializedName("id_asesmen") val idAsesmen: Int?,
    @SerializedName("nama_lengkap") val namaLengkap: String?,
    @SerializedName("no_ktp") val noKtp: String?,
    @SerializedName("tempat_lahir") val tempatLahir: String?,
    @SerializedName("tanggal_lahir") val tanggalLahir: String?,
    @SerializedName("jenis_kelamin") val jenisKelamin: String?,
    @SerializedName("kebangsaan") val kebangsaan: String?,
    @SerializedName("alamat") val alamat: String?,
    @SerializedName("kode_pos") val kodePos: String?,
    @SerializedName("no_rumah") val noRumah: String?,
    @SerializedName("no_kantor") val noKantor: String?,
    @SerializedName("no_hp") val noHp: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("kualifikasi_pendidikan") val kualifikasiPendidikan: String?,
    @SerializedName("institusi") val institusi: String?,
    @SerializedName("jabatan") val jabatan: String?,
    @SerializedName("alamat_kantor") val alamatKantor: String?,
    @SerializedName("kode_pos_kantor") val kodePosKantor: String?,
    @SerializedName("telp_kantor") val telpKantor: String?,
    @SerializedName("fax_kantor") val faxKantor: String?,
    @SerializedName("email_kantor") val emailKantor: String?,
    @SerializedName("created_at") val createdAt: String?
)

data class FormApl02(
    @SerializedName("id_apl02") val idApl02: Int,
    @SerializedName("id_asesmen") val idAsesmen: Int?,
    @SerializedName("kode_unit") val kodeUnit: String?,
    @SerializedName("judul_unit") val judulUnit: String?,
    @SerializedName("elemen") val elemen: String?,
    @SerializedName("kuk") val kuk: String?,
    @SerializedName("status") val status: String?, // K / BK
    @SerializedName("bukti") val bukti: String?,
    @SerializedName("created_at") val createdAt: String?
)

data class FormJawaban(
    @SerializedName("id_jawaban") val idJawaban: Int,
    @SerializedName("id_asesmen") val idAsesmen: Int?,
    @SerializedName("id_pertanyaan") val idPertanyaan: Int?,
    @SerializedName("jawaban") val jawaban: String?,
    @SerializedName("created_at") val createdAt: String?
)

data class FormPertanyaan(
    @SerializedName("id_pertanyaan") val idPertanyaan: Int,
    @SerializedName("id_formulir") val idFormulir: Int?,
    @SerializedName("nama_field") val namaField: String?,
    @SerializedName("label_pertanyaan") val labelPertanyaan: String?,
    @SerializedName("tipe") val tipe: String?,
    @SerializedName("pilihan") val pilihan: String?,
    @SerializedName("is_required") val isRequired: Boolean?,
    @SerializedName("urutan") val urutan: Int?
)

data class Jurusan(
    @SerializedName("id_jurusan") val idJurusan: Int,
    @SerializedName("kode_jurusan") val kodeJurusan: String,
    @SerializedName("nama_jurusan") val namaJurusan: String,
    @SerializedName("jenjang") val jenjang: String,
    @SerializedName("deskripsi") val deskripsi: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?
)

data class JurusanSkema(
    @SerializedName("id_jurusan_skema") val idJurusanSkema: Int,
    @SerializedName("id_jurusan") val idJurusan: Int,
    @SerializedName("id_skema") val idSkema: Int,
    @SerializedName("tanggal_berlaku") val tanggalBerlaku: String?,
    @SerializedName("catatan") val catatan: String?
)

data class KriteriaUnjukKerja(
    @SerializedName("id_kuk") val idKuk: Int,
    @SerializedName("id_elemen") val idElemen: Int?,
    @SerializedName("deskripsi_kuk") val deskripsiKuk: String,
    @SerializedName("urutan") val urutan: Int?,
    @SerializedName("standar_industri") val standarIndustri: String?
)

data class Permission(
    @SerializedName("id_permission") val idPermission: Int,
    @SerializedName("permission_name") val permissionName: String,
    @SerializedName("description") val description: String?
)

data class SkemaSertifikasi(
    @SerializedName("id_skema") val idSkema: Int,
    @SerializedName("judul_skema") val judulSkema: String,
    @SerializedName("nomor_skema") val nomorSkema: String,
    @SerializedName("jenis_skema") val jenisSkema: String?,
    @SerializedName("deskripsi") val deskripsi: String?,
    @SerializedName("tanggal_berlaku") val tanggalBerlaku: String?,
    @SerializedName("status") val status: String?
)

data class TandaTangan(
    @SerializedName("id_ttd") val idTtd: Int,
    @SerializedName("id_asesmen") val idAsesmen: Int?,
    @SerializedName("peran") val peran: String?,
    @SerializedName("nama") val nama: String?,
    @SerializedName("image_path") val imagePath: String?,
    @SerializedName("qr_code_path") val qrCodePath: String?,
    @SerializedName("signed_at") val signedAt: String?
)

data class UnitKompetensi(
    @SerializedName("id_unit") val idUnit: Int,
    @SerializedName("kode_unit") val kodeUnit: String,
    @SerializedName("judul_unit") val judulUnit: String,
    @SerializedName("id_skema") val idSkema: Int?,
    @SerializedName("deskripsi") val deskripsi: String?
)

data class UnitKompetensiDetail(
    @SerializedName("id_detail") val idDetail: Int,
    @SerializedName("id_asesmen") val idAsesmen: Int?,
    @SerializedName("kode_unit") val kodeUnit: String?,
    @SerializedName("elemen") val elemen: String?,
    @SerializedName("kuk") val kuk: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("bukti") val bukti: String?,
    @SerializedName("metode_asesmen") val metodeAsesmen: String?,
    @SerializedName("komentar") val komentar: String?,
    @SerializedName("created_at") val createdAt: String?
)

data class User(
    @SerializedName("id_user") val idUser: Int,
    @SerializedName("username") val username: String,
    @SerializedName("password_hash") val passwordHash: String,
    @SerializedName("email") val email: String,
    @SerializedName("role") val role: String,
    @SerializedName("id_related") val idRelated: Int?,
    @SerializedName("last_login") val lastLogin: String?,
    @SerializedName("is_active") val isActive: Boolean?,
    @SerializedName("reset_token") val resetToken: String?,
    @SerializedName("reset_token_expires") val resetTokenExpires: String?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?
)

data class UserSession(
    @SerializedName("id_session") val idSession: String,
    @SerializedName("id_user") val idUser: Int,
    @SerializedName("login_time") val loginTime: String,
    @SerializedName("last_activity") val lastActivity: String,
    @SerializedName("ip_address") val ipAddress: String,
    @SerializedName("user_agent") val userAgent: String?
)
