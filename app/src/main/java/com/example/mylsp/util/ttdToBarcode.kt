package com.example.mylsp.util

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Base64
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import java.io.ByteArrayOutputStream
import java.security.MessageDigest
import java.util.UUID
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set
import com.google.zxing.common.BitMatrix

fun bitmapToBase64(bitmap: Bitmap): String {
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    val byteArray = outputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

fun generateSecureIdFromUri(uriString: String, secretKey: String = "RahasiaAnggerKun"): String {
    val salt = UUID.randomUUID().toString() // bikin ID beda tiap file
    val input = "$uriString$secretKey$salt"
    val digest = MessageDigest.getInstance("SHA-256")
    val hashBytes = digest.digest(input.toByteArray())
    return hashBytes.joinToString("") { "%02x".format(it) }
}

fun generateQRCode(content: String, size: Int = 512): Bitmap {
    if (content.isEmpty()) {
        throw IllegalArgumentException("QR content cannot be empty")
    }

    val bitMatrix: BitMatrix = MultiFormatWriter().encode(
        content,
        BarcodeFormat.QR_CODE,
        size,
        size
    )
    val bmp = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565)
    for (x in 0 until size) {
        for (y in 0 until size) {
            bmp.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
        }
    }
    return bmp
}

