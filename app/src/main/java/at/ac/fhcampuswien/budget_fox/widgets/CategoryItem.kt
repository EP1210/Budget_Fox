package at.ac.fhcampuswien.budget_fox.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
    modifier: Modifier = Modifier,
    edit: @Composable () -> Unit,
    delete: @Composable () -> Unit,
    toggle: @Composable () -> Unit
) {
    Card(
        modifier = modifier
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
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(weight = 1f)
                )
                edit()
                delete()
                toggle()
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