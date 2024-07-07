package at.ac.fhcampuswien.budget_fox.widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SimpleField(
    title: String,
    defaultValue: String = "",
    onValueChange: (String) -> Unit
) {
    var textValue by remember {
        mutableStateOf(value = defaultValue)
    }

    OutlinedTextField(
        modifier = Modifier
            .padding(horizontal = 70.dp),
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