package com.example.mylsp.util

import androidx.compose.runtime.mutableStateOf
import com.example.lsp24.models.Asesi
import com.example.lsp24.models.Asesmen
import com.example.lsp24.models.FormApl01
import com.example.lsp24.models.User

object Util {
    val dummyUsers = listOf(
        User(
            idUser = 1,
            username = "johndoe",
            passwordHash = "1234",
            email = "johndoe@example.com",
            role = "asesor",
            idRelated = null,
            lastLogin = "2025-08-06T08:30:00",
            isActive = true,
            resetToken = null,
            resetTokenExpires = null,
            createdAt = "2025-01-15T10:00:00",
            updatedAt = "2025-08-01T12:00:00"
        ),
        User(
            idUser = 2,
            username = "janedoe",
            passwordHash = "1234",
            email = "janedoe@example.com",
            role = "asesi",
            idRelated = 101,
            lastLogin = "2025-08-05T19:15:00",
            isActive = true,
            resetToken = "abc123reset",
            resetTokenExpires = "2025-08-10T00:00:00",
            createdAt = "2025-02-01T09:45:00",
            updatedAt = "2025-08-03T14:20:00"
        ),
        User(
            idUser = 4,
            username = "udi",
            passwordHash = "1234",
            email = "janedoe@example.com",
            role = "asesi",
            idRelated = 101,
            lastLogin = "2025-08-05T19:15:00",
            isActive = true,
            resetToken = "abc123reset",
            resetTokenExpires = "2025-08-10T00:00:00",
            createdAt = "2025-02-01T09:45:00",
            updatedAt = "2025-08-03T14:20:00"
        ),
        User(
            idUser = 3,
            username = "michael",
            passwordHash = "1234",
            email = "michael@example.com",
            role = "asesi",
            idRelated = 202,
            lastLogin = null,
            isActive = false,
            resetToken = null,
            resetTokenExpires = null,
            createdAt = "2025-03-12T16:10:00",
            updatedAt = null
        )
    )

    val dummyAsesiList = listOf(
        Asesi(
            idAsesi = 1,
            idUser = 2,
            namaLengkap = "Budi Santoso",
            noKtp = "3276010101010001",
            tempatLahir = "Bandung",
            tanggalLahir = "1990-05-12",
            jenisKelamin = "Laki-laki",
            alamat = "Jl. Merdeka No. 10, Bandung",
            kodePos = "40111",
            noTelepon = "081234567890",
            email = "budi@example.com",
            kualifikasiPendidikan = "S1 Teknik Informatika",
            createdAt = "2025-08-01 10:00:00",
            updatedAt = "2025-08-05 14:20:00",
            idJurusan = 5
        ),
        Asesi(
            idAsesi = 2,
            idUser = 102,
            namaLengkap = "Siti Aminah",
            noKtp = "3276010202020002",
            tempatLahir = "Jakarta",
            tanggalLahir = "1992-08-20",
            jenisKelamin = "Perempuan",
            alamat = "Jl. Sudirman No. 45, Jakarta",
            kodePos = "10220",
            noTelepon = "081298765432",
            email = "siti@example.com",
            kualifikasiPendidikan = "S2 Manajemen",
            createdAt = "2025-08-02 14:30:00",
            updatedAt = "2025-08-06 09:15:00",
            idJurusan = 3
        ),
        Asesi(
            idAsesi = 3,
            idUser = 103,
            namaLengkap = "Agus Prasetyo",
            noKtp = "3276010303030003",
            tempatLahir = "Surabaya",
            tanggalLahir = "1995-03-15",
            jenisKelamin = "Laki-laki",
            alamat = "Jl. Diponegoro No. 7, Surabaya",
            kodePos = "60241",
            noTelepon = "082145678901",
            email = "agus@example.com",
            kualifikasiPendidikan = "D3 Teknik Elektro",
            createdAt = "2025-08-03 08:00:00",
            updatedAt = "2025-08-07 16:45:00",
            idJurusan = 7
        )
    )


    val dummyFormApl01List = listOf(
        FormApl01(
            idApl01 = 1,
            idAsesmen = 101,
            namaLengkap = "Budi Santoso",
            noKtp = "3276010101010001",
            tempatLahir = "Bandung",
            tanggalLahir = "1990-05-12",
            jenisKelamin = "Laki-laki",
            kebangsaan = "Indonesia",
            alamat = "Jl. Merdeka No. 10, Bandung",
            kodePos = "40111",
            noRumah = "0221234567",
            noKantor = "0227654321",
            noHp = "081234567890",
            email = "budi@example.com",
            kualifikasiPendidikan = "S1 Teknik Informatika",
            institusi = "Universitas Teknologi Bandung",
            jabatan = "Programmer",
            alamatKantor = "Jl. Gatot Subroto No. 12, Bandung",
            kodePosKantor = "40251",
            telpKantor = "0229876543",
            faxKantor = "0229876544",
            emailKantor = "budi.kantor@example.com",
            createdAt = "2025-08-01 10:00:00"
        ),
        FormApl01(
            idApl01 = 2,
            idAsesmen = 102,
            namaLengkap = "Siti Aminah",
            noKtp = "3276010202020002",
            tempatLahir = "Jakarta",
            tanggalLahir = "1992-08-20",
            jenisKelamin = "Perempuan",
            kebangsaan = "Indonesia",
            alamat = "Jl. Sudirman No. 45, Jakarta",
            kodePos = "10220",
            noRumah = "0212345678",
            noKantor = "0218765432",
            noHp = "081298765432",
            email = "siti@example.com",
            kualifikasiPendidikan = "S2 Manajemen",
            institusi = "Universitas Indonesia",
            jabatan = "Manajer",
            alamatKantor = "Jl. Thamrin No. 21, Jakarta",
            kodePosKantor = "10350",
            telpKantor = "0217654321",
            faxKantor = "0217654322",
            emailKantor = "siti.kantor@example.com",
            createdAt = "2025-08-02 14:30:00"
        )
    )

    val dummyAsesmenList = listOf(
        Asesmen(
            idAsesmen = 101,
            idSkema = 10,
            idAsesi = 1,
            idAsesor = 5,
            tanggalMulai = "2025-08-05",
            tanggalSelesai = "2025-08-06",
            tuk = "TUK Bandung",
            status = "Menunggu Persetujuan",
            hasil = null,
            catatan = null,
            createdAt = "2025-08-01 09:00:00",
            updatedAt = "2025-08-01 09:00:00"
        ),
        Asesmen(
            idAsesmen = 102,
            idSkema = 12,
            idAsesi = 2,
            idAsesor = 6,
            tanggalMulai = "2025-08-07",
            tanggalSelesai = "2025-08-08",
            tuk = "TUK Jakarta",
            status = "Disetujui",
            hasil = "Kompeten",
            catatan = "Peserta menunjukkan kompetensi yang baik",
            createdAt = "2025-08-02 10:00:00",
            updatedAt = "2025-08-06 15:45:00"
        )
    )


    var logUser = 0

}