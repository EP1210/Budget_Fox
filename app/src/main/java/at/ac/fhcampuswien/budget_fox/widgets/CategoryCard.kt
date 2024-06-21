package at.ac.fhcampuswien.budget_fox.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoryCard(
    categoryName: String,
    categoryDescription: String,
    checked: Boolean,
    modifier: Modifier = Modifier,
    edit: () -> Unit,
    delete: () -> Unit,
    checkEvent: () -> Unit
) {
    var isChecked by remember {
        mutableStateOf(value = checked)
    }

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
                SimpleEventIcon(
                    icon = Icons.Default.Edit,
                    colour = Color.Blue,
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
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = {
                        checkEvent()
                        isChecked = it
                    }
                )
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