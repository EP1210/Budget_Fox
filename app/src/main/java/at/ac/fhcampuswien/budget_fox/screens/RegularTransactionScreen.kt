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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.view_models.RegularTransactionViewModel
import at.ac.fhcampuswien.budget_fox.view_models.ViewModelFactory
import at.ac.fhcampuswien.budget_fox.widgets.DateField
import at.ac.fhcampuswien.budget_fox.widgets.SimpleButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleDropdownField
import at.ac.fhcampuswien.budget_fox.widgets.SimpleField
import at.ac.fhcampuswien.budget_fox.widgets.SimpleNumberField
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTopAppBar

@Composable
fun RegularTransactionScreen(
    navigationController: NavController,
    userId: String?
) {
    val factory = ViewModelFactory()
    val viewModel: RegularTransactionViewModel = viewModel(factory = factory)

    if (userId == null || userId == "") {
        Text("User not found")
        return
    }

    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "Add Regular Transaction") {
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
            DateField(
                description = "Date",
                onValueChanged = { date ->
                    viewModel.transactionDate.value = date
                    viewModel.setTransactionDate(date)
                }
            )
            SimpleDropdownField(
                placeholder = "Select Frequency ▼",
                items = listOf("Daily", "Weekly", "Monthly", "Yearly")
            ) { frequency ->
                viewModel.setTransactionFrequency(frequency)
            }

            SimpleButton(
                name = "Add Regular Expense"
            ) {
                viewModel.setTransactionAmount(viewModel.transactionAmount * -1)
                viewModel.insertRegularTransaction(userId = userId)
            }

                Text(
                    text = viewModel.transactionMessage.value,
                    color = if (viewModel.transactionMessage.value.contains("successfully")) Color.Green else Color.Red,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 16.dp)
                )
        }
    }
}
