package at.ac.fhcampuswien.budget_fox.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.view_models.BudgetViewModel
import at.ac.fhcampuswien.budget_fox.view_models.ViewModelFactory
import at.ac.fhcampuswien.budget_fox.widgets.ProgressListItem
import at.ac.fhcampuswien.budget_fox.widgets.SimpleEventIcon
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTopAppBar

@Composable
fun BudgetScreen(
    navController: NavController,
    userId: String?
) {
    val factory = ViewModelFactory()
    val viewModel: BudgetViewModel = viewModel(factory = factory)

    if (userId == null || userId == "") {
        Text("User not found")
        return
    }

    viewModel.loadCategories(userId)

    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "Budgets of current month") {
                SimpleEventIcon(
                    icon = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "An arrow icon to navigate back to the previous screen"
                ) {
                    navController.popBackStack()
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {
            items(items = viewModel.budgets.value) { item ->
                ProgressListItem(
                    name = item.description,
                    amount = item.budget,
                    progress = item.amount,
                    color = Color.Red
                )
            }
        }
    }
}