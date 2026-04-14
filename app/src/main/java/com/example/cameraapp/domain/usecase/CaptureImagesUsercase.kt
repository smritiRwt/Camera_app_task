package com.example.cameraapp.domain.usecase

import android.content.Context
import androidx.camera.core.CameraControl
import androidx.camera.core.ImageCapture
import com.example.cameraapp.domain.repository.CameraRepository
import kotlinx.coroutines.delay
import javax.inject.Inject


class CaptureImagesUseCase @Inject constructor(
    private val repository: CameraRepository
) {
    suspend operator fun invoke(
        imageCapture: ImageCapture,
        cameraControl: CameraControl,
        context: Context
    ): List<String> {

        val zoomLevels = listOf(1f, 2f, 3f)
        val images = mutableListOf<String>()

        for (zoom in zoomLevels) {
            cameraControl.setZoomRatio(zoom)
            delay(300)

            val path = repository.captureImage(imageCapture, context, zoom)
            path?.let { images.add(it) }
        }

        return images
    }
}