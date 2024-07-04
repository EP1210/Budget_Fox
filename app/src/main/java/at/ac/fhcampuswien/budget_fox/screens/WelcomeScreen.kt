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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.R
import at.ac.fhcampuswien.budget_fox.navigation.Screen
import at.ac.fhcampuswien.budget_fox.view_models.ViewModelFactory
import at.ac.fhcampuswien.budget_fox.view_models.WelcomeViewModel
import at.ac.fhcampuswien.budget_fox.widgets.SimpleButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTextLink
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTitle
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

@Composable
fun WelcomeScreen(
    navigationController: NavController
) {
    val factory = ViewModelFactory()
    val viewModel: WelcomeViewModel = viewModel(factory = factory)

    //region Firebase Cloud Messaging
    FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
        if (!task.isSuccessful) {
            Log.w("PUSH-NOTIFICATIONS", "Fetching FCM registration token failed", task.exception)
            return@OnCompleteListener
        }
        // Get new FCM registration token
        val token = task.result

        Log.d("PUSH-NOTIFICATIONS", token)
    })
    //endregion

    //region Autologin
    viewModel.checkIfUserIsLoggedIn { uid ->
        navigationController.navigate(route = Screen.UserProfile.setArguments(userId = uid)) {
            popUpTo(id = 0)
        }
    }
    //endregion

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
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