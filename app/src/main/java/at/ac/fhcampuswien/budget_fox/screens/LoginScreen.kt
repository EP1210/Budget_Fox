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
import at.ac.fhcampuswien.budget_fox.widgets.SimpleButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTitle
import at.ac.fhcampuswien.budget_fox.widgets.emailField
import at.ac.fhcampuswien.budget_fox.widgets.passwordField
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

@Composable
fun LoginForm() {
    var user: FirebaseUser? = null
    user = Firebase.auth.currentUser
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 70.dp)
    ) {
        SimpleTitle(title = "Login")
        val email = emailField()
        val password = passwordField()

        SimpleButton(name = "Login") {
            userLogin(email = email, password = password)
        }
        SimpleButton(name = "Create account") {
            // todo: navigate to registration screen
        }
        SimpleButton(name = "Logout") {
            Log.d(TAG, "The user $user with uid ${user?.uid} logs out.")
            user = null
            Firebase.auth.signOut()
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
                val user: FirebaseUser? = Firebase.auth.currentUser
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