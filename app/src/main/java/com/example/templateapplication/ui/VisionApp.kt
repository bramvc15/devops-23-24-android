@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.templateapplication.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.example.templateapplication.component.NavigationBar
import com.example.templateapplication.navigation.AppNavigation


@Composable
fun VisionApp(){

    NavigationBar()
    AppNavigation()
}
