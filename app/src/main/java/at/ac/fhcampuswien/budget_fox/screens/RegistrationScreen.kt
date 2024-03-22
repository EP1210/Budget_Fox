package at.ac.fhcampuswien.budget_fox.screens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import at.ac.fhcampuswien.budget_fox.models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

fun userToDatabase(user: User): Map<String, Any> {
    return mapOf(
        "firstName" to user.firstName,
        "lastName" to user.lastName,
        "dateOfBirth" to user.dateOfBirth.toEpochDays()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationForm() {
    val fieldLabels = listOf("First name", "Last name", /*"Date of birth" "E-Mail", "Password", "Confirm password"*/)
    val user by remember {
        mutableStateOf(value = User())
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "User registration",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        var email by remember {
            mutableStateOf(value = "")
        }
        var password by remember {
            mutableStateOf(value = "")
        }
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text(
                    text = "E-Mail"
                )
            })
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text(
                    text = "Password"
                )
            })

        fieldLabels.forEach { fieldLabel ->
            var userInput by remember {
                mutableStateOf(value = "")
            }
            OutlinedTextField(
                value = userInput,
                onValueChange = {
                    userInput = it
                    when (fieldLabel) {
                        fieldLabels[0] -> user.firstName = userInput
                        fieldLabels[1] -> user.lastName = userInput
                    }
                },
                label = {
                    Text(
                        text = fieldLabel
                    )
                }
            )
        }
        Button(
            onClick = {
                registerUser(user = user, email, password)
            }
        ) {
            Text(
                text = "Register"
            )
        }
    }
}

fun registerUser(user: User, email: String, password: String) {
    val auth = Firebase.auth

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "Registration OK $email, $password")
                val firebaseUser = auth.currentUser

                if (firebaseUser != null) {
                    createUserEntryInDatabase(user = user, firebaseUser = firebaseUser)
                }
            } else {
                Log.e(TAG, "Registration failed $email, $password")
            }
        }
}

fun createUserEntryInDatabase(user: User, firebaseUser: FirebaseUser) {
    val database = Firebase.firestore

    database.collection("users").document(firebaseUser.uid).set(userToDatabase(user = user))
}

@Composable
@Preview(showBackground = true)
fun Preview() {
    RegistrationForm()
}
