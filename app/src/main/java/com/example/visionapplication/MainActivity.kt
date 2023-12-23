package com.example.visionapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.example.visionapplication.ui.VisionApp
import com.example.visionapplication.ui.screens.AuthScreen
import com.example.visionapplication.ui.theme.VisionApplicationTheme
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import com.example.visionapplication.ui.utils.VisionNavigationType
import com.example.visionapplication.ui.views.MainViewModel


/**
 * Main activity
 *
 * @constructor Create empty Main activity
 */
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        mainViewModel.setContext(this)

        Log.i("vm inspection", "Main activity onCreate")
        setContent {
            VisionApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (mainViewModel.userIsAuthenticated) {
                    val windowSize = calculateWindowSizeClass(this)
                    when (windowSize.widthSizeClass) {
                        WindowWidthSizeClass.Compact -> {
                            VisionApp(VisionNavigationType.BOTTOM_NAVIGATION)
                        }
                        WindowWidthSizeClass.Medium -> {
                            VisionApp(VisionNavigationType.NAVIGATION_RAIL)
                        }
                        WindowWidthSizeClass.Expanded -> {
                            VisionApp(navigationType = VisionNavigationType.PERMANENT_NAVIGATION_DRAWER)
                        }
                        else -> {
                            VisionApp(navigationType = VisionNavigationType.BOTTOM_NAVIGATION)
                        }
                    }

                    } else {
                        AuthScreen(mainViewModel)
                    }
            }
        }
    }
    }
}



