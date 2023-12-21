package com.example.templateapplication.ui.views

import com.example.templateapplication.model.Doctor

data class VisionUiState(
    val success: List<Doctor>? = null,
    val error: String? = null,
    val loading: Boolean = false
) {
    companion object {
        val Loading = VisionUiState(loading = true)
    }
}