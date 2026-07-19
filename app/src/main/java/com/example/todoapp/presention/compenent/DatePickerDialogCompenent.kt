package com.example.todoapp.presention.compenent

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogComponent(
    show: Boolean,
    onDismiss: () -> Unit,
    onDateSelected: (Long?) -> Unit
) {

    val datePickerState = rememberDatePickerState()

    if (show) {

        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(
                    onClick = {
                        onDateSelected(datePickerState.selectedDateMillis)
                        onDismiss()
                    }
                ) {
                    Text("Tamam")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onDismiss
                ) {
                    Text("İptal")
                }
            }
        ) {
            DatePicker(
                state = datePickerState
            )
        }
    }
}