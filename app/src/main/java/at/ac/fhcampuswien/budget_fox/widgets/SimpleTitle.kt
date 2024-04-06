package at.ac.fhcampuswien.budget_fox.widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SimpleTitle( // can be used in registration or login screen
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        modifier = modifier
            .padding(bottom = 10.dp)
    )
}