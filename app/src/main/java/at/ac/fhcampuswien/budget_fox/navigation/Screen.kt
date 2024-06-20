package at.ac.fhcampuswien.budget_fox.navigation

const val TRANSACTION_ID = "transactionId"

sealed class Screen(val route: String) {
    data object Registration : Screen(route = "registration_screen")
    data object Login : Screen(route = "login_screen")
    data object UserProfile : Screen(route = "user_profile_screen")
    data object Welcome : Screen(route = "welcome_screen")
    data object Transaction : Screen(route = "transaction_screen")
    data object TransactionList : Screen(route = "transaction_list_screen")
    data object Category : Screen(route = "category_screen/{$TRANSACTION_ID}") {
        fun passTransactionId(transactionId: String): String {
            return this.route.replace(oldValue = "{$TRANSACTION_ID}", newValue = transactionId)
        }
    }
    data object Statistics : Screen(route = "statistics_screen")
    data object HouseholdWelcome : Screen(route = "household_welcome_screen")
    data object HouseholdJoin : Screen(route = "household_join_screen")
    data object HouseholdCreate : Screen(route = "household_register_screen")
    data object HouseholdTransaction : Screen(route = "household_transaction_screen")
}