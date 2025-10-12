package com.example.mylsp.common.helper.barcode

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.viewinterop.AndroidView
import java.io.File
import java.io.FileOutputStream

@Composable
fun CaptureBox(
    controller: CaptureController,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    AndroidView(
        factory = { context ->
            val composeView = ComposeView(context)
            composeView.setContent(content)
            controller.setView(composeView)
            composeView
        },
        modifier = Modifier
            .graphicsLayer()
    )
}

class CaptureController {
    private var view: View? = null
    fun setView(v: View) {
        view = v
    }
    fun capture(onCaptured: (Bitmap) -> Unit) {
        view?.let {
            val bitmap = Bitmap.createBitmap(it.width, it.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            it.draw(canvas)
            onCaptured(bitmap)
        }
    }
}

@Composable
fun rememberCaptureController() = remember { CaptureController() }

fun saveBitmapToPNG(context: Context, bitmap: Bitmap) {
    val filename = "signature_${System.currentTimeMillis()}.png"
    val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), filename)

    FileOutputStream(file).use { out ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
    }

    Toast.makeText(context, "Tersimpan di: ${file.absolutePath}", Toast.LENGTH_LONG).show()
}

fun saveBitmapToUri(context: Context, uri: Uri, bitmap: Bitmap) {
    context.contentResolver.openOutputStream(uri)?.use { out ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
    }
    Toast.makeText(context, "Tersimpan di: $uri", Toast.LENGTH_LONG).show()
}


