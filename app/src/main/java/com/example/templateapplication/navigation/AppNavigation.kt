package com.example.templateapplication.navigation

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.templateapplication.screens.DoctorsScreen
import com.example.templateapplication.screens.HomeScreen
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.templateapplication.models.DoctorViewModel
import com.example.templateapplication.screens.BlogsScreen
import com.example.templateapplication.screens.DoctorDetailScreen


@Composable
fun AppNavigation(){

    val navController : NavHostController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {


                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                listOfNavItems.forEach { navItem ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == navItem.route} == true,
                        onClick = { navController.navigate(navItem.route){
                                    popUpTo(navController.graph.findStartDestination().id){
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                        } },
                        icon = {
                            Icon(imageVector = navItem.icon, contentDescription = null)
                        },
                        label = {
                            Text(text = navItem.label)
                        }
                    )
                }
            }
        }
    ) {paddingValues ->
        NavHost (
            navController = navController,
            startDestination = Screens.HomeScreen.name,
            modifier = Modifier
                .padding(paddingValues)
        ){
            composable(route = Screens.HomeScreen.name) {
                HomeScreen()
            }
            composable(route = Screens.DoctorsScreen.name) {
                DoctorsScreen(navController)
            }
            composable(route = Screens.BlogsScreen.name) {
                BlogsScreen()
            }
            composable(
                route = "${Screens.DoctorDetailScreen.name}/{id}",
                arguments = listOf(navArgument("id") { type = NavType.LongType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getLong("id")
                val viewModel: DoctorViewModel = viewModel()

                if (id != null) {
                    viewModel.getDoctorById(id.toInt())
                }

                val selectedDoctor = viewModel.selectedDoctor.value

                if (selectedDoctor != null) {
                    Log.d("doctor", "${selectedDoctor.name}")
                }

                if (selectedDoctor != null) {
                    DoctorDetailScreen( selectedDoctor = selectedDoctor)
                }


            }



        }
    }

}