package at.ac.fhcampuswien.budget_fox.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.models.Transaction
import at.ac.fhcampuswien.budget_fox.navigation.Screen
import at.ac.fhcampuswien.budget_fox.navigation.SimpleBottomNavigationBar
import at.ac.fhcampuswien.budget_fox.view_models.HouseholdTransactionViewModel
import at.ac.fhcampuswien.budget_fox.view_models.ViewModelFactory
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTopAppBar
import at.ac.fhcampuswien.budget_fox.widgets.TransactionListItem

@Composable
fun HouseholdTransactionScreen(
    navigationController: NavController,
    route: String,
    userId: String?,
    householdId: String?
) {

    val factory = ViewModelFactory()
    val viewModel: HouseholdTransactionViewModel = viewModel(factory = factory)

    if (userId == null || userId == "" || householdId == null || householdId == "") {
        Text("User or household not found")
        return
    }

    viewModel.getHousehold(householdId)

    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "Household transactions")
        },
        bottomBar = {
            SimpleBottomNavigationBar(
                navigationController = navigationController,
                currentRoute = route,
                userId = userId
            )
        }
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues = it)
                .fillMaxSize()
        ) {
            items(items = viewModel.getTransactions()) { item: Transaction ->
                TransactionListItem(
                    transaction = item,
                    numbersVisible = viewModel.numbersVisible,
                    onDelete = {},
                    onItemClick = {})
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
        )
        {
            FloatingActionButton(
                onClick = {
                    val addTransactionRoute =
                        Screen.HouseholdAddTransaction.passHouseholdId(
                            householdId = householdId
                        )
                    navigationController.navigate(addTransactionRoute)
                },
                shape = CircleShape,
                modifier = Modifier
                    .padding(20.dp)
                    .align(alignment = Alignment.BottomEnd)
            ) {
                Icon(Icons.Filled.Add, "Add transaction")
            }
            FloatingActionButton(
                onClick = {
                    viewModel.numbersVisible.value = !viewModel.numbersVisible.value
                },
                shape = CircleShape,
                modifier = Modifier
                    .padding(20.dp)
                    .align(alignment = Alignment.BottomStart)
            ) {
                Icon(Icons.Filled.Lock, "Hide numbers")
            }
            FloatingActionButton(
                onClick = {
                    val householdSettingsRoute = Screen.HouseholdSettings.setArguments(
                        householdId = householdId,
                        userId = userId
                    )
                    navigationController.navigate(householdSettingsRoute)
                },
                shape = CircleShape,
                modifier = Modifier
                    .padding(20.dp)
                    .align(alignment = Alignment.BottomCenter)
            ) {
                Icon(Icons.Filled.Settings, "Household settings")
            }
        }
    }
}