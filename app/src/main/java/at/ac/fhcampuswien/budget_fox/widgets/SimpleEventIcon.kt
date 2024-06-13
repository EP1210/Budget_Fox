package at.ac.fhcampuswien.budget_fox.widgets

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun SimpleEventIcon(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    event: () -> Unit
) {
    IconButton(
        onClick = event,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Localized description"
        )
    }
}