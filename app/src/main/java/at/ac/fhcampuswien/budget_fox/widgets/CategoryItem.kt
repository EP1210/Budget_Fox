package at.ac.fhcampuswien.budget_fox.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoryItem(
    categoryName: String,
    categoryDescription: String,
    edit: () -> Unit,
    delete: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(all = 10.dp)
            .width(width = 350.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 5.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = categoryName,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(weight = 1f)
                )
                SimpleEventIcon(
                    icon = Icons.Default.Edit,
                    contentDescription = "An icon to edit the category"
                ) {
                    edit()
                }
                SimpleEventIcon(
                    icon = Icons.Default.Delete,
                    colour = Color.Red,
                    contentDescription = "An icon to delete the category"
                ) {
                    delete()
                }
            }
            if (categoryDescription.isNotBlank()) {
                HorizontalDivider(
                    thickness = 3.dp,
                    color = Color.Black
                )
                Text(
                    text = categoryDescription
                )
            }
        }
    }
}