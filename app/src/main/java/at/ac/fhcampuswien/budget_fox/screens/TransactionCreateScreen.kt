package at.ac.fhcampuswien.budget_fox.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import at.ac.fhcampuswien.budget_fox.view_models.TransactionViewModel
import at.ac.fhcampuswien.budget_fox.view_models.ViewModelFactory
import at.ac.fhcampuswien.budget_fox.widgets.DateField
import at.ac.fhcampuswien.budget_fox.widgets.SimpleButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleEventIcon
import at.ac.fhcampuswien.budget_fox.widgets.SimpleField
import at.ac.fhcampuswien.budget_fox.widgets.SimpleNumberField
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTopAppBar
import java.time.format.DateTimeFormatter

@Composable
fun TransactionCreateScreen(
    navigationController: NavController,
    userId: String?
) {
    val factory = ViewModelFactory()
    val viewModel: TransactionViewModel = viewModel(factory = factory)

    if (userId == null || userId == "") {
        Text("User not found")
        return
    }

    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "Add transaction") {
                SimpleEventIcon(
                    icon = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "An arrow icon to navigate back to the previous screen"
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
        ) {
            SimpleNumberField(
                title = "Amount"
            ) { amount ->
                viewModel.setTransactionAmount(amount = amount.toDouble())
            }
            SimpleField(
                title = "Description"
            ) { description ->
                viewModel.setTransactionDescription(description = description)
            }
            DateField(
                description = "Date",
                onValueChanged = { date ->
                    viewModel.setTransactionDate(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                }
            )

            SimpleButton(
                name = "Create income"
            ) {
                viewModel.insertTransaction(userId = userId)
            }
            SimpleButton(
                name = "Create expense"
            ) {
                viewModel.setTransactionAmount(viewModel.transactionAmount * -1)
                viewModel.insertTransaction(userId = userId)
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