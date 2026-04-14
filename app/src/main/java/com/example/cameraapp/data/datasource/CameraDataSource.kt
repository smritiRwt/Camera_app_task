package com.example.cameraapp.data.datasource

import android.content.Context
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.content.ContextCompat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File

class CameraDataSource {

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun capture(
        imageCapture: ImageCapture,
        context: Context,
        zoom: Float
    ): String? = suspendCancellableCoroutine { cont ->

        val directory = context.getExternalFilesDir(null) ?: context.filesDir
        val file = File(
            directory,
            "IMG_${System.currentTimeMillis()}_zoom_$zoom.jpg"
        )

        val options = ImageCapture.OutputFileOptions.Builder(file).build()

        imageCapture.takePicture(
            options,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    cont.resume(file.absolutePath, null)
                }

                override fun onError(exception: ImageCaptureException) {
                    cont.resume(null, null)
                }
            }
        )
    }
}