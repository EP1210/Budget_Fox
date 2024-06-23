package at.ac.fhcampuswien.budget_fox.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import at.ac.fhcampuswien.budget_fox.screens.CategoryScreen
import at.ac.fhcampuswien.budget_fox.screens.HouseholdCreateScreen
import at.ac.fhcampuswien.budget_fox.screens.HouseholdJoinScreen
import at.ac.fhcampuswien.budget_fox.screens.HouseholdTransactionScreen
import at.ac.fhcampuswien.budget_fox.screens.HouseholdWelcomeScreen
import at.ac.fhcampuswien.budget_fox.screens.LoginScreen
import at.ac.fhcampuswien.budget_fox.screens.RegistrationScreen
import at.ac.fhcampuswien.budget_fox.screens.StatisticsScreen
import at.ac.fhcampuswien.budget_fox.screens.TransactionCreateScreen
import at.ac.fhcampuswien.budget_fox.screens.TransactionScreen
import at.ac.fhcampuswien.budget_fox.screens.UserProfileScreen
import at.ac.fhcampuswien.budget_fox.screens.WelcomeScreen
import at.ac.fhcampuswien.budget_fox.view_models.HouseholdCreateViewModel
import at.ac.fhcampuswien.budget_fox.view_models.StatisticsViewModel
import at.ac.fhcampuswien.budget_fox.view_models.UserViewModel

@Composable
fun Navigation() {
    val navigationController = rememberNavController()
    val userViewModel: UserViewModel = viewModel()
    val statisticsViewModel: StatisticsViewModel = viewModel()
    val householdCreateViewModel: HouseholdCreateViewModel = viewModel()

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
            WelcomeScreen(
                navigationController = navigationController,
                viewModel = userViewModel
            )
        }
        composable(route = Screen.TransactionCreate.route) {
            TransactionCreateScreen(
                navigationController = navigationController,
                viewModel = userViewModel
            )
        }
        composable(route = Screen.Transaction.route) {
            TransactionScreen(
                navigationController = navigationController,
                route = Screen.Transaction.route,
                viewModel = userViewModel
            )
        }
        composable(route = Screen.Category.route) { backStackEntry ->
            CategoryScreen(
                navigationController = navigationController,
                viewModel = userViewModel,
                transactionId = backStackEntry.arguments?.getString(TRANSACTION_ID)
            )
        }
        composable(route = Screen.Statistics.route) {
            StatisticsScreen(
                navigationController = navigationController,
                route = Screen.Statistics.route,
                viewModel = statisticsViewModel
            )
        }
        composable(route = Screen.HouseholdWelcome.route) {
            HouseholdWelcomeScreen(
                navigationController = navigationController,
                route = Screen.HouseholdWelcome.route
            )
        }
        composable(route = Screen.HouseholdCreate.route) {
            HouseholdCreateScreen(
                navigationController = navigationController,
                viewModel = householdCreateViewModel
            )
        }
        composable(route = Screen.HouseholdJoin.route) {
            HouseholdJoinScreen(navigationController = navigationController)
        }
        composable(route = Screen.HouseholdTransaction.route) {
            HouseholdTransactionScreen(navigationController = navigationController)
        }
    }
}