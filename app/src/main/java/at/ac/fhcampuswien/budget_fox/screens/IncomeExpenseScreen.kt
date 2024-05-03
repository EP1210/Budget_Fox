package at.ac.fhcampuswien.budget_fox.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.data.UserRepository
import at.ac.fhcampuswien.budget_fox.models.Income
import at.ac.fhcampuswien.budget_fox.widgets.SimpleBottomNavigationBar
import at.ac.fhcampuswien.budget_fox.widgets.SimpleButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleField
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTitle
import at.ac.fhcampuswien.budget_fox.widgets.SimpleNumberField
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import java.time.Period

@Composable
fun IncomeExpenseScreen(
    navigationController: NavController,
    route: String
) {
    val userRepository = UserRepository()
    val firebaseUser = Firebase.auth.currentUser
    var incomeDescription by remember {
        mutableStateOf(value = "")
    }
    var monthlyInterval by remember {
        mutableStateOf(value = "")
    }
    var incomeAmount by remember {
        mutableStateOf(value = "")
    }

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
                incomeAmount = amount
            }
            SimpleField(
                title = "Description"
            ) { description ->
                incomeDescription = description
            }
            SimpleField(
                title = "Monthly interval (optional)"
            ) { interval ->
                monthlyInterval = interval
            }

            SimpleButton(
                name = "Add income",
                modifier = Modifier
                    .padding(bottom = 30.dp)
            ) {
                if (incomeAmount.isNotBlank() && incomeDescription.isNotBlank() && firebaseUser != null) {
                    userRepository.insertIncome(
                        income = Income(
                            amount = incomeAmount.toDouble(),
                            description = incomeDescription,
                            period = when {
                                monthlyInterval.isNotBlank() -> Period.ofMonths(monthlyInterval.toInt())
                                else -> null
                            }
                        ),
                        uid = firebaseUser.uid
                    )
                }
            }

            // TODO: make it possible to add an expense
            /*
            SimpleField(
                title = "Date"
            )
            SimpleNumberField(
                title = "Amount"
            )
            SimpleField(
                title = "Description"
            )
            SimpleButton(
                name = "Add expense"
            ) {

            }
             */
        }
    }
}