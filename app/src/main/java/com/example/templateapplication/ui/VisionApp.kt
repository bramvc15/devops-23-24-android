@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.templateapplication.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templateapplication.navigation.AppNavigation
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import com.example.templateapplication.ui.utils.VisionNavigationType
import com.example.templateapplication.ui.views.DoctorViewModel

@Composable
fun VisionApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    val viewModel: DoctorViewModel = viewModel(factory = DoctorViewModel.Factory)
    val visionUiState = viewModel.uiState.collectAsState().value

    val navigationType: VisionNavigationType = when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            VisionNavigationType.BOTTOM_NAVIGATION
        }

        WindowWidthSizeClass.Medium -> {
            VisionNavigationType.NAVIGATION_RAIL
        }

        WindowWidthSizeClass.Expanded -> {
            VisionNavigationType.PERMANENT_NAVIGATION_DRAWER
        }

        else -> {
            VisionNavigationType.BOTTOM_NAVIGATION
        }
    }

    AppNavigation(navigationType,
                modifier = modifier,
                visionUiState = visionUiState,
        doctorViewModel = viewModel)
}
