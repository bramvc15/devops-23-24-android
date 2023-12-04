@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.templateapplication.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.example.templateapplication.navigation.AppNavigation
import com.example.templateapplication.ui.views.DoctorViewModel


@Composable
fun VisionApp(){
    val doctorViewModel = DoctorViewModel()
    AppNavigation(doctorViewModel = doctorViewModel)
}
