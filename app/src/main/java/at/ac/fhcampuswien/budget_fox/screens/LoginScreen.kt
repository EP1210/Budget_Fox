package at.ac.fhcampuswien.budget_fox.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.navigation.Screen
import at.ac.fhcampuswien.budget_fox.view_models.LoginViewModel
import at.ac.fhcampuswien.budget_fox.view_models.ViewModelFactory
import at.ac.fhcampuswien.budget_fox.widgets.EmailField
import at.ac.fhcampuswien.budget_fox.widgets.PasswordField
import at.ac.fhcampuswien.budget_fox.widgets.SimpleButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTextLink
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTitle

@Composable
fun LoginScreen(
    navigationController: NavController
) {
    val factory = ViewModelFactory()
    val viewModel: LoginViewModel = viewModel(factory = factory)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        SimpleTitle(title = "Login")

        EmailField { mail ->
            viewModel.setEmail(mail)
        }
        PasswordField { pwd ->
            viewModel.setPassword(pwd)
        }

        SimpleButton(name = "Login") {
            if (viewModel.email.isNotBlank() && viewModel.password.isNotBlank()) {
                viewModel.userLogin { uid ->
                    navigationController.navigate(route = Screen.UserProfile.setArguments(userId = uid)) {
                        popUpTo(id = 0)
                    }
                }
            }
        }

        Text(
            text = viewModel.errorMessage.value,
            color = Color.Red
        )

        SimpleTextLink(name = "Create account") {
            navigationController.navigate(route = Screen.Registration.route)
        }
    }
}