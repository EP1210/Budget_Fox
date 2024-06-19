package at.ac.fhcampuswien.budget_fox.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CategoryItem(
    categoryName: String,
    categoryDescription: String,
    edit: () -> Unit,
    delete: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(bottom = 7.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(start = 5.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = categoryName,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(weight = 1f)
                )
                SimpleEventIcon(
                    icon = Icons.Default.Edit
                ) {
                    edit()
                }
                SimpleEventIcon(
                    icon = Icons.Default.Delete,
                    colour = Color.Red
                ) {
                    delete()
                }
            }
            if (categoryDescription.isNotBlank()) {
                Text(
                    text = categoryDescription
                )
            }
        }
    }
}