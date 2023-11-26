package com.example.templateapplication

import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templateapplication.data.AppContainer
import com.example.templateapplication.data.DefaultAppContainer
import com.example.templateapplication.models.DoctorViewModel
import com.example.templateapplication.navigation.AppNavigation
import com.example.templateapplication.ui.theme.VisionApplicationTheme


object Constants {
    lateinit var context: Context
     }
class MainActivity : ComponentActivity() {
    lateinit var container: AppContainer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        container = DefaultAppContainer()
        Constants.context = this
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        setContent {
            MyApp()
        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MyApp() {
    val doctorViewModel: DoctorViewModel = viewModel(factory = DoctorViewModel.Factory)

    VisionApplicationTheme {
        AppNavigation(doctorViewModel)
    }
}


