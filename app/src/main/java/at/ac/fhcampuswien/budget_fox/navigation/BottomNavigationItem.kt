package at.ac.fhcampuswien.budget_fox.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val label: String,
    val selected: ImageVector,
    val unselected: ImageVector,
    val route: String
)

fun getBottomNavigationItems(): List<BottomNavigationItem> {
    return listOf(
        BottomNavigationItem(
            label = "Profile",
            selected = Icons.Filled.Face,
            unselected = Icons.Outlined.Face,
            route = Screen.UserProfile.route
        ),
        BottomNavigationItem(
            label = "Budget",
            selected = Icons.Filled.ShoppingCart,
            unselected = Icons.Outlined.ShoppingCart,
            route = Screen.IncomeExpense.route
        )
    )
}