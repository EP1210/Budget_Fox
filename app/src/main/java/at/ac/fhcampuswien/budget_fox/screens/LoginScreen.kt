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
    val authentication = Firebase.auth

    authentication.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "Successfully logged in with email $email and password $password :)")
            } else {
                Log.d(TAG, "An error occurred while trying to log in :(")
            }
        }
}