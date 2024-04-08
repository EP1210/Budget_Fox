package at.ac.fhcampuswien.budget_fox.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import at.ac.fhcampuswien.budget_fox.screens.LoginScreen
import at.ac.fhcampuswien.budget_fox.screens.RegistrationScreen
import at.ac.fhcampuswien.budget_fox.screens.UserProfileScreen
import at.ac.fhcampuswien.budget_fox.screens.WelcomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route //TODO: Start screen
    ) {
        composable(route = Screen.Registration.route) {
            RegistrationScreen(navController)
        }
        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.UserProfile.route) {
            UserProfileScreen(navController = navController)
        }
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navController = navController)
        }
    }
}