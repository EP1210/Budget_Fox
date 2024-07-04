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
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.models.Transaction
import at.ac.fhcampuswien.budget_fox.navigation.Screen
import at.ac.fhcampuswien.budget_fox.view_models.HouseholdViewModel
import at.ac.fhcampuswien.budget_fox.view_models.UserViewModel
import at.ac.fhcampuswien.budget_fox.widgets.SimpleBottomNavigationBar
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTopAppBar
import at.ac.fhcampuswien.budget_fox.widgets.TransactionListItem

@Composable
fun HouseholdTransactionScreen(
    navigationController: NavController,
    route: String,
    userViewModel: UserViewModel,
    householdViewModel: HouseholdViewModel
) {
    val householdId = userViewModel.getHousehold()
    if(householdId == "") {
        Text("Household not found!")
        return
    }
    householdViewModel.getHousehold(householdId)

    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "Household transactions")
        },
        bottomBar = {
            SimpleBottomNavigationBar(
                navigationController = navigationController,
                currentRoute = route
            )
        }
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues = it)
                .fillMaxSize()
        ) {
            items(items = householdViewModel.getTransactions()) { item: Transaction ->
                TransactionListItem(transaction = item, numbersVisible = userViewModel.numbersVisible, onDelete = {}, onItemClick = {})
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
                    val route = Screen.HouseholdAddTransaction.setHouseholdId(householdId = userViewModel.getHousehold())
                    navigationController.navigate(route)
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
                    userViewModel.numbersVisible.value = !userViewModel.numbersVisible.value
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
                    val route = Screen.HouseholdSettings.setArguments(householdId = userViewModel.getHousehold(), userId = userViewModel.getUserId())
                    navigationController.navigate(route)
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