package com.example.todoapp.presention.note

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoapp.presention.bottomsheet.AlarmBottomSheet
import com.example.todoapp.presention.bottomsheet.CustomBottomSheet

@Composable
fun ListScreen(
    onBack: () -> Unit,
    viewModel: ListViewModel = hiltViewModel()
){

    var showBottomSheet by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    var currentNoteId by remember { mutableStateOf<Int?>(null) }

    Column {
        Row {
            Button(
                onClick = {
                    if (currentNoteId == null) {
                        viewModel.insertNote(
                            title = title,
                            content = content,
                            onSaved = { newId -> currentNoteId = newId }
                        )
                    }
                    showBottomSheet = true
                },
                modifier = Modifier.padding(start = 12.dp, top = 20.dp, end = 12.dp, bottom = 12.dp)
            ) {
                Text("Alarm Ekle")
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.insertNote(title = title, content = content)
                    onBack()
                },
                modifier = Modifier.padding(start = 12.dp, top = 20.dp, end = 12.dp, bottom = 12.dp)
            ) {
                Text("Kaydet")
            }
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            value = title,
            onValueChange = { title = it },
            label = { Text("Başlık") }
        )

        OutlinedTextField(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            value = content,
            onValueChange = { content = it },
            minLines = 10,
            label = { Text("İçerik") }
        )
    }

    CustomBottomSheet(
        visible = showBottomSheet,
        onDismiss = { showBottomSheet = false }
    ) {
        currentNoteId?.let { noteId ->
            AlarmBottomSheet(
                noteId = noteId,
                viewModel = viewModel,
                onDismiss = { showBottomSheet = false }
            )
        } ?: Text("Önce notu kaydedin")
    }
}

@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column {

        Row {

            Button(
                onClick = {},
                modifier = Modifier.padding(
                    start = 12.dp,
                    top = 20.dp,
                    end = 12.dp,
                    bottom = 12.dp
                )
            ) {
                Text("Alarm Ekle")
            }


            Spacer(
                modifier = Modifier.weight(1f)
            )


            Button(
                onClick = {},
                modifier = Modifier.padding(
                    start = 12.dp,
                    top = 20.dp,
                    end = 12.dp,
                    bottom = 12.dp
                )
            ) {
                Text("Kaydet")
            }

        }


        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            value = title,
            onValueChange = { title = it },
            label = {
                Text("Başlık")
            }
        )


        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            value = content,
            onValueChange = { content = it },
            minLines = 10,
            label = {
                Text("İçerik")
            }
        )

    }
}