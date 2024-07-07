package at.ac.fhcampuswien.budget_fox.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import at.ac.fhcampuswien.budget_fox.screens.CategoriesStatisticsScreen
import at.ac.fhcampuswien.budget_fox.screens.CategoryScreen
import at.ac.fhcampuswien.budget_fox.screens.HouseholdCreateScreen
import at.ac.fhcampuswien.budget_fox.screens.HouseholdJoinScreen
import at.ac.fhcampuswien.budget_fox.screens.HouseholdSettingsScreen
import at.ac.fhcampuswien.budget_fox.screens.HouseholdTransactionAddScreen
import at.ac.fhcampuswien.budget_fox.screens.HouseholdTransactionScreen
import at.ac.fhcampuswien.budget_fox.screens.HouseholdWelcomeScreen
import at.ac.fhcampuswien.budget_fox.screens.LoginScreen
import at.ac.fhcampuswien.budget_fox.screens.RegistrationScreen
import at.ac.fhcampuswien.budget_fox.screens.RegularTransactionScreen
import at.ac.fhcampuswien.budget_fox.screens.SavingGoalAddScreen
import at.ac.fhcampuswien.budget_fox.screens.SavingGoalOverviewScreen
import at.ac.fhcampuswien.budget_fox.screens.StatisticsScreen
import at.ac.fhcampuswien.budget_fox.screens.TransactionCreateScreen
import at.ac.fhcampuswien.budget_fox.screens.TransactionListScreen
import at.ac.fhcampuswien.budget_fox.screens.UserProfileScreen
import at.ac.fhcampuswien.budget_fox.screens.WelcomeScreen

@Composable
fun Navigation() {
    val navigationController = rememberNavController()

    NavHost(
        navController = navigationController,
        startDestination = Screen.Welcome.route
    ) {
        composable(route = Screen.Registration.route) {
            RegistrationScreen(
                navigationController = navigationController
            )
        }
        composable(route = Screen.Login.route) {
            LoginScreen(
                navigationController = navigationController
            )
        }
        composable(route = Screen.UserProfile.route) { backStackEntry ->
            UserProfileScreen(
                navigationController = navigationController,
                route = Screen.UserProfile.route,
                userId = backStackEntry.arguments?.getString(USER_ID)
            )
        }
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(
                navigationController = navigationController
            )
        }
        composable(route = Screen.TransactionCreate.route) { backStackEntry ->
            TransactionCreateScreen(
                navigationController = navigationController,
                userId = backStackEntry.arguments?.getString(USER_ID)
            )
        }
        composable(route = Screen.TransactionList.route) { backStackEntry ->
            TransactionListScreen(
                navigationController = navigationController,
                route = Screen.TransactionList.route,
                userId = backStackEntry.arguments?.getString(USER_ID)
            )
        }
        composable(route = Screen.Category.route) { backStackEntry ->
            CategoryScreen(
                navigationController = navigationController,
                userId = backStackEntry.arguments?.getString(USER_ID),
                transactionId = backStackEntry.arguments?.getString(TRANSACTION_ID)
            )
        }
        composable(route = Screen.Statistics.route) { backStackEntry ->
            StatisticsScreen(
                navigationController = navigationController,
                route = Screen.Statistics.route,
                userId = backStackEntry.arguments?.getString(USER_ID)
            )
        }
        composable(route = Screen.CategoriesStatistics.route) { backStackEntry ->
            CategoriesStatisticsScreen(
                navigationController = navigationController,
                userId = backStackEntry.arguments?.getString(USER_ID)
            )
        }
        composable(route = Screen.HouseholdWelcome.route) { backStackEntry ->
            HouseholdWelcomeScreen(
                navigationController = navigationController,
                route = Screen.HouseholdWelcome.route,
                userId = backStackEntry.arguments?.getString(USER_ID)
            )
        }
        composable(route = Screen.HouseholdCreate.route) { backStackEntry ->
            HouseholdCreateScreen(
                navigationController = navigationController,
                userId = backStackEntry.arguments?.getString(USER_ID)
            )
        }
        composable(route = Screen.HouseholdJoin.route) { backStackEntry ->
            HouseholdJoinScreen(
                navigationController = navigationController,
                userId = backStackEntry.arguments?.getString(USER_ID)
            )
        }
        composable(route = Screen.HouseholdTransaction.route) { backStackEntry ->
            HouseholdTransactionScreen(
                navigationController = navigationController,
                route = Screen.HouseholdTransaction.route,
                householdId = backStackEntry.arguments?.getString(HOUSEHOLD_ID),
                userId = backStackEntry.arguments?.getString(USER_ID)
            )
        }
        composable(route = Screen.HouseholdAddTransaction.route) { backStackEntry ->
            HouseholdTransactionAddScreen(
                navigationController = navigationController,
                householdId = backStackEntry.arguments?.getString(HOUSEHOLD_ID)
            )
        }
        composable(route = Screen.HouseholdSettings.route) { backStackEntry ->
            HouseholdSettingsScreen(
                householdId = backStackEntry.arguments?.getString(HOUSEHOLD_ID),
                userId = backStackEntry.arguments?.getString(USER_ID),
                navigationController = navigationController
            )
        }
        composable(route = Screen.RegularTransaction.route) { backStackEntry ->
            RegularTransactionScreen(
                navigationController = navigationController,
                userId = backStackEntry.arguments?.getString(USER_ID)
            )
        }
        composable(route = Screen.SavingGoalOverview.route) { backStackEntry ->
            SavingGoalOverviewScreen(
                navController = navigationController,
                userId = backStackEntry.arguments?.getString(USER_ID)
            )
        }
        composable(route = Screen.SavingGoalAdd.route) { backStackEntry ->
            SavingGoalAddScreen(
                navController = navigationController,
                userId = backStackEntry.arguments?.getString(USER_ID)
            )
        }
    }
}