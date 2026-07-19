package com.example.todoapp.presention.compenent

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialogComponent(
    show: Boolean,
    onDismiss: () -> Unit,
    onTimeSelected: (Int, Int) -> Unit
) {

    val timePickerState = rememberTimePickerState()

    if (show) {

        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {

                TextButton(
                    onClick = {

                        onTimeSelected(
                            timePickerState.hour,
                            timePickerState.minute
                        )

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

            },
            text = {

                TimePicker(
                    state = timePickerState
                )

            }
        )

    }
}