package com.example.visionapplication.ui.views

import com.example.visionapplication.model.Doctor

data class VisionUiState(
    val success: List<Doctor>? = null,
    val Error: String? = null,
    val loading: Boolean = false,
    val selectedDoctor: Doctor? = null
) {

    companion object {
        val Loading = VisionUiState(loading = true)
    }
}