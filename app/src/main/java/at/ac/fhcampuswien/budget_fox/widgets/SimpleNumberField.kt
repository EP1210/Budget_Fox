package at.ac.fhcampuswien.budget_fox.widgets

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun simpleNumberField(title: String): String {
    var textValue by remember {
        mutableStateOf(value = "")
    }

    OutlinedTextField(
        value = textValue,
        onValueChange = { userInput ->
            textValue = userInput
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        label = {
            Text(
                text = title
            )
        },
        singleLine = true
    )

    return textValue
}