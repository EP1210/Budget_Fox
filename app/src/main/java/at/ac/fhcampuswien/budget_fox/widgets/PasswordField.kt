package at.ac.fhcampuswien.budget_fox.widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import at.ac.fhcampuswien.budget_fox.R

@Composable
fun PasswordField(
    onValueChange: (String) -> Unit
) {
    var password by remember {
        mutableStateOf(value = "")
    }
    var plaintextPassword by remember {
        mutableStateOf(value = false)
    }

    OutlinedTextField(
        modifier = Modifier
            .padding(horizontal = 70.dp),
        value = password,
        onValueChange = { userInput ->
            password = userInput
            onValueChange(password)
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
                    painter = when {
                        // Icon source: https://fonts.google.com/icons
                        plaintextPassword -> painterResource(id = R.drawable.visibility_off)
                        else -> painterResource(id = R.drawable.visibility)
                    },
                    contentDescription = null
                )
            }
        }
    )
}