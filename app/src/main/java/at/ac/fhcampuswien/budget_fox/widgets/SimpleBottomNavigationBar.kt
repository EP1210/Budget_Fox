package at.ac.fhcampuswien.budget_fox.widgets

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.navigation.getBottomNavigationItems

@Composable
fun SimpleBottomNavigationBar(
    navigationController: NavController,
    currentRoute: String
) {
    NavigationBar {
        getBottomNavigationItems().forEach { navigationItem ->
            NavigationBarItem(
                selected = navigationItem.route == currentRoute,
                onClick = {
                    navigationController.navigate(route = navigationItem.route) {
                        popUpTo(id = 0)
                    }
                },
                icon = {
                    Icon(
                        imageVector = when (navigationItem.route) {
                            currentRoute -> navigationItem.selected
                            else -> navigationItem.unselected
                        },
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