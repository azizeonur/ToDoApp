package com.example.todoapp.presention.entity

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun EntityDialog(
    uiState: EntityUiState,
    onTitleChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {

    AlertDialog(

        onDismissRequest = onDismiss,

        title = {
            Text("Kategori Oluştur")
        },

        text = {

            OutlinedTextField(
                value = uiState.title,
                onValueChange = onTitleChange,
                label = {
                    Text("Kategori Adı")
                }
            )

        },

        dismissButton = {

            TextButton(
                onClick = onDismiss
            ) {
                Text("İptal")
            }

        },

        confirmButton = {

            TextButton(
                onClick = onSave
            ) {
                Text("Kaydet")
            }

        }

    )
}