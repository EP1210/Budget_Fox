package at.ac.fhcampuswien.budget_fox.screens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.data.UserRepository
import at.ac.fhcampuswien.budget_fox.models.User
import at.ac.fhcampuswien.budget_fox.navigation.Screen
import at.ac.fhcampuswien.budget_fox.view_models.UserViewModel
import at.ac.fhcampuswien.budget_fox.widgets.DateField
import at.ac.fhcampuswien.budget_fox.widgets.EmailField
import at.ac.fhcampuswien.budget_fox.widgets.PasswordField
import at.ac.fhcampuswien.budget_fox.widgets.SimpleButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleField
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTextLink
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTitle
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import java.time.LocalDateTime

@Composable
fun RegistrationScreen(
    navigationController: NavController,
    viewModel: UserViewModel
) {
    var email by remember {
        mutableStateOf(value = "")
    }
    var dateOfBirth by remember {
        mutableStateOf(value = LocalDateTime.now())
    }
    var password by remember {
        mutableStateOf(value = "")
    }
    var firstName by remember {
        mutableStateOf(value = "")
    }
    var lastName by remember {
        mutableStateOf(value = "")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 70.dp)
            .imePadding()
    ) {
        SimpleTitle(title = "User registration")

        EmailField { mail ->
            email = mail
        }
        PasswordField { word ->
            password = word
        }
        DateField { date ->
            dateOfBirth = date
        }
        SimpleField(title = "First name") { name ->
            firstName = name
        }
        SimpleField(title = "Last name") { name ->
            lastName = name
        }

        SimpleButton(name = "Register") {
            if (email.isNotBlank() && password.isNotBlank())
                registerUser(
                    user = User(firstName, lastName, dateOfBirth, LocalDateTime.now()),
                    email = email,
                    password = password,
                    navigationController = navigationController,
                    viewModel = viewModel
                )
            else
                Log.d("Register", "Fill out email / password") // TODO: Alert or something
        }

        SimpleTextLink(name = "To login") {
            navigationController.navigate(route = Screen.Login.route)
        }
    }
}

fun registerUser(
    user: User,
    email: String,
    password: String,
    navigationController: NavController,
    viewModel: UserViewModel
) {
    val auth = Firebase.auth
    val database = Firebase.firestore

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "Registration OK $email, $password")
                val firebaseUser = auth.currentUser

                viewModel.setUserState(firstLogin = true)
                if (firebaseUser != null) {
                    val newDocRef = database.collection("users").document(firebaseUser.uid)
                    val batch = database.batch()

                    batch.set(newDocRef, user.userToDatabase())

                    //TODO: Refactor ugly code!
                    batch.commit()
                        .addOnSuccessListener {
                            val repository = UserRepository()

                            //TODO: Duplicate code!
                            repository.getAllDataFromUser(firebaseUser.uid,
                                onSuccess = { user ->
                                    if (user != null) {
                                        viewModel.setUser(user)
                                        navigationController.navigate(route = Screen.UserProfile.route) {
                                            popUpTo(id = 0)
                                        }
                                    } else {
                                        Log.d("FIREBASE", "USER IS NULL!")
                                    }
                                }, onFailure = { exception: Exception ->
                                    Log.d("FIREBASE", "COLD NOT LOAD USER! $exception")
                                })
                        }
                        .addOnFailureListener { ex ->
                            Log.d("FIRESTORE", ex.toString())
                        }
                }
            } else {
                Log.e(TAG, "Registration failed $email, $password")
            }
        }
}