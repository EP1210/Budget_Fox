package at.ac.fhcampuswien.budget_fox.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.view_models.SavingGoalAddViewModel
import at.ac.fhcampuswien.budget_fox.view_models.UserProfileViewModel
import at.ac.fhcampuswien.budget_fox.view_models.ViewModelFactory
import at.ac.fhcampuswien.budget_fox.widgets.SimpleButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleField
import at.ac.fhcampuswien.budget_fox.widgets.SimpleNumberField
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTopAppBar

@Composable
fun SavingGoalAddScreen(
    navController: NavController,
    userId: String?
) {
    val factory = ViewModelFactory()
    val viewModel: SavingGoalAddViewModel = viewModel(factory = factory)

    if (userId == null || userId == "") {
        Text("User not found")
        return
    }

    Scaffold(topBar = {
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
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SimpleField(title = "Saving goal title") { title ->
                viewModel.setGoalTitle(title)
            }
            SimpleNumberField(title = "Saving goal amount") { amount ->
                viewModel.setGoalAmount(amount.toDouble())
            }
            SimpleButton(name = "Add saving goal") {
                viewModel.addSavingGoal(userId = userId) {
                    navController.popBackStack()
                }
            }
        }
    }
}