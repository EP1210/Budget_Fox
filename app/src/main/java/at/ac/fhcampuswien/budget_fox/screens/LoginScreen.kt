package at.ac.fhcampuswien.budget_fox.screens

import android.content.ContentValues.TAG
import android.util.Log
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
    var email by remember {
        mutableStateOf(value = "")
    }
    var password by remember {
        mutableStateOf(value = "")
    }
    val buttonNames = listOf("Login", "Create account")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        ScreenTitle(title = "Login")

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text(
                    text = "E-Mail"
                )
            }
        )
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text(
                    text = "Password"
                )
            }
        )

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
    Log.d(TAG, "Function was successfully called.")
}