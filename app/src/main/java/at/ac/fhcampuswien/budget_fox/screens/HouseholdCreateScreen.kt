package at.ac.fhcampuswien.budget_fox.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.view_models.HouseholdCreateViewModel
import at.ac.fhcampuswien.budget_fox.widgets.SimpleButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleEventIcon
import at.ac.fhcampuswien.budget_fox.widgets.SimpleField
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTopAppBar

@Composable
fun HouseholdCreateScreen(
    navigationController: NavController,
    viewModel: HouseholdCreateViewModel
) {
    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = "Create a Household"
            ) {
                SimpleEventIcon(
                    icon = Icons.AutoMirrored.Filled.ArrowBack
                ) {
                    navigationController.popBackStack()
                }
            }
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
        ) {
            SimpleField(
                title = "Household name"
            ) { name ->
                viewModel.setHouseholdName(householdName = name)
            }
            SimpleButton(name = "Create") {
                if (viewModel.householdName.isNotBlank()) {
                    viewModel.insertHousehold()
                    viewModel.setHouseholdName(householdName = "")
                }
            }
        }
    }
}