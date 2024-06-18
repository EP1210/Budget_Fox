package at.ac.fhcampuswien.budget_fox.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lightspark.composeqr.QrCodeView

@Composable
fun HouseholdSettings(householdId: String) {
    QrCodeView(
        data = householdId,
        modifier = Modifier
            .padding(all = 16.dp)
            .fillMaxWidth()
    )
}