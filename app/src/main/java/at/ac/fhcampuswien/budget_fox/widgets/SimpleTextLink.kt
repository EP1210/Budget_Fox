package at.ac.fhcampuswien.budget_fox.widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SimpleTextLink(
    name: String,
    modifier: Modifier = Modifier,
    event: () -> Unit
) {
    TextButton(
        onClick = event,
        modifier = modifier
            .padding(top = 10.dp)
    ) {
        Text(
            text = name
        )
    }
}