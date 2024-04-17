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
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.navigation.Screen
import at.ac.fhcampuswien.budget_fox.widgets.SimpleButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTextButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTitle
import at.ac.fhcampuswien.budget_fox.widgets.emailField
import at.ac.fhcampuswien.budget_fox.widgets.passwordField
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun LoginScreen(navController: NavController) {
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
            userLogin(email = email, password = password, navController = navController)
        }
        SimpleTextButton(name = "Create account") {
            navController.navigate(route = Screen.Registration.route)
        }
    }
}

fun userLogin(
    email: String,
    password: String,
    navController: NavController
) {
    Firebase.auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                navController.navigate(route = Screen.UserProfile.route) {
                    popUpTo(id = 0)
                }
            } else {
                Log.d(TAG, "An error occurred while trying to log in.")
                // todo: display error message in login screen
            }
        }
}