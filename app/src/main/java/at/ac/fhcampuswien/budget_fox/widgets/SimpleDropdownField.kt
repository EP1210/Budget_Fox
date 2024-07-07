package at.ac.fhcampuswien.budget_fox.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SimpleDropdownField(
    placeholder: String,
    items: List<String>,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<String?>(null) }

    Column {
        Box(modifier = Modifier
                .clickable { expanded = true }
                .align(Alignment.CenterHorizontally)) {
            BasicText(
                text = selectedItem ?: placeholder,
                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                modifier = Modifier
                    .padding(20.dp)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                items.forEach { label ->
                    DropdownMenuItem(
                        { Text(text = label, color = MaterialTheme.colorScheme.onSurfaceVariant) },
                        onClick = {
                            selectedItem = label
                            expanded = false
                            onItemSelected(label)
                        }
                    )
                }
            }
        }
    }
}
