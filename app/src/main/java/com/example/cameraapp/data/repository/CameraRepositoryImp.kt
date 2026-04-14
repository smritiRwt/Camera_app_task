package com.example.cameraapp.data.repository

import android.content.Context
import androidx.camera.core.ImageCapture
import com.example.cameraapp.data.datasource.CameraDataSource
import com.example.cameraapp.domain.model.ImageModel
import com.example.cameraapp.domain.repository.CameraRepository
import java.io.File

class CameraRepositoryImpl(
    private val dataSource: CameraDataSource
) : CameraRepository {

    override suspend fun captureImage(
        imageCapture: ImageCapture,
        context: Context,
        zoom: Float
    ): String? {
        return dataSource.capture(imageCapture, context, zoom)
    }

    override fun getSavedImages(context: Context): List<ImageModel> {
        val directory = context.getExternalFilesDir(null) ?: context.filesDir
        return directory.listFiles()?.filter {
            it.extension == "jpg" && it.name.startsWith("IMG_")
        }?.sortedByDescending {
            it.lastModified()
        }?.map {
            ImageModel(
                path = it.absolutePath,
                zoomLabel = extractZoomLabel(it.name)
            )
        } ?: emptyList()
    }

    override fun deleteImage(path: String): Boolean {
        return File(path).delete()
    }

    private fun extractZoomLabel(filename: String): String {
        return try {
            val zoomValue = filename.substringAfter("_zoom_", "1.0").substringBefore(".jpg")
            "${zoomValue.toDouble().toInt()}x"
        } catch (e: Exception) {
            "1x"
        }
    }
}
