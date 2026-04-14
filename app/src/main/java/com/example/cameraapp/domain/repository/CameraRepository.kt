package com.example.cameraapp.domain.repository

import android.content.Context
import androidx.camera.core.ImageCapture

import com.example.cameraapp.domain.model.ImageModel

interface CameraRepository {
    suspend fun captureImage(
        imageCapture: ImageCapture,
        context: Context,
        zoom: Float
    ): String?

    fun getSavedImages(context: Context): List<ImageModel>
    fun deleteImage(path: String): Boolean
}
