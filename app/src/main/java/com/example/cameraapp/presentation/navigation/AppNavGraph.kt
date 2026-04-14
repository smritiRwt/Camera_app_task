package com.example.cameraapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cameraapp.presentation.camera.CameraScreen
import com.example.cameraapp.presentation.preview.ImagePreviewScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "preview") {

        composable("preview") {
            ImagePreviewScreen(navController)
        }

        composable("camera") {
            CameraScreen(navController)
        }
    }
}
