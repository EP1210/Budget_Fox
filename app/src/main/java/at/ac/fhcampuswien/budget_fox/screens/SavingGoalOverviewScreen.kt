package at.ac.fhcampuswien.budget_fox.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.navigation.Screen
import at.ac.fhcampuswien.budget_fox.view_models.SavingGoalOverviewViewModel
import at.ac.fhcampuswien.budget_fox.view_models.ViewModelFactory
import at.ac.fhcampuswien.budget_fox.widgets.SavingGoalListItem
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavingGoalOverviewScreen(
    navController: NavController,
    userId: String?
) {
    if (userId == null) {
        Text(text = "User not found")
        return
    }

    val factory = ViewModelFactory()
    val viewModel: SavingGoalOverviewViewModel = viewModel(factory = factory)

    val savingGoalItems = viewModel.loadSavingGoals(userId = userId).collectAsState()

    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "Saving goals") {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Go back"
                    )
                }
            }
        },
    ) { paddingVal ->
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues = paddingVal)
                .fillMaxSize()
        ) {
            items(items = savingGoalItems.value) { item ->
                SavingGoalListItem(savingGoal = item) {
                    navController.navigate(Screen.SavingGoalTransactionList.setArguments(userId = userId, savingGoalId = item.uuid))
                }
            }
        }
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingVal)
                .fillMaxSize()
        ) {
            FloatingActionButton(
                onClick = {
                    val route = Screen.SavingGoalAdd.setArguments(userId = userId)
                    navController.navigate(route)
                },
                shape = CircleShape,
                modifier = Modifier
                    .padding(20.dp)
                    .align(alignment = Alignment.BottomEnd)
            ) {
                Icon(Icons.Filled.Add, "Add goal")
            }
        }
    }
}