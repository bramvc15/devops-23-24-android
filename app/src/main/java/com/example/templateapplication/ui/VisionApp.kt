@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.templateapplication.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templateapplication.navigation.AppNavigation
import com.example.templateapplication.ui.screens.HomeScreen
import com.example.templateapplication.ui.views.DoctorViewModel


@Composable
fun VisionApp(){
    Scaffold(
      modifier = Modifier

    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val doctorViewModel: DoctorViewModel = viewModel()
            HomeScreen(doctorUiState = doctorViewModel.doctorUiState )
            //StartNav(doctorUiState =doctorViewModel.doctorUiState, modifier = Modifier)
            AppNavigation(doctorUiState =doctorViewModel.doctorUiState, modifier = Modifier)
        }
    }
}