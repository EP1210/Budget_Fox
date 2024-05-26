package at.ac.fhcampuswien.budget_fox.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.ac.fhcampuswien.budget_fox.models.Income
import java.util.UUID

@Composable
fun TransactionListItem(income: Income) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .clickable {}
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = if (true) {
                Icons.Outlined.AddCircle
            } else {
                Icons.Outlined.ShoppingCart
            },
            contentDescription = income.description,
            modifier = Modifier.size(40.dp),
            tint = MaterialTheme.colorScheme.inverseOnSurface
        )
        Spacer(modifier = Modifier.width(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = income.description,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    fontSize = 16.sp
                )
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = if (true) {
                        "- ${income.amount}€"
                    } else {
                        "+ ${income.amount}€"
                    },
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    fontSize = 16.sp,
                )

                Text(
                    text = income.period.toString(),
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                )
            }
        }
    }
}

@Composable
@Preview
fun DefaultPreview() {
    TransactionListItem(Income(UUID.randomUUID(), 10.78, "FHCW"))
}