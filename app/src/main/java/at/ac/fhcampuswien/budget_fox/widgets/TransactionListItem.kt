package at.ac.fhcampuswien.budget_fox.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.models.Transaction
import at.ac.fhcampuswien.budget_fox.navigation.Screen
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TransactionListItem(
    navigationController: NavController? = null,
    transaction: Transaction
) {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp) //"Margin" https://stackoverflow.com/a/65582416
            .border(0.dp, Color.Transparent)
            .clip(RoundedCornerShape(6.dp))
            .background(MaterialTheme.colorScheme.primary)
            .clickable {
                navigationController?.navigate(route = Screen.Category.route)
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
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
            fontSize = 16.sp
        )
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.weight(weight = 4f)
        ) {
            Text(
                text = "${transaction.amount}€",
                color = MaterialTheme.colorScheme.inverseOnSurface,
                fontSize = 16.sp,
            )
            Text(
                text = format.format(transaction.date),
                color = MaterialTheme.colorScheme.inverseOnSurface,
            )
        }
    }
}