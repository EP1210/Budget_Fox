package at.ac.fhcampuswien.budget_fox.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun SimpleBottomNavigationBar(
    navigationController: NavController,
    currentRoute: String,
    userId: String
) {
    NavigationBar {
        getBottomNavigationItems(userId = userId).forEach { navigationItem ->
            // First = Route without arguments
            val selected = navigationItem.route.split("/").first() == currentRoute.split("/").first() || navigationItem.alternativeRoute.split("/").first() == currentRoute.split("/").first()
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navigationController.navigate(route = navigationItem.route) {
                        popUpTo(id = 0)
                    }
                },
                icon = {
                    Icon(
                        imageVector =
                            if(selected)
                                navigationItem.selected
                            else
                                navigationItem.unselected,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = navigationItem.label
                    )
                }
            )
        }
    }
}