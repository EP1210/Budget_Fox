package at.ac.fhcampuswien.budget_fox.widgets

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun SimpleField(
    title: String,
    onValueChange: (String) -> Unit
) {
    var textValue by remember {
        mutableStateOf(value = "")
    }

    OutlinedTextField(
        value = textValue,
        onValueChange = { userInput ->
            textValue = userInput
            onValueChange(textValue)
        },
        label = {
            Text(
                text = title
            )
        },
        singleLine = true
    )
}