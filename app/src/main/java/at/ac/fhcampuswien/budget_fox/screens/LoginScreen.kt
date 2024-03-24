package at.ac.fhcampuswien.budget_fox.screens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.ac.fhcampuswien.budget_fox.widgets.CreateButton
import at.ac.fhcampuswien.budget_fox.widgets.CreateTitle
import at.ac.fhcampuswien.budget_fox.widgets.emailField
import at.ac.fhcampuswien.budget_fox.widgets.passwordField
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

private var user: FirebaseUser? = null

@Composable
fun LoginForm() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 70.dp)
    ) {
        CreateTitle(title = "Login")
        val email = emailField()
        val password = passwordField()

        CreateButton(name = "Login") {
            userLogin(email = email, password = password)
        }
        CreateButton(name = "Create account") {
            // todo: navigate to registration screen
        }
        CreateButton(name = "Logout") {
            Log.d(TAG, "The user $user with uid ${user?.uid} logs out.")
            user = null
            Log.d(TAG, "The user $user is logged out.")
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