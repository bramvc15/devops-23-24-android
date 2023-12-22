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


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("vm inspection", "Main activity onCreate")
        setContent {
            VisionApplicationTheme {
                Surface{
                    val windowSize = calculateWindowSizeClass(this)


                    VisionApp(windowSize = windowSize.widthSizeClass)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReplyAppPreview() {
    VisionApplicationTheme {
        Surface {
            VisionApp(
                windowSize = WindowWidthSizeClass.Compact
            )
        }
    }
}
@Preview(showBackground = true, widthDp = 700)
@Composable
fun ReplyAppMediumPreview() {
    VisionApplicationTheme {
        Surface {
            VisionApp(
                windowSize = WindowWidthSizeClass.Medium,
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 1000)
@Composable
fun ReplyAppExpandedPreview() {
    VisionApplicationTheme {
        Surface {
            VisionApp(
                windowSize = WindowWidthSizeClass.Expanded,
            )
        }
    }
}


