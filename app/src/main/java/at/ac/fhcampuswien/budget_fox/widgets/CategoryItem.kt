package at.ac.fhcampuswien.budget_fox.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CategoryItem(
    categoryName: String,
    categoryDescription: String,
    deleteIcon: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 7.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 5.dp)
                    .weight(weight = 1f)
            ) {
                Text(
                    text = categoryName,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = categoryDescription
                )
            }
            deleteIcon()
        }
    }
}