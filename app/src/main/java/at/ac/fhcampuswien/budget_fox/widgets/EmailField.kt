package at.ac.fhcampuswien.budget_fox.widgets

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun emailField(): String {
    var email by remember {
        mutableStateOf(value = "")
    }

    OutlinedTextField(
        value = email,
        onValueChange = { userInput ->
            email = userInput
        },
        label = {
            Text(
                text = "E-Mail"
            )
        },
        singleLine = true
    )

    return email
}