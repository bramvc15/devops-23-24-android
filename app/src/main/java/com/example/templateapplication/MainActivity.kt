package com.example.templateapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.example.templateapplication.ui.VisionApp
import com.example.templateapplication.ui.theme.VisionApplicationTheme
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.templateapplication.ui.utils.VisionNavigationType


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("vm inspection", "Main activity onCreate")
        setContent {
            VisionApplicationTheme {
                Surface{
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
                }
            }
        }
    }
}


