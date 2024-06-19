package at.ac.fhcampuswien.budget_fox.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.view_models.UserViewModel
import at.ac.fhcampuswien.budget_fox.widgets.SimpleButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleDropdownField
import at.ac.fhcampuswien.budget_fox.widgets.SimpleEventIcon
import at.ac.fhcampuswien.budget_fox.widgets.SimpleField
import at.ac.fhcampuswien.budget_fox.widgets.SimpleNumberField
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTopAppBar

@Composable
fun RegularTransactionScreen(
    navigationController: NavController,
    viewModel: UserViewModel
) {
    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "Add Regular Transaction") {
                SimpleEventIcon(
                    icon = Icons.AutoMirrored.Filled.ArrowBack
                ) {
                    navigationController.popBackStack()
                }
            }
        }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
                .padding(horizontal = 70.dp)
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
                title = "Date (yyyy-MM-dd)"
            ) { date ->
                viewModel.setTransactionDate(date)
            }
            SimpleDropdownField(
                title = "",
                items = listOf("Daily", "Weekly", "Monthly", "Yearly")
            ) { frequency ->
                viewModel.setTransactionFrequency(frequency)
            }

            SimpleButton(
                name = "Add Regular Expense"
            ) {
                viewModel.setTransactionAmount(viewModel.transactionAmount * -1)
                viewModel.insertRegularTransaction()
            }
        }
    }
}
