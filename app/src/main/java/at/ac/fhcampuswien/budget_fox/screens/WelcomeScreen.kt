package at.ac.fhcampuswien.budget_fox.screens

import android.content.ContentValues.TAG
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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.messaging.FirebaseMessaging

@Composable
fun WelcomeScreen(
    navigationController: NavController,
    viewModel: UserViewModel
) {
    FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
        if (!task.isSuccessful) {
            Log.w(TAG, "Fetching FCM registration token failed", task.exception)
            return@OnCompleteListener
        }

        // Get new FCM registration token
        val token = task.result

        // Log and toast
        //val msg = getString(R.string.msg_token_fmt, token)
        Log.d(TAG, token)
        //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
    })
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
        else
        {
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