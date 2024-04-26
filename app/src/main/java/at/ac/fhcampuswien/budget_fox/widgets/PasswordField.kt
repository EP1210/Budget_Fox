package at.ac.fhcampuswien.budget_fox.widgets

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun passwordField(): String {
    var password by remember {
        mutableStateOf(value = "")
    }
    var plaintextPassword by remember {
        mutableStateOf(value = false)
    }

    OutlinedTextField(
        value = password,
        onValueChange = { userInput ->
            password = userInput
        },
        label = {
            Text(
                text = "Password"
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        visualTransformation = when {
            !plaintextPassword -> PasswordVisualTransformation()
            else -> VisualTransformation.None
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    plaintextPassword = !plaintextPassword
                }
            ) {
                Icon(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = null
                )
            }
        }
    )

    return password
}