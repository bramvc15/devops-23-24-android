package com.example.templateapplication

import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.templateapplication.components.HomeIntroSection
import com.example.templateapplication.components.HomeNewsSection
import com.example.templateapplication.navigation.AppNavigation
import com.example.templateapplication.navigation.BottomNavigation
import com.example.templateapplication.screens.HomeScreen
import com.example.templateapplication.ui.theme.VisionApplicationTheme


object Constants {
    lateinit var context: Context
     }



class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Constants.context = this
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)


        setContent {
            VisionApplicationTheme {
                val windowSizeClass = calculateWindowSizeClass(this)
                VisionApp(windowSizeClass)
                AppNavigation()

            }


        }
    }
}




@Composable
fun VisionAppPortrait() {
    VisionApplicationTheme {
        Scaffold(
            bottomBar = {

                BottomNavigation()
            }
        ) { padding ->
            HomeScreen(Modifier.padding(padding))
        }
    }
}

@Composable
fun VisionApp(windowSize: WindowSizeClass) {
    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            VisionAppPortrait()
        }
        WindowWidthSizeClass.Expanded -> {
            //VisionAppLandscape()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeIntroSectionPreview() {
    HomeIntroSection()
}

@Preview(showBackground = true)
@Composable
fun HomeNewsSectionPreview() {
    HomeNewsSection()
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun BottomNavigationPreview() {
    VisionApplicationTheme { BottomNavigation() }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun ScreenContentPreview() {
    VisionApplicationTheme { HomeScreen() }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun VisionPortraitPreview() {
    VisionAppPortrait()
}
