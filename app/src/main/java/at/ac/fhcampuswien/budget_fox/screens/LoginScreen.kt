package at.ac.fhcampuswien.budget_fox.screens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.ac.fhcampuswien.budget_fox.widgets.ScreenTitle
import at.ac.fhcampuswien.budget_fox.widgets.emailField
import at.ac.fhcampuswien.budget_fox.widgets.passwordField
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

private var user = Firebase.auth.currentUser

@Composable
fun LoginForm() {
    val buttonNames = listOf("Login", "Create account", "Logout")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 70.dp)
    ) {
        ScreenTitle(title = "Login")
        val email = emailField()
        val password = passwordField()

        buttonNames.forEach { name ->
            FilledTonalButton(
                onClick = {
                    when (name) {
                        buttonNames[0] -> userLogin(email = email, password = password)
                        // todo: navigate to registration screen
                        buttonNames[2] -> {
                            Log.d(TAG, "The user $user with uid ${user?.uid} logs out.")
                            user = null
                            Log.d(TAG, "The user $user is logged out.")
                        }
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

fun userLogin(
    email: String,
    password: String
) {
    Firebase.auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                user = Firebase.auth.currentUser
                Log.d(
                    TAG,
                    "Successfully logged in with email $email, password $password and uid ${user?.uid}."
                )
                // todo: navigate to profile screen of user
            } else {
                Log.d(TAG, "An error occurred while trying to log in.")
                // todo: display error message in login screen
            }
        }
}