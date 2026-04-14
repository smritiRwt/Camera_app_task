package com.example.cameraapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.ContextCompat
import androidx.activity.viewModels
import com.example.cameraapp.presentation.navigation.AppNavGraph
import com.example.cameraapp.presentation.ThemeViewModel
import com.example.cameraapp.ui.theme.CameraAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ REQUEST PERMISSION HERE (BEFORE setContent)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
        }

        val themeViewModel: ThemeViewModel by viewModels()

        setContent {
            CameraAppTheme(darkTheme = themeViewModel.isDarkTheme) {
                AppNavGraph()
            }
        }
    }
}