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
fun EmailField(
    onValueChange: (String) -> Unit
) {
    var email by remember {
        mutableStateOf(value = "")
    }

    OutlinedTextField(
        modifier = Modifier
            .padding(horizontal = 70.dp),
        value = email,
        onValueChange = { userInput ->
            email = userInput
            onValueChange(email)
        },
        label = {
            Text(
                text = "E-Mail"
            )
        },
        singleLine = true
    )
}