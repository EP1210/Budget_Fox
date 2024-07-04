package at.ac.fhcampuswien.budget_fox.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val label: String,
    val selected: ImageVector,
    val unselected: ImageVector,
    val route: String,
    val alternativeRoute: String = ""
)

fun getBottomNavigationItems(userId: String): List<BottomNavigationItem> {
    return listOf(
        BottomNavigationItem(
            label = "Profile",
            selected = Icons.Filled.Face,
            unselected = Icons.Outlined.Face,
            route = Screen.UserProfile.passUserId(userId = userId)
        ),
        BottomNavigationItem(
            label = "Budget",
            selected = Icons.Filled.ShoppingCart,
            unselected = Icons.Outlined.ShoppingCart,
            route = Screen.Transaction.passUserId(userId = userId)
        ),
        BottomNavigationItem(
            label = "Statistics",
            selected = Icons.Filled.Info,
            unselected = Icons.Outlined.Info,
            route = Screen.Statistics.passUserId(userId = userId)
        ),
        BottomNavigationItem(
            label = "Household",
            selected = Icons.Filled.Home,
            unselected = Icons.Outlined.Home,
            route = Screen.HouseholdWelcome.passUserId(userId = userId),
            alternativeRoute = Screen.HouseholdTransaction.route
        )
    )
}