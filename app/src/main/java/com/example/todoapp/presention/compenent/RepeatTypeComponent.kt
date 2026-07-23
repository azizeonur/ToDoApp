package com.example.todoapp.presention.compenent

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.todoapp.data.database.RepeatType

@Composable
fun RepeatTypeComponent(
    repeatType: RepeatType,
    onRepeatTypeSelected: (RepeatType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    SelectField(
        title = "Repeat",
        value = repeatType.name,
        placeholder = "Select repeat type",
        onClick = {
            expanded = true
        }
    )

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = {
            expanded = false
        }
    ) {

        RepeatType.entries.forEach { type ->

            DropdownMenuItem(
                text = {
                    Text(type.name)
                },
                onClick = {
                    onRepeatTypeSelected(type)
                    expanded = false
                }
            )

        }
    }
}