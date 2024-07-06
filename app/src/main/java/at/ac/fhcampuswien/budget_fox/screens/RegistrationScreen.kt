package at.ac.fhcampuswien.budget_fox.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.navigation.Screen
import at.ac.fhcampuswien.budget_fox.view_models.RegistrationViewModel
import at.ac.fhcampuswien.budget_fox.view_models.ViewModelFactory
import at.ac.fhcampuswien.budget_fox.widgets.DateField
import at.ac.fhcampuswien.budget_fox.widgets.EmailField
import at.ac.fhcampuswien.budget_fox.widgets.PasswordField
import at.ac.fhcampuswien.budget_fox.widgets.SimpleButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleField
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTextLink
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTitle

@Composable
fun RegistrationScreen(
    navigationController: NavController
) {
    val factory = ViewModelFactory()
    val viewModel: RegistrationViewModel = viewModel(factory = factory)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        SimpleTitle(title = "User registration")

        EmailField { mail ->
            viewModel.setEmail(email = mail)
        }
        PasswordField { pwd ->
            viewModel.setPassword(password = pwd)
        }
        DateField(description = "Date of birth") { date ->
            viewModel.setDateOfBirth(dateOfBirth = date)
        }
        SimpleField(title = "First name") { name ->
            viewModel.setFirstName(firstName = name)
        }
        SimpleField(title = "Last name") { name ->
            viewModel.setLastName(lastName = name)
        }

        SimpleButton(name = "Register") {
            viewModel.registerUser { uid ->
                navigationController.navigate(Screen.UserProfile.passUserId(userId = uid)) {
                    popUpTo(id = 0)
                }
            }
        }

        Text(
            text = viewModel.errorMessage.value,
            color = Color.Red
        )

        SimpleTextLink(name = "To login") {
            navigationController.navigate(route = Screen.Login.route)
        }
    }
}