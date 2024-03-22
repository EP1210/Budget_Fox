package at.ac.fhcampuswien.budget_fox.screens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
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
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun RegistrationForm() {
    var email by remember {
        mutableStateOf(value = "")
    }
    var password by remember {
        mutableStateOf(value = "")
    }

    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "User registration", fontSize = 30.sp, fontWeight = FontWeight.Bold)

        OutlinedTextField(value = email, onValueChange = {email = it}, label = {
            Text(text = "E-Mail")
        })
        OutlinedTextField(value = password, onValueChange = {password = it}, label = {
            Text(text = "Password")
        })

        Button(onClick = {
            registerUser(email, password)
        }) {
            Text(text="Register")
        }
    }
}

fun registerUser(email: String, password: String) {
    val auth = Firebase.auth

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener() { task ->
            if(task.isSuccessful) {
                Log.d(TAG,"Registration OK $email, $password")
            }
            else
            {
                Log.e(TAG, "Registration failed $email, $password")
            }
        }
}

@Composable
@Preview(showBackground = true)
fun Preview() {
    RegistrationForm()
}
