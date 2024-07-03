package at.ac.fhcampuswien.budget_fox.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.navigation.Screen
import at.ac.fhcampuswien.budget_fox.view_models.UserProfileViewModel
import at.ac.fhcampuswien.budget_fox.view_models.ViewModelFactory
import at.ac.fhcampuswien.budget_fox.widgets.SimpleBottomNavigationBar
import at.ac.fhcampuswien.budget_fox.widgets.SimpleButton
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Composable
fun UserProfileScreen(
    navigationController: NavController,
    route: String,
    userId: String?
) {
    val factory = ViewModelFactory()
    val viewModel: UserProfileViewModel = viewModel(factory = factory)

    if (userId == null) {
        Text("User not found")
        return
    }

    Scaffold(
        bottomBar = {
            SimpleBottomNavigationBar(
                navigationController = navigationController,
                currentRoute = route
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
        ) {
            val auth = Firebase.auth

            val userMail = auth.currentUser?.email

            var userName by remember {
                mutableStateOf("")
            }

            var userBirthDate by remember {
                mutableStateOf(LocalDateTime.now())
            }

            var userRegistrationDate by remember {
                mutableStateOf(LocalDateTime.now())
            }


            val user = viewModel.getUserData(userId = userId).collectAsState()
            userName = user.value?.firstName + " " + user.value?.lastName

            val registrationEpoch =
                user.value?.dateOfRegistrationInEpoch?.let { Instant.ofEpochSecond(it) }
            if (registrationEpoch != null) {
                userRegistrationDate = LocalDateTime.ofInstant(registrationEpoch, ZoneOffset.UTC)
            }

            val birthdayEpoch = user.value?.dateOfBirthInEpoch?.let { Instant.ofEpochSecond(it) }
            if (birthdayEpoch != null) {
                userBirthDate = LocalDateTime.ofInstant(birthdayEpoch, ZoneOffset.UTC)
            }


            /*SimpleTitle(
                title = when (viewModel.newUser) {
                    true -> "Registration successful!"
                    false -> "Personal information"
                }
            )*/
            Text(
                text = """E-Mail: $userMail
                |Name: $userName
                |Birth date: ${userBirthDate.dayOfMonth}.${userBirthDate.monthValue}.${userBirthDate.year}
                |Registration date: ${userRegistrationDate.dayOfMonth}.${userRegistrationDate.monthValue}.${userRegistrationDate.year}
                |Registration time: ${userRegistrationDate.hour}:${userRegistrationDate.minute}
            """.trimMargin()
            )

            SimpleButton(name = "Saving goals") {
                /*val userId = viewModel.getUserId()
                if (userId != "") {
                    val route = Screen.SavingGoalOverview.setArguments(userId = userId)
                    navigationController.navigate(route)
                }
                else
                {
                    //TODO: Display error message
                    Log.d("TAG", "Route empty")
                }*/
            }

            SimpleButton(name = "Logout") {
                auth.signOut()
                Log.d("TAG", "Signed out!")
                navigationController.navigate(Screen.Login.route) {
                    popUpTo(id = 0)
                }
            }
        }
    }
}

