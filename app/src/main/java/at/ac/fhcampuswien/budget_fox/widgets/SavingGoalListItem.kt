package at.ac.fhcampuswien.budget_fox.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.ac.fhcampuswien.budget_fox.models.SavingGoal
import at.ac.fhcampuswien.budget_fox.models.Transaction

@Composable
fun SavingGoalListItem(
    savingGoal: SavingGoal
) {
    val currentProgress by remember { mutableFloatStateOf((savingGoal.getProgress() / savingGoal.amount).toFloat()) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp) //"Margin" https://stackoverflow.com/a/65582416
            .border(0.dp, Color.Transparent)
            .clip(RoundedCornerShape(6.dp))
            .background(MaterialTheme.colorScheme.primary)
            .clickable {
                //TODO: Go to saving goal
            }
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = savingGoal.name,
                color = MaterialTheme.colorScheme.inverseOnSurface,
                fontSize = 16.sp
            )
            Column(
                horizontalAlignment = Alignment.End, modifier = Modifier.weight(weight = 4f)
            ) {
                Text(
                    text = "${savingGoal.getProgress()} / ${savingGoal.amount} €",
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
                progress = { currentProgress }, modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.inverseOnSurface),
                color = Color.Green
            )
        }
    }
}

@Composable
@Preview
fun Preview() {
    val goal = SavingGoal(
        name = "IntelliJ Jahreslizenz",
        amount = 202.80
    ) //https://www.jetbrains.com/de-de/idea/buy/?section=personal&billing=yearly
    goal.addTransaction(Transaction(amount = 10.0, description = "Oma Geburstag"))
    SavingGoalListItem(goal)
}