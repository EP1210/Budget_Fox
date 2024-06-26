package at.ac.fhcampuswien.budget_fox.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.view_models.SavingGoalAddViewModel
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTopAppBar

@Composable
fun SavingGoalAddScreen(
    navController: NavController,
    viewModel: SavingGoalAddViewModel
) {
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
        }
    ) {paddingValues ->
        Text(text = "Add", modifier = Modifier.padding(paddingValues))
    }
}