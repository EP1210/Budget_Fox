package at.ac.fhcampuswien.budget_fox.widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SimpleButton(
    name: String,
    modifier: Modifier = Modifier,
    event: () -> Unit
) {
    FilledTonalButton(
        onClick = event,
        modifier = modifier
            .padding(top = 10.dp)
    ) {
        Text(
            text = name
        )
    }
}