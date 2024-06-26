package at.ac.fhcampuswien.budget_fox.navigation

const val HOUSEHOLD_ID = "HOUSEHOLD"
const val USER_ID = "USER"

sealed class Screen(val route: String) {
    data object Registration : Screen(route = "registration_screen")
    data object Login : Screen(route = "login_screen")
    data object UserProfile : Screen(route = "user_profile_screen")
    data object Welcome : Screen(route = "welcome_screen")
    data object Transaction : Screen(route = "transaction_screen")
    data object TransactionList : Screen(route = "transaction_list_screen")
    data object Category : Screen(route = "category_screen")
    data object Statistics : Screen(route = "statistics_screen")
    data object HouseholdWelcome : Screen(route = "household_welcome_screen")
    data object HouseholdJoin : Screen(route = "household_join_screen")
    data object HouseholdCreate : Screen(route = "household_register_screen")
    data object HouseholdTransaction : Screen(route = "household_transaction_screen")
    data object RegularTransaction : Screen(route = "regular_transaction_screen")
    data object HouseholdAddTransaction : Screen(route = "household_add_transaction_screen/{${HOUSEHOLD_ID}}") {
        fun setHouseholdId(householdId: String): String {
            return this.route.replace(oldValue = "{$HOUSEHOLD_ID}", householdId)
        }
    }
    data object HouseholdSettings : Screen(route = "household_settings_screen/{$HOUSEHOLD_ID}/{${USER_ID}}") {
        fun setArguments(householdId: String, userId: String): String {
            return this.route.replace(oldValue = "{$HOUSEHOLD_ID}", householdId).replace(oldValue = "{$USER_ID}", userId)
        }

    }
}