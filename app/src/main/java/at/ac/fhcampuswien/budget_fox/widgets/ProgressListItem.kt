package at.ac.fhcampuswien.budget_fox.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProgressListItem(
    id: String = "",
    name: String,
    isDone: Boolean = false,
    progress: Double = 0.0,
    amount: Double = 0.0,
    onClick: () -> Unit = {},
    onEdit: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp) //"Margin" https://stackoverflow.com/a/65582416
            .border(0.dp, Color.Transparent)
            .clip(RoundedCornerShape(6.dp))
            .background(MaterialTheme.colorScheme.primary)

            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        onEdit(id)
                    },
                    onTap = {
                        onClick()
                    }
                )
            }
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (isDone) "$name (✓)" else name,
                color = MaterialTheme.colorScheme.inverseOnSurface,
                fontSize = 16.sp
            )
            Column(
                horizontalAlignment = Alignment.End, modifier = Modifier.weight(weight = 4f)
            ) {
                Text(
                    text = "$progress / $amount €",
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    fontSize = 16.sp,
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
        ) {
            LinearProgressIndicator(
                progress = { (progress / amount).toFloat() }, modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.inverseOnSurface),
                color = Color.Green
            )
        }
    }
}