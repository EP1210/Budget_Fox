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
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun LoginForm() {
    var email by remember {
        mutableStateOf(value = "")
    }
    var password by remember {
        mutableStateOf(value = "")
    }
    val fields = listOf("E-Mail", "Password")
    val buttonNames = listOf("Login", "Create account")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        ScreenTitle(title = "Login")

        fields.forEach { field ->
            OutlinedTextField(
                value = when (field) {
                    fields[0] -> email
                    else -> password
                },
                onValueChange = { userInput ->
                    when (field) {
                        fields[0] -> email = userInput
                        else -> password = userInput
                    }
                },
                label = {
                    Text(
                        text = when (field) {
                            fields[0] -> fields[0]
                            else -> fields[1]
                        }
                    )
                }
            )
        }

        buttonNames.forEach { name ->
            FilledTonalButton(
                onClick = {
                    when (name) {
                        buttonNames[0] -> userLogin(email = email, password = password)
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

fun userLogin(email: String, password: String) {
    val authentication = Firebase.auth

    authentication.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Log.d(TAG, "Successfully logged in with email $email and password $password :)")
                // todo: navigate to profile screen of user
            } else {
                // Log.d(TAG, "An error occurred while trying to log in :(")
                // todo: display error message in login screen
            }
        }
}