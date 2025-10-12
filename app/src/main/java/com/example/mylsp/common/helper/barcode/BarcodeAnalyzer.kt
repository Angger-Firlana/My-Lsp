package com.example.mylsp.common.helper.barcode

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.common.HybridBinarizer

class BarcodeAnalyzer(
    private val onBarcodeDetected: (String) -> Unit
) : ImageAnalysis.Analyzer {

    private val reader: MultiFormatReader = MultiFormatReader().apply {
        val hints = mapOf(
            DecodeHintType.TRY_HARDER to true, // Mode agresif
            DecodeHintType.POSSIBLE_FORMATS to BarcodeFormat.values().toList()
        )
        setHints(hints)
    }

    override fun analyze(imageProxy: ImageProxy) {
        try {
            val buffer = imageProxy.planes[0].buffer
            val bytes = ByteArray(buffer.remaining())
            buffer.get(bytes)

            val source = PlanarYUVLuminanceSource(
                bytes,
                imageProxy.width,
                imageProxy.height,
                0,
                0,
                imageProxy.width,
                imageProxy.height,
                false
            )

            val bitmap = BinaryBitmap(HybridBinarizer(source))
            val result = reader.decodeWithState(bitmap)

            result?.text?.let {
                onBarcodeDetected(it)
            }
        } catch (e: Exception) {
            // Tidak terdeteksi
        } finally {
            imageProxy.close()
        }
    }
}
