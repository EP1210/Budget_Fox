package at.ac.fhcampuswien.budget_fox.widgets

import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CategoryItem(
    name: String,
    description: String
) {
    Card {
        Text(
            text = name
        )
    }
}