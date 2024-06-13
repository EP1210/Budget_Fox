package at.ac.fhcampuswien.budget_fox.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.navigation.Screen
import at.ac.fhcampuswien.budget_fox.widgets.SimpleBottomNavigationBar
import at.ac.fhcampuswien.budget_fox.widgets.SimpleButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTopAppBar

@Composable
fun HouseholdWelcomeScreen(
    navigationController: NavController,
    route: String
) {
    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "Household")
        },
        bottomBar = {
            SimpleBottomNavigationBar(
                navigationController = navigationController,
                currentRoute = route
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(paddingValues = it)
                .fillMaxSize()
        ) {
            SimpleButton(
                name = "Create Household"
            ) {
                navigationController.navigate(route = Screen.HouseholdCreate.route)
            }
            SimpleButton(
                name = "Join Household"
            ) {
                navigationController.navigate(route = Screen.HouseholdJoin.route)
            }
        }
    }
}