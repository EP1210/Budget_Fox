package at.ac.fhcampuswien.budget_fox.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.navigation.Screen
import at.ac.fhcampuswien.budget_fox.view_models.SavingGoalOverviewViewModel
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTopAppBar

@Composable
fun SavingGoalOverviewScreen(
    navController: NavController,
    viewModel: SavingGoalOverviewViewModel
) {
    if(viewModel.userId == null) {
        Text(text = "User not found")
        return
    }
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
        if(viewModel.savingGoals.size == 0) {
            Text(text = "No saving goals found", modifier = Modifier.padding(paddingVal))
        }
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingVal)
                .fillMaxSize()
        ) {
            FloatingActionButton(
                onClick = {
                    val route = Screen.SavingGoalAdd.setArguments(userId = viewModel.userId)
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