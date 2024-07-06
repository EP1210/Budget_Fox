package at.ac.fhcampuswien.budget_fox.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.navigation.Screen
import at.ac.fhcampuswien.budget_fox.view_models.HouseholdWelcomeViewModel
import at.ac.fhcampuswien.budget_fox.view_models.ViewModelFactory
import at.ac.fhcampuswien.budget_fox.widgets.SimpleBottomNavigationBar
import at.ac.fhcampuswien.budget_fox.widgets.SimpleButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTopAppBar

@Composable
fun HouseholdWelcomeScreen(
    navigationController: NavController,
    route: String,
    userId: String?
) {
    val factory = ViewModelFactory()
    val viewModel: HouseholdWelcomeViewModel = viewModel(factory = factory)

    if (userId == null || userId == "") {
        Text("User not found")
        return
    }

    viewModel.getHousehold(userId = userId)

     if(viewModel.householdId.value != "") {
        navigationController.navigate(route = Screen.HouseholdTransaction.passHouseholdId(householdId = viewModel.householdId.value, userId = userId)) {
            popUpTo(id = 0)
        }
    }

    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "Household")
        },
        bottomBar = {
            SimpleBottomNavigationBar(
                navigationController = navigationController,
                currentRoute = route,
                userId = userId
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
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