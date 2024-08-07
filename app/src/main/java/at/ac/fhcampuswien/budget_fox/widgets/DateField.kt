package at.ac.fhcampuswien.budget_fox.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateField(
    description: String,
    onValueChanged: (LocalDateTime) -> Unit
) {
    val initialDate = LocalDateTime.now()
    val initialDateMillis = initialDate.toEpochMillis()
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialDateMillis)
    val showDialog = rememberSaveable { mutableStateOf(false) }
    var selectedDate by rememberSaveable {
        mutableStateOf(initialDate)
    }

    if (showDialog.value) {
        DatePickerDialog(
            onDismissRequest = { showDialog.value = false },
            confirmButton = {
                TextButton(onClick = {
                    showDialog.value = false
                    if (datePickerState.selectedDateMillis != null) {
                        selectedDate =
                            Instant.fromEpochMilliseconds(datePickerState.selectedDateMillis!!)
                                .toLocalDateTime(TimeZone.currentSystemDefault())
                                .toJavaLocalDateTime()
                        onValueChanged(selectedDate)
                    }
                }) {
                    Text("Ok")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog.value = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    OutlinedTextField(
        value = "${selectedDate.dayOfMonth} ${selectedDate.month} ${selectedDate.year}",
        onValueChange = {},
        modifier = Modifier
            .padding(horizontal = 70.dp)
            .clickable {
                showDialog.value = true
            },
        enabled = false,
        label = { Text(description) },
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 16.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledBorderColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}

private fun LocalDateTime.toEpochMillis(): Long {
    return this.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli()
}
