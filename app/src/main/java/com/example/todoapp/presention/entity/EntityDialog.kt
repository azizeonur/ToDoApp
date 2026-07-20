package com.example.todoapp.presention.entity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.todoapp.R

@Composable
fun EntityDialog(
    uiState: EntityUiState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {

    AlertDialog(

        onDismissRequest = onDismiss,

        title = {
            Text(stringResource(R.string.new_category))
        },

        text = {
            Column {

                OutlinedTextField(
                    value = uiState.title,
                    onValueChange = onTitleChange,
                    label = {
                        Text(stringResource(R.string.category_name))
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = uiState.description,
                    onValueChange = onDescriptionChange,
                    label = {
                        Text(stringResource(R.string.category_description))
                    }
                )
            }

        },

        dismissButton = {

            TextButton(
                onClick = onDismiss
            ) {
                Text(stringResource(R.string.cancel))            }

        },

        confirmButton = {

            TextButton(
                onClick = onSave
            ) {
                Text(stringResource(R.string.SAVE))            }
        }

    )
}