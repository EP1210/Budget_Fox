package at.ac.fhcampuswien.budget_fox.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.data.UserRepository
import at.ac.fhcampuswien.budget_fox.models.Transaction
import at.ac.fhcampuswien.budget_fox.view_models.UserViewModel
import at.ac.fhcampuswien.budget_fox.widgets.SimpleBottomNavigationBar
import at.ac.fhcampuswien.budget_fox.widgets.SimpleButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleField
import at.ac.fhcampuswien.budget_fox.widgets.SimpleNumberField
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTitle
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import java.time.Period

@Composable
fun IncomeExpenseScreen(
    navigationController: NavController,
    route: String,
    viewModel: UserViewModel
) {
    val userRepository = UserRepository()
    val firebaseUser = Firebase.auth.currentUser


    Scaffold(
        bottomBar = {
            SimpleBottomNavigationBar(
                navigationController = navigationController,
                currentRoute = route
            )
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
            SimpleTitle(
                title = "Add an income or expense"
            )

            SimpleNumberField(
                title = "Amount"
            ) { amount ->
                viewModel.setIncomeAmount(amount)
            }
            SimpleField(
                title = "Description"
            ) { description ->
                viewModel.setIncomeDescription(description)
            }
            SimpleField(
                title = "Monthly interval (optional)"
            ) { interval ->
               viewModel.setMonthlyInterval(interval)
            }

            SimpleButton(
                name = "Add income",
                modifier = Modifier
                    .padding(bottom = 30.dp)
            ) {
                if (viewModel.incomeAmount.isNotBlank() && viewModel.incomeDescription.isNotBlank() && firebaseUser != null) {
                    userRepository.insertTransaction(
                        transaction = Transaction(
                            amount = viewModel.incomeAmount.toDouble(),
                            description = viewModel.incomeDescription,
                            period = when {
                                viewModel.monthlyInterval.isNotBlank() -> Period.ofMonths(viewModel.monthlyInterval.toInt())
                                else -> null
                            }
                        ),
                        userId = firebaseUser.uid
                    )
                }
            }

            SimpleField(
                title = "Date"
            ) { date ->
                viewModel.setExpenseDate(date = date)
            }
            SimpleNumberField(
                title = "Amount"
            ) { amount ->
                viewModel.setExpenseAmount(amount = amount)
            }
            SimpleField(
                title = "Description"
            ) { description ->
                viewModel.setExpenseDescription(description = description)
            }

            SimpleButton(
                name = "Add expense"
            ) {
                if (viewModel.expenseAmount.isNotBlank() && viewModel.expenseDescription.isNotBlank() && viewModel.expenseDate.isNotBlank() && firebaseUser != null) {
                    userRepository.insertTransaction(
                        transaction = Transaction(
                            period = when {
                                viewModel.monthlyInterval.isNotBlank() -> Period.ofMonths(viewModel.expenseDate.toInt())
                                else -> null
                            },
                            amount = viewModel.expenseAmount.toDouble() * -1,
                            description = viewModel.expenseDescription
                        ),
                        userId = firebaseUser.uid
                    )
                }
            }
        }
    }
}