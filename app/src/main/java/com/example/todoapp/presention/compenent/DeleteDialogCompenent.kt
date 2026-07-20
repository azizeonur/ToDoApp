package com.example.todoapp.presention.compenent

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.todoapp.R

@Composable
fun DeleteDialogComponent(
    show: Boolean,
    title: String,
    message: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {

    if (show) {

        AlertDialog(
            onDismissRequest = onDismiss,

            title = {
                Text(title)
            },

            text = {
                Text(message)
            },

            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirm()
                        onDismiss()
                    }
                ) {
                    Text(stringResource(R.string.evet))
                }
            },

            dismissButton = {
                TextButton(
                    onClick = onDismiss
                ) {
                    Text(stringResource(R.string.hayır))                }
            }
        )
    }
}