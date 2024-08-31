package com.example.mealorganizer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mealorganizer.ui.screens.home.HomeScreen
import com.example.mealorganizer.ui.screens.login_singup.GreetingScreen
import com.example.mealorganizer.ui.screens.login_singup.LogInScreen
import com.example.mealorganizer.ui.screens.login_singup.SignUpScreen
import com.example.mealorganizer.ui.screens.profile.ProfileInfoScreen

@Composable
fun HomeNavigation(
    navController: NavHostController,
    startRoute: String = NavigationRoutesEnum.GREETING_SCREEN.name
) {
    NavHost(
        navController = navController,
        startDestination = startRoute
    ) {
        composable(route = NavigationRoutesEnum.GREETING_SCREEN.name) {
            GreetingScreen(navController = navController)
        }

        composable(route = NavigationRoutesEnum.HOME_SCREEN.name) {
            HomeScreen(navController = navController)
        }

        composable(route = NavigationRoutesEnum.PROFILE_INFO_SCREEN.name) {
            ProfileInfoScreen(navController = navController)
        }

        composable(route = NavigationRoutesEnum.SIGN_UP_SCREEN.name) {
            SignUpScreen(navController = navController)
        }

        composable(route = NavigationRoutesEnum.LOG_IN_SCREEN.name) {
            LogInScreen(navController = navController)
        }

        composable(route = NavigationRoutesEnum.PROFILE_INFO.name) {
            ProfileInfoScreen(navController = navController)
        }
    }
}