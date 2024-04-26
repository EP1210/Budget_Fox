package at.ac.fhcampuswien.budget_fox.screens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.models.Income
import at.ac.fhcampuswien.budget_fox.models.User
import at.ac.fhcampuswien.budget_fox.navigation.Screen
import at.ac.fhcampuswien.budget_fox.view_models.UserViewModel
import at.ac.fhcampuswien.budget_fox.widgets.SimpleButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTextLink
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTitle
import at.ac.fhcampuswien.budget_fox.widgets.dateField
import at.ac.fhcampuswien.budget_fox.widgets.emailField
import at.ac.fhcampuswien.budget_fox.widgets.passwordField
import at.ac.fhcampuswien.budget_fox.widgets.simpleField
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import java.time.LocalDateTime
import java.time.Period

@Composable
fun RegistrationScreen(
    navigationController: NavController,
    viewModel: UserViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp)
            .imePadding()
    ) {
        SimpleTitle(title = "User registration")

        val email = emailField()
        val password = passwordField()

        val birthDate = dateField()

        val firstName = simpleField(title = "First name")
        val lastName = simpleField(title = "Last name")

        SimpleButton(name = "Register") {
            if (email.isNotBlank() && password.isNotBlank())
                registerUser(user = User(firstName, lastName, birthDate, LocalDateTime.now()), email, password, navigationController, viewModel = viewModel)
            else
                Log.d("Register", "Fill out email / password") // TODO: Alert or something
        }

        SimpleTextLink(name = "To login") {
            navigationController.navigate(route = Screen.Login.route)
        }
    }
}

fun registerUser(user: User, email: String, password: String, navigationController: NavController, viewModel: UserViewModel) {
    val auth = Firebase.auth

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "Registration OK $email, $password")
                val firebaseUser = auth.currentUser

                viewModel.setUserState(firstLogin = true)
                if (firebaseUser != null) {
                    createUserEntryInDatabase(user = user, firebaseUser = firebaseUser)
                    navigationController.navigate(route = Screen.UserProfile.route) {
                        popUpTo(id = 0)
                    }
                }
            } else {
                Log.e(TAG, "Registration failed $email, $password")
            }
        }
}

fun createUserEntryInDatabase(user: User, firebaseUser: FirebaseUser) {
    val database = Firebase.firestore
    user.addIncome(Income(amount = 10.4F, description = "FHCW", period = Period.ZERO)) // todo

    database.collection("users").document(firebaseUser.uid).set(user.userToDatabase(firebaseUser.uid))
}
