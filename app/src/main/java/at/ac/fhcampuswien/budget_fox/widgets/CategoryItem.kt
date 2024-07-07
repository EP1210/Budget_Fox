package at.ac.fhcampuswien.budget_fox.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryItem(
    categoryName: String,
    categoryDescription: String,
    categoryBudget: Double,
    edit: (String, String, Double) -> Unit,
    delete: () -> Unit,
    check: @Composable () -> Unit
) {
    var sheetVisible by remember {
        mutableStateOf(value = false)
    }
    var newName = ""
    var newDescription = ""
    var newBudget = 0.0

    Card(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .clickable {
                sheetVisible = true
            }
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
                check()
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
            if (categoryBudget != 0.0) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.Black
                )
                Text(
                    text = "Budget: $categoryBudget €"
                )
            }
        }
    }

    if (sheetVisible) {
        ModalBottomSheet(
            onDismissRequest = {
                sheetVisible = false
            }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    text = "Edit category \"$categoryName\"",
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                )
                SimpleField(
                    title = "New name"
                ) { name ->
                    newName = name
                }
                SimpleField(
                    title = "New description"
                ) { description ->
                    newDescription = description
                }
                SimpleNumberField(title = "Budget") { budget ->
                    if (budget.isNotBlank())
                        newBudget = budget.toDouble()
                }
                SimpleButton(
                    name = "Save"
                ) {
                    if (newName.isNotBlank()) {
                        edit(newName, newDescription, newBudget)
                    }
                }
                SimpleButton(
                    name = "Delete category"
                ) {
                    delete()
                    sheetVisible = false
                }
            }
        }
    }
}