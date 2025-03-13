package com.senthilkumaar.dogapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.senthilkumaar.dogapp.screen.Splash.SplashScreen
import com.senthilkumaar.dogapp.screen.detail.DogDetailScreen
import com.senthilkumaar.dogapp.screen.list.DogListScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "splash_screen") {  // Start with SplashScreen
        composable("splash_screen") {
            SplashScreen(navController)
        }

        composable(Screen.DogList.route) {
            DogListScreen(navController, viewModel = hiltViewModel())
        }

        composable(
            route = Screen.DogDetail.route,
            arguments = listOf(navArgument("dogId") { type = NavType.IntType })
        ) { backStackEntry ->
            val dogId = backStackEntry.arguments?.getInt("dogId") ?: 0
            DogDetailScreen(
                dogId = dogId,
                viewModel = hiltViewModel(),
                navController = navController
            )
        }
    }
}