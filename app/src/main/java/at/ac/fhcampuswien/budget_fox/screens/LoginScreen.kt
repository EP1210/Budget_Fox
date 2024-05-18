package at.ac.fhcampuswien.budget_fox.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import at.ac.fhcampuswien.budget_fox.navigation.Screen
import at.ac.fhcampuswien.budget_fox.view_models.UserViewModel
import at.ac.fhcampuswien.budget_fox.widgets.SimpleButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTextLink
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTitle
import at.ac.fhcampuswien.budget_fox.widgets.EmailField
import at.ac.fhcampuswien.budget_fox.widgets.PasswordField
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun LoginScreen(
    navigationController: NavController,
    viewModel: UserViewModel
) {
    var email by remember {
        mutableStateOf(value = "")
    }
    var password by remember {
        mutableStateOf(value = "")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 70.dp)
    ) {
        SimpleTitle(title = "Login")

        EmailField { mail ->
            email = mail
        }
        PasswordField { word ->
            password = word
        }

        SimpleButton(name = "Login") {
            if (email.isNotBlank() && password.isNotBlank()) {
                userLogin(
                    email = email,
                    password = password,
                    navController = navigationController,
                    viewModel = viewModel
                )
            }
        }
        SimpleTextLink(name = "Create account") {
            navigationController.navigate(route = Screen.Registration.route)
        }
    }
}

fun userLogin(
    email: String,
    password: String,
    navController: NavController,
    viewModel: UserViewModel
) {
    Firebase.auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                viewModel.setUserState(firstLogin = task.result.additionalUserInfo?.isNewUser)
                //TODO: Duplicate code!
                val repository = UserRepository()
                val firebaseUser = Firebase.auth.currentUser
                val uid = firebaseUser?.uid
                if (uid != null) {
                    repository.getAllDataFromUser(uid, //TODO: Leon Fragen
                        onSuccess = { user ->
                            if (user != null) {
                                viewModel.setUser(user)
                                navController.navigate(route = Screen.UserProfile.route) {
                                    popUpTo(id = 0)
                                }
                            } else {
                                Log.d("FIREBASE", "USER IS NULL!")
                            }
                        }, onFailure = { exception: Exception ->
                            Log.d("FIREBASE", "COLD NOT LOAD USER! $exception")
                        })
                }
                else
                {
                    Log.d("FIREBASE", "User is null!")
                }
            } else {
                // todo: display error message in login screen
            }
        }
}