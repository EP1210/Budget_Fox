package at.ac.fhcampuswien.budget_fox.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.navigation.Screen
import at.ac.fhcampuswien.budget_fox.navigation.SimpleBottomNavigationBar
import at.ac.fhcampuswien.budget_fox.view_models.UserProfileViewModel
import at.ac.fhcampuswien.budget_fox.view_models.ViewModelFactory
import at.ac.fhcampuswien.budget_fox.widgets.SimpleButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTitle
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun UserProfileScreen(
    navigationController: NavController,
    route: String,
    userId: String?
) {
    val factory = ViewModelFactory()
    val viewModel: UserProfileViewModel = viewModel(factory = factory)

    if (userId == null || userId == "") {
        Text("User not found")
        return
    }

    Scaffold(
        bottomBar = {
            SimpleBottomNavigationBar(
                navigationController = navigationController,
                currentRoute = route,
                userId = userId
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
        ) {
            val auth = Firebase.auth
            val userMail = auth.currentUser?.email

            SimpleTitle(title = "Personal information")

            viewModel.getUserData(userId = userId)

            Text(
                text = """E-Mail: $userMail
                |Name: ${viewModel.userName.value}
                |Birth date: ${viewModel.userBirthDate.value.dayOfMonth}.${viewModel.userBirthDate.value.monthValue}.${viewModel.userBirthDate.value.year}
                |Registration date: ${viewModel.userRegistrationDate.value.dayOfMonth}.${viewModel.userRegistrationDate.value.monthValue}.${viewModel.userRegistrationDate.value.year}
                |Registration time: ${viewModel.userRegistrationDate.value.hour}:${viewModel.userRegistrationDate.value.minute}
            """.trimMargin()
            )

            SimpleButton(name = "Saving goals") {
                val savingGoalsRoute = Screen.SavingGoalOverview.setArguments(userId = userId)
                navigationController.navigate(savingGoalsRoute)
            }
            SimpleButton(name = "Budgets") {
                val savingGoalsRoute = Screen.Budget.setArguments(userId = userId)
                navigationController.navigate(savingGoalsRoute)
            }

            SimpleButton(name = "Logout") {
                auth.signOut()
                Log.d("TAG", "Signed out!")
                navigationController.navigate(Screen.Login.route) {
                    popUpTo(id = 0)
                }
            }
        }
    }
}

