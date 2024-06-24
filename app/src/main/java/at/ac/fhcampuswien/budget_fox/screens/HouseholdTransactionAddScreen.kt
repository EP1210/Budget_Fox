package at.ac.fhcampuswien.budget_fox.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.view_models.HouseholdTransactionAddViewModel
import at.ac.fhcampuswien.budget_fox.widgets.SimpleButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleField
import at.ac.fhcampuswien.budget_fox.widgets.SimpleNumberField
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTopAppBar

@Composable
fun HouseholdTransactionAddScreen(
    viewModel: HouseholdTransactionAddViewModel,
    navigationController: NavController,
    householdId: String?
) {
    if (householdId == null) {
        Text("Household not found")
        return
    }

    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "Add transaction") {
                IconButton(onClick = {
                    navigationController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }
            }
        }
    ) {
        viewModel.findHouseholdById(householdId)
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
        ) {
            SimpleNumberField(
                title = "Amount"
            ) { amount ->
                viewModel.setTransactionAmount(amount.toDouble())
            }
            SimpleField(
                title = "Description"
            ) { description ->
                viewModel.setTransactionDescription(description)
            }
            SimpleField(
                title = "Date"
            ) { interval ->
                viewModel.setTransactionDate(interval)
            }

            SimpleButton(
                name = "Add income",
                modifier = Modifier
                    .padding(bottom = 30.dp)
            ) {
                viewModel.addHouseholdTransaction()
            }

            SimpleButton(
                name = "Add expense"
            ) {
                viewModel.setTransactionAmount(viewModel.transactionAmount * -1)
                viewModel.addHouseholdTransaction()
            }
        }
    }
}