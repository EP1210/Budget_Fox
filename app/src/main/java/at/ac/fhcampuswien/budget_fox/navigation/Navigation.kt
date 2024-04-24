package at.ac.fhcampuswien.budget_fox.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import at.ac.fhcampuswien.budget_fox.screens.LoginScreen
import at.ac.fhcampuswien.budget_fox.screens.RegistrationScreen
import at.ac.fhcampuswien.budget_fox.screens.UserProfileScreen
import at.ac.fhcampuswien.budget_fox.screens.WelcomeScreen
import at.ac.fhcampuswien.budget_fox.view_models.UserViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val userViewModel: UserViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
    ) {
        composable(route = Screen.Registration.route) {
            Log.d("NAV", "Registration Screen")
            RegistrationScreen(
                navController = navController,
                viewModel = userViewModel
            )
        }
        composable(route = Screen.Login.route) {
            Log.d("NAV", "Login Screen")
            LoginScreen(
                navController = navController,
                //viewModel = userViewModel
            )
        }
        composable(route = Screen.UserProfile.route) {
            Log.d("NAV", "User Profile Screen")
            UserProfileScreen(
                navController = navController,
                viewModel = userViewModel
            )
        }
        composable(route = Screen.Welcome.route) {
            Log.d("NAV", "Welcome Screen")
            WelcomeScreen(navController = navController)
        }
    }
}