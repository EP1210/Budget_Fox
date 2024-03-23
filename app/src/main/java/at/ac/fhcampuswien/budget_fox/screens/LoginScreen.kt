package at.ac.fhcampuswien.budget_fox.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.ac.fhcampuswien.budget_fox.widgets.ScreenTitle

@Composable
fun LoginScreen() {
    val loginFieldLabels = listOf("E-Mail", "Password")
    val buttonNames = listOf("Login", "Create account")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        ScreenTitle(title = "Login")

        loginFieldLabels.forEach { label ->
            var userInput by remember {
                mutableStateOf(value = "")
            }

            OutlinedTextField(
                value = userInput,
                onValueChange = {
                    userInput = it
                },
                label = {
                    Text(
                        text = label
                    )
                }
            )
        }

        buttonNames.forEach { name ->
            FilledTonalButton(
                onClick = {
                    when (name) {
                        buttonNames[0] -> userLogin()
                        // todo: navigate to registration screen
                    }
                },
                modifier = Modifier
                    .padding(top = 10.dp)
            ) {
                Text(
                    text = name
                )
            }
        }
    }
}

fun userLogin() {
    // Log.d(TAG, "Function was successfully called.")
}