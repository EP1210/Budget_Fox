package at.ac.fhcampuswien.budget_fox.screens

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
import at.ac.fhcampuswien.budget_fox.navigation.Screen
import at.ac.fhcampuswien.budget_fox.widgets.SimpleButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTextButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTitle
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun WelcomeScreen(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (Firebase.auth.currentUser != null) {
            navController.navigate(route = Screen.UserProfile.route) {
                popUpTo(id = 0)
            }
        }

        Image(
            painter = painterResource(id = R.drawable.fox_head),
            contentDescription = "Fox head logo"
        )

        SimpleTitle(title = "Welcome to Budget Fox")

        SimpleButton(name = "Create account") {
            navController.navigate(route = Screen.Registration.route)
        }
        SimpleButton(name = "Login") {
            navController.navigate(route = Screen.Login.route)
        }

        val uriHandler = LocalUriHandler.current

        SimpleTextButton(name = "About us") {
            uriHandler.openUri("https://github.com/EP1210/Budget_Fox")
        }
    }
}