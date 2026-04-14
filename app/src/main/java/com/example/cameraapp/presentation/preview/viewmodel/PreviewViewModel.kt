package com.example.cameraapp.presentation.preview.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.cameraapp.domain.model.ImageModel
import com.example.cameraapp.domain.repository.CameraRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PreviewViewModel @Inject constructor(
    private val repository: CameraRepository
) : ViewModel() {

    var images by mutableStateOf<List<ImageModel>>(emptyList())
        private set

    // 🔹 Load all saved images (only once)
    fun loadSavedImages(context: Context) {
        images = repository.getSavedImages(context)
    }

    private fun extractZoomLabel(filename: String): String {
        return try {
            val zoomValue = filename.substringAfter("_zoom_", "1.0").substringBefore(".jpg")
            "${zoomValue.toDouble().toInt()}x"
        } catch (e: Exception) {
            "1x"
        }
    }

    // 🔹 Add new images (from camera)
    fun addNewImages(context: Context, newImages: List<String>) {
        val updated = newImages.map {
            ImageModel(
                path = it,
                zoomLabel = extractZoomLabel(it.substringAfterLast("/"))
            )
        }

        images = (updated + images).distinctBy { it.path }
    }

    // 🔹 Delete image
    fun deleteImage(image: ImageModel) {
        if (repository.deleteImage(image.path)) {
            images = images.filterNot { it.path == image.path }
        }
    }
}