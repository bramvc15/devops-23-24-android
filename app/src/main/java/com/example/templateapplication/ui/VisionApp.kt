@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.templateapplication.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.example.templateapplication.navigation.AppNavigation
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templateapplication.ui.utils.VisionNavigationType


@Composable
fun VisionApp(windowSize: WindowWidthSizeClass,
modifier: Modifier = Modifier,
              ){
    val navigationType: VisionNavigationType
        when (windowSize) {
            WindowWidthSizeClass.Compact -> {
                navigationType = VisionNavigationType.BOTTOM_NAVIGATION
            }

            WindowWidthSizeClass.Medium -> {
                navigationType = VisionNavigationType.NAVIGATION_RAIL

            }

            WindowWidthSizeClass.Expanded -> {
                navigationType = VisionNavigationType.PERMANENT_NAVIGATION_DRAWER
            }

            else -> {
                navigationType = VisionNavigationType.BOTTOM_NAVIGATION
            }
        }
        AppNavigation(navigationType,
            modifier = modifier)
}