package at.ac.fhcampuswien.budget_fox.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.navigation.Screen
import at.ac.fhcampuswien.budget_fox.view_models.UserViewModel
import at.ac.fhcampuswien.budget_fox.widgets.SimpleBottomNavigationBar
import at.ac.fhcampuswien.budget_fox.widgets.SimpleButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTitle
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Composable
fun UserProfileScreen(
    navigationController: NavController,
    viewModel: UserViewModel,
    route: String
) {
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


            val user = viewModel.user
            userName = user?.firstName + " " + user?.lastName
            userBirthDate = LocalDateTime.ofInstant(user?.dateOfBirthInEpoch?.let {
                Instant.ofEpochSecond(
                    it
                )
            }, ZoneOffset.UTC)
            userRegistrationDate = LocalDateTime.ofInstant(user?.dateOfRegistrationInEpoch?.let {
                Instant.ofEpochSecond(
                    it
                )
            }, ZoneOffset.UTC)


            SimpleTitle(
                title = when (viewModel.newUser) {
                    true -> "Registration successful!"
                    false -> "Personal information"
                }
            )
            Text(
                text = """E-Mail: $userMail
                |Name: $userName
                |Birth date: ${userBirthDate.dayOfMonth}.${userBirthDate.monthValue}.${userBirthDate.year}
                |Registration date: ${userRegistrationDate.dayOfMonth}.${userRegistrationDate.monthValue}.${userRegistrationDate.year}
                |Registration time: ${userRegistrationDate.hour}:${userRegistrationDate.minute}
            """.trimMargin()
            )

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

