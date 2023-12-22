@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.templateapplication.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templateapplication.navigation.AppNavigation
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import com.example.templateapplication.ui.utils.VisionContentType
import com.example.templateapplication.ui.utils.VisionNavigationType
import com.example.templateapplication.ui.views.DoctorViewModel

@Composable
fun VisionApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    val navigationType: VisionNavigationType
    val contentType: VisionContentType
    val viewModel: DoctorViewModel = viewModel(factory = DoctorViewModel.Factory)
    val visionUiState = viewModel.uiState.collectAsState().value

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            navigationType = VisionNavigationType.BOTTOM_NAVIGATION
            contentType = VisionContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Medium -> {
            navigationType = VisionNavigationType.NAVIGATION_RAIL
            contentType = VisionContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Expanded -> {
            navigationType = VisionNavigationType.PERMANENT_NAVIGATION_DRAWER
            contentType = VisionContentType.LIST_AND_DETAIL
        }
        else -> {
            navigationType = VisionNavigationType.BOTTOM_NAVIGATION
            contentType = VisionContentType.LIST_ONLY
        }
    }

    AppNavigation(
                modifier = modifier,
                visionUiState = visionUiState,
        doctorViewModel = viewModel,
        navigationType = navigationType,
        contentType = contentType,)
}
