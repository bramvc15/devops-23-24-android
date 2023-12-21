package com.example.templateapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.example.templateapplication.ui.VisionApp
import com.example.templateapplication.ui.screens.AuthScreen
import com.example.templateapplication.ui.theme.VisionApplicationTheme
import com.example.templateapplication.ui.views.MainViewModel


class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        mainViewModel.setContext(this)

        setContent {
            VisionApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (mainViewModel.userIsAuthenticated) {
                        VisionApp()
                    } else {
                        AuthScreen(mainViewModel)
                    }
                }
            }
        }
    }
}
