package at.ac.fhcampuswien.budget_fox.screens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toLocalDateTime

fun userToDatabase(user: User): Map<String, Any> {
    return mapOf(
        "firstName" to user.firstName,
        "lastName" to user.lastName,
        "dateOfBirthInUnixDays" to user.dateOfBirth.toEpochDays()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationForm() {
    val fieldLabels = listOf(
        "First name",
        "Last name", /*"Date of birth" "E-Mail", "Password", "Confirm password"*/
    )
    val user by remember {
        mutableStateOf(value = User()) // ? muss state sein
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        val datePickerState = rememberDatePickerState()
        val showDialog = rememberSaveable { mutableStateOf(false) }

        // https://material.io/blog/material-3-compose-1-1
        if (showDialog.value) {
            DatePickerDialog(
                onDismissRequest = { showDialog.value = false },
                confirmButton = {
                    TextButton(onClick = { showDialog.value = false }) {
                        Text("Ok")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog.value = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
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

        var dateOfBirth by remember {
            mutableStateOf(java.time.LocalDate.now())
        }

        if (datePickerState.selectedDateMillis != null) {
            val birthDateTime = Instant.fromEpochMilliseconds(datePickerState.selectedDateMillis!!)
                .toLocalDateTime(timeZone = TimeZone.currentSystemDefault())

            val birthLocalDate =
                LocalDate(birthDateTime.year, birthDateTime.month, birthDateTime.dayOfMonth)

            dateOfBirth = birthLocalDate.toJavaLocalDate()
            user.dateOfBirth = birthLocalDate
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

        OutlinedTextField(value = "${dateOfBirth.dayOfMonth} ${dateOfBirth.month} ${dateOfBirth.year}",
            onValueChange = {},
            modifier = Modifier
                .clickable(onClick = {
                    showDialog.value = true
                }),
            enabled = false,
            label = {
                Text("Date of birth")
            }
        )

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
        .addOnCompleteListener { task ->
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
