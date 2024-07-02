package at.ac.fhcampuswien.budget_fox.widgets

import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun SimpleCheckbox(
    isChecked: Boolean,
    event: (Boolean) -> Unit
) {
    var checked by remember {
        mutableStateOf(value = isChecked)
    }

    Checkbox(
        checked = checked,
        onCheckedChange = {
            checked = it
            event(checked)
        }
    )
}