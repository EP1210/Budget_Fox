package at.ac.fhcampuswien.budget_fox.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.R
import at.ac.fhcampuswien.budget_fox.data.UserRepository
import at.ac.fhcampuswien.budget_fox.navigation.Screen
import at.ac.fhcampuswien.budget_fox.view_models.UserViewModel
import at.ac.fhcampuswien.budget_fox.widgets.SimpleButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTextLink
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTitle
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun WelcomeScreen(
    navigationController: NavController,
    viewModel: UserViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        //TODO: Duplicate code!
        val repository = UserRepository()
        val firebaseUser = Firebase.auth.currentUser
        val uid = firebaseUser?.uid

        if (uid != null) {
            repository.getAllDataFromUser(uid, //TODO: Leon Fragen
                onSuccess = { user ->
                    if (user != null) {
                        viewModel.setUser(user = user)
                        navigationController.navigate(route = Screen.UserProfile.route) {
                            popUpTo(id = 0)
                        }
                    } else {
                        Log.d("FIREBASE", "USER IS NULL!")
                    }
                }, onFailure = { exception: Exception ->
                    Log.d("FIREBASE", "COLD NOT LOAD USER! $exception")
                })
        } else {
            Log.d("FIREBASE", "User is not logged in!")
        }

        Image(
            painter = painterResource(id = R.drawable.fox_head),
            contentDescription = "Fox head logo"
        )

        SimpleTitle(title = "Welcome to Budget Fox")

        SimpleButton(name = "Create account") {
            navigationController.navigate(route = Screen.Registration.route)
        }
        SimpleButton(name = "Login") {
            navigationController.navigate(route = Screen.Login.route)
        }

        val uriHandler = LocalUriHandler.current

        SimpleTextLink(name = "About us") {
            uriHandler.openUri("https://github.com/EP1210/Budget_Fox")
        }
    }
}