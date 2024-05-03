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
import at.ac.fhcampuswien.budget_fox.models.Income
import at.ac.fhcampuswien.budget_fox.widgets.SimpleBottomNavigationBar
import at.ac.fhcampuswien.budget_fox.widgets.SimpleButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTitle
import at.ac.fhcampuswien.budget_fox.widgets.simpleField
import at.ac.fhcampuswien.budget_fox.widgets.simpleNumberField
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

            val amount = simpleNumberField(
                title = "Amount"
            )
            val description = simpleField(
                title = "Description"
            )
            val period = simpleField(
                title = "Monthly interval (optional)"
            )
            SimpleButton(
                name = "Add income",
                modifier = Modifier
                    .padding(bottom = 30.dp)
            ) {
                if (amount.isNotBlank() && description.isNotBlank() && firebaseUser != null) {
                    userRepository.insertIncome(
                        income = Income(
                            amount = amount.toDouble(),
                            description = description,
                            period = when {
                                period.isNotBlank() -> Period.ofMonths(period.toInt())
                                else -> null
                            }
                        ),
                        uid = firebaseUser.uid
                    )
                }
            }

            // TODO: make it possible to add an expense
            simpleField(
                title = "Date"
            )
            simpleNumberField(
                title = "Amount"
            )
            simpleField(
                title = "Description"
            )
            SimpleButton(
                name = "Add expense"
            ) {

            }
        }
    }
}