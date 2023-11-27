package com.example.templateapplication.navigation

/*
@Composable
fun StartNav(doctorUiState: DoctorUiState, modifier: Modifier){

    val navController: NavHostController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold {
            paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.DoctorSelectionScreen.name,
            modifier = Modifier
                .padding(paddingValues)
        ) {
            composable(route = Screens.DoctorSelectionScreen.name) {

                DoctorSelectionScreen(doctorUiState)

            }
            composable(route = Screens.PasswordScreen.name) {
                PasswordScreen()
            }
        }
    }

}*/