package at.ac.fhcampuswien.budget_fox.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import at.ac.fhcampuswien.budget_fox.screens.IncomeExpenseScreen
import at.ac.fhcampuswien.budget_fox.screens.LoginScreen
import at.ac.fhcampuswien.budget_fox.screens.RegistrationScreen
import at.ac.fhcampuswien.budget_fox.screens.UserProfileScreen
import at.ac.fhcampuswien.budget_fox.screens.WelcomeScreen
import at.ac.fhcampuswien.budget_fox.view_models.UserViewModel

@Composable
fun Navigation() {
    val navigationController = rememberNavController()
    val userViewModel: UserViewModel = viewModel()

    NavHost(
        navController = navigationController,
        startDestination = Screen.Welcome.route
    ) {
        composable(route = Screen.Registration.route) {
            RegistrationScreen(
                navigationController = navigationController,
                viewModel = userViewModel
            )
        }
        composable(route = Screen.Login.route) {
            LoginScreen(
                navigationController = navigationController,
                viewModel = userViewModel
            )
        }
        composable(route = Screen.UserProfile.route) {
            UserProfileScreen(
                navigationController = navigationController,
                route = Screen.UserProfile.route,
                viewModel = userViewModel
            )
        }
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navigationController = navigationController)
        }
        composable(route = Screen.IncomeExpense.route) {
            IncomeExpenseScreen(
                navigationController = navigationController,
                route = Screen.IncomeExpense.route,
                viewModel = userViewModel
            )
        }
    }
}