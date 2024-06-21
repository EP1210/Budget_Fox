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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.navigation.Screen
import at.ac.fhcampuswien.budget_fox.view_models.UserViewModel
import at.ac.fhcampuswien.budget_fox.widgets.SimpleBottomNavigationBar
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTopAppBar
import at.ac.fhcampuswien.budget_fox.widgets.TransactionListItem

@Composable
fun TransactionListScreen(
    navigationController: NavController,
    route: String,
    viewModel: UserViewModel
) {
    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "Your transactions")
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
        ) {
            viewModel.user?.let { it1 ->
                items(it1.getTransactions()) { transaction ->
                    TransactionListItem(
                        transaction = transaction,
                        navigationController = navigationController,
                        numbersVisible = viewModel.numbersVisible
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
        ) {
            FloatingActionButton(
                onClick = {
                    navigationController.navigate(route = Screen.Transaction.route)
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
                    contentDescription = "Add transaction"
                )
            }
        }
    }
}