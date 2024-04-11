package at.ac.fhcampuswien.budget_fox.widgets

import androidx.compose.foundation.clickable
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dateField(): LocalDateTime {
    val datePickerState = rememberDatePickerState()
    val showDialog = rememberSaveable { mutableStateOf(false) }

    var dateOfBirth by remember {
        mutableStateOf(LocalDateTime.now())
    }


    // https://material.io/blog/material-3-compose-1-1
    if (showDialog.value) {
        DatePickerDialog(
            onDismissRequest = { showDialog.value = false },
            confirmButton = {
                TextButton(onClick = { showDialog.value = false }) {
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

    if (datePickerState.selectedDateMillis != null) {
        dateOfBirth = Instant.fromEpochMilliseconds(datePickerState.selectedDateMillis!!)
            .toLocalDateTime(timeZone = TimeZone.currentSystemDefault()).toJavaLocalDateTime()
    }

    OutlinedTextField(
        value = "${dateOfBirth.dayOfMonth} ${dateOfBirth.month} ${dateOfBirth.year}",
        onValueChange = {},
        modifier = Modifier
            .clickable(onClick = {
                showDialog.value = true
            }),
        enabled = false,
        label = {
            Text("Date of birth")
        }
    )

    return dateOfBirth
}
