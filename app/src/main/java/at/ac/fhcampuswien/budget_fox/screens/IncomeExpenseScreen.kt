package at.ac.fhcampuswien.budget_fox.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.widgets.SimpleBottomNavigationBar

@Composable
fun IncomeExpenseScreen(
    navigationController: NavController,
    route: String
) {
    Scaffold(
        bottomBar = {
            SimpleBottomNavigationBar(
                navigationController = navigationController,
                currentRoute = route
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(paddingValues = it)
        ) {

        }
    }
}