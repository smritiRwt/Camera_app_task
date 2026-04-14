package com.example.cameraapp.presentation.camera.viewmodel

import android.content.Context
import androidx.camera.core.CameraControl
import androidx.camera.core.ImageCapture
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cameraapp.domain.usecase.CaptureImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val useCase: CaptureImagesUseCase
) : ViewModel() {

    var isLoading by mutableStateOf(false)
        private set

    var currentZoomStatus by mutableStateOf<String?>(null)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun captureImages(
        imageCapture: ImageCapture,
        cameraControl: CameraControl,
        context: Context,
        onDone: (List<String>) -> Unit
    ) {
        if (isLoading) return

        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                // UseCase handles the sequence of captures at different zoom levels
                val result = useCase(imageCapture, cameraControl, context)
                if (result.isNotEmpty()) {
                    onDone(result)
                } else {
                    throw Exception("No images were captured")
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "An unknown error occurred"
            } finally {
                isLoading = false
            }
        }
    }
}
