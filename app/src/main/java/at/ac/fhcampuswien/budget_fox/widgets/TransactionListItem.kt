package at.ac.fhcampuswien.budget_fox.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.ac.fhcampuswien.budget_fox.models.Transaction
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TransactionListItem(
    transaction: Transaction,
    numbersVisible: MutableState<Boolean> = mutableStateOf(value = true),
    categoryNames: List<String> = emptyList(),
    deleteEnabled: Boolean = true,
    onDelete: (Transaction) -> Unit = {},
    onItemClick: (String) -> Unit = {}
) {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    var showDeleteButton by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp) //"Margin" https://stackoverflow.com/a/65582416
            .border(0.dp, Color.Transparent)
            .clip(RoundedCornerShape(6.dp))
            .background(MaterialTheme.colorScheme.primary)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        if(deleteEnabled)
                        {
                            showDeleteButton = true
                        }
                    },
                    onTap = {
                        onItemClick(transaction.uuid)
                    }
                )
            }
            .padding(16.dp)
    ) {
        Column {
            if (transaction.isRegular) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(Color(0xFFFFEBEE))
                        .border(1.dp, Color.Red, RoundedCornerShape(50))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "First regular",
                        color = Color.Black,
                        fontSize = 12.sp
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = if (transaction.amount >= 0) {
                        Icons.Outlined.AddCircle
                    } else {
                        Icons.Outlined.ShoppingCart
                    },
                    contentDescription = transaction.description,
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.inverseOnSurface
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = transaction.description,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(2f)
                )
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text =
                        if (numbersVisible.value) {
                            "${transaction.amount}€"
                        } else {
                            "*** €"
                        },
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                        fontSize = 16.sp,
                    )
                    Text(
                        text = format.format(transaction.date),
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                    )
                }
            }
            if (showDeleteButton) {
                IconButton(
                    onClick = {
                        onDelete(transaction)
                        showDeleteButton = false
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Transaction",
                        tint = Color.Red
                    )
                }
            }
            if (categoryNames.isNotEmpty()) {
                LazyRow {
                    items(items = categoryNames) { categoryName ->
                        Text(
                            text = "| $categoryName ",
                            color = MaterialTheme.colorScheme.inverseOnSurface
                        )
                    }
                }
            }
        }
    }
}