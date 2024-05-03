package at.ac.fhcampuswien.budget_fox.navigation

sealed class Screen(val route: String) {
    data object Registration : Screen(route = "registration_screen")
    data object Login : Screen(route = "login_screen")
    data object UserProfile : Screen(route = "user_profile_screen")
    data object Welcome : Screen(route = "welcome_screen")
    data object IncomeExpense : Screen(route = "income_expense_screen")
    data object TransactionList: Screen(route = "transaction_list_screen")
}