package com.example.todoapp.presention.compenent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CategoryBottomSheet(
    onSave: (String, String) -> Unit
) {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(
            text = "Kategori Oluştur"
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = {
                title = it
            },
            label = {
                Text("Kategori Adı")
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = description,
            onValueChange = {
                description = it
            },
            label = {
                Text("Kategori Açıklaması")
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier.align(Alignment.End),
            onClick = {

                if (title.isNotBlank()) {
                    onSave(
                        title.trim(),
                        description.trim()
                    )
                }

            }
        ) {
            Text("Kaydet")
        }
    }
}