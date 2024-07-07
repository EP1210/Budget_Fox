package at.ac.fhcampuswien.budget_fox.widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun SimpleNumberField(
    title: String,
    textDefaultValue: String = "",
    onValueChange: (String) -> Unit,
) {
    var textValue by remember {
        mutableStateOf(value = textDefaultValue)
    }

    OutlinedTextField(
        modifier = Modifier
            .padding(horizontal = 70.dp),
        value = textValue,
        onValueChange = { userInput ->
            if (userInput.matches(Regex("^\\d*\\.?\\d*\$"))) {
                textValue = userInput
                onValueChange(textValue)
            }
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
}