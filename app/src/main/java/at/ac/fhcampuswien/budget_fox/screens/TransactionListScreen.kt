package at.ac.fhcampuswien.budget_fox.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.navigation.Screen
import at.ac.fhcampuswien.budget_fox.view_models.TransactionListViewModel
import at.ac.fhcampuswien.budget_fox.view_models.ViewModelFactory
import at.ac.fhcampuswien.budget_fox.widgets.SimpleBottomNavigationBar
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTopAppBar
import at.ac.fhcampuswien.budget_fox.widgets.TransactionListItem

@Composable
fun TransactionListScreen(
    navigationController: NavController,
    route: String,
    userId: String?
) {
    val factory = ViewModelFactory()
    val viewModel: TransactionListViewModel = viewModel(factory = factory)

    if (userId == null || userId == "") {
        Text("User not found")
        return
    }

    LaunchedEffect(Unit) {
        viewModel.loadTransactions(userId = userId)
    }

    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "Your transactions")
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
        ) {
            items(viewModel.transactions) { transaction ->
                TransactionListItem(
                    transaction = transaction,
                    numbersVisible = viewModel.numbersVisible,
                    onDelete = { transactionToDelete ->
                        viewModel.deleteTransaction(userId = userId, transaction = transactionToDelete)
                    },
                    onItemClick = { transactionId ->
                        navigationController.navigate(
                            route = Screen.Category.setArguments(userId = userId, transactionId = transactionId)
                        )
                    }
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
        ) {
            FloatingActionButton(
                onClick = {
                    navigationController.navigate(route = Screen.TransactionCreate.passUserId(userId = userId))
                },
                shape = CircleShape,
                modifier = Modifier
                    .padding(20.dp)
                    .align(alignment = Alignment.BottomEnd)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add transaction"
                )
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
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "Numbers Visible"
                )
            }
            FloatingActionButton(
                onClick = {
                    navigationController.navigate(route = Screen.RegularTransaction.passUserId(userId = userId))
                },
                shape = CircleShape,
                modifier = Modifier
                    .padding(20.dp)
                    .align(alignment = Alignment.BottomCenter)
            ) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = "Add regular transaction"
                )
            }
        }
    }
}