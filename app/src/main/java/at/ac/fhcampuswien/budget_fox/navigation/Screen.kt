package at.ac.fhcampuswien.budget_fox.navigation

const val TRANSACTION_ID = "transactionId"
const val HOUSEHOLD_ID = "HOUSEHOLD"
const val USER_ID = "USER"

sealed class Screen(val route: String) {
    data object Registration : Screen(route = "registration_screen")
    data object Login : Screen(route = "login_screen")
    data object UserProfile : Screen(route = "user_profile_screen/{$USER_ID}") {
        fun passUserId(userId: String): String {
            return this.route.replace(oldValue = "{$USER_ID}", userId)
        }
    }

    data object Welcome : Screen(route = "welcome_screen")
    data object TransactionCreate : Screen(route = "transaction_create_screen/{$USER_ID}") {
        fun passUserId(userId: String): String {
            return this.route.replace(oldValue = "{$USER_ID}", userId)
        }
    }

    data object TransactionList : Screen(route = "transaction_list_screen/{$USER_ID}") {
        fun passUserId(userId: String): String {
            return this.route.replace(oldValue = "{$USER_ID}", userId)
        }
    }

    data object Category : Screen(route = "category_screen/{$USER_ID}/{$TRANSACTION_ID}") {
        fun setArguments(userId: String, transactionId: String): String {
            return this.route.replace(oldValue = "{$USER_ID}", newValue = userId)
                .replace(oldValue = "{$TRANSACTION_ID}", newValue = transactionId)
        }
    }

    data object Statistics : Screen(route = "statistics_screen/{$USER_ID}") {
        fun passUserId(userId: String): String {
            return this.route.replace(oldValue = "{$USER_ID}", userId)
        }
    }

    data object CategoriesStatistics : Screen(route = "categories_statistics_screen/{$USER_ID}") {
        fun passUserId(userId: String): String {
            return this.route.replace(oldValue = "{$USER_ID}", userId)
        }
    }

    data object HouseholdWelcome : Screen(route = "household_welcome_screen/{$USER_ID}") {
        fun passUserId(userId: String): String {
            return this.route.replace(oldValue = "{$USER_ID}", userId)
        }
    }

    data object HouseholdJoin : Screen(route = "household_join_screen/{$USER_ID}") {
        fun passUserId(userId: String): String {
            return this.route.replace(oldValue = "{$USER_ID}", userId)
        }
    }

    data object HouseholdCreate : Screen(route = "household_register_screen/{$USER_ID}") {
        fun passUserId(userId: String): String {
            return this.route.replace(oldValue = "{$USER_ID}", userId)
        }
    }

    data object HouseholdTransaction :
        Screen(route = "household_transaction_screen/{$HOUSEHOLD_ID}/{$USER_ID}") {
        fun passHouseholdId(householdId: String, userId: String): String {
            return this.route.replace(oldValue = "{$HOUSEHOLD_ID}", newValue = householdId)
                .replace(oldValue = "{$USER_ID}", userId)
        }
    }

    data object RegularTransaction : Screen(route = "regular_transaction_screen/{$USER_ID}") {
        fun passUserId(userId: String): String {
            return this.route.replace(oldValue = "{$USER_ID}", userId)
        }
    }

    data object HouseholdAddTransaction :
        Screen(route = "household_add_transaction_screen/{$HOUSEHOLD_ID}") {
        fun passHouseholdId(householdId: String): String {
            return this.route.replace(oldValue = "{$HOUSEHOLD_ID}", householdId)
        }
    }

    data object HouseholdSettings :
        Screen(route = "household_settings_screen/{$HOUSEHOLD_ID}/{$USER_ID}") {
        fun setArguments(householdId: String, userId: String): String {
            return this.route.replace(oldValue = "{$HOUSEHOLD_ID}", householdId)
                .replace(oldValue = "{$USER_ID}", userId)
        }

    }

    data object SavingGoalOverview : Screen(route = "saving_goal_overview_screen/{$USER_ID}") {
        fun setArguments(userId: String): String {
            return this.route.replace(oldValue = "{$USER_ID}", userId)
        }
    }

    data object SavingGoalAdd : Screen(route = "saving_goal_add_screen/{$USER_ID}") {
        fun setArguments(userId: String): String {
            return this.route.replace(oldValue = "{$USER_ID}", userId)
        }
    }
}