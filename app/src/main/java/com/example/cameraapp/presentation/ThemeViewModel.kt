package com.example.cameraapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor() : ViewModel() {
    var isDarkTheme by mutableStateOf(false)
        private set

    fun toggleTheme() {
        isDarkTheme = !isDarkTheme
    }
}
