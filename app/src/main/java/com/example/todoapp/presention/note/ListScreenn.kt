package com.example.todoapp.presention.note

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoapp.presention.compenent.DatePickerDialogComponent
import com.example.todoapp.presention.compenent.SelectField
import com.example.todoapp.presention.compenent.TimePickerDialogComponent
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ListScreen(
    onBack: () -> Unit,
    viewModel: ListViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        viewModel.setSong(uri?.toString())
    }

    val displayDate =
        uiState.selectedDateMillis?.let {
            SimpleDateFormat(
                "dd MMM yyyy",
                Locale.getDefault()
            ).format(Date(it))
        } ?: ""

    val displayTime =
        if (uiState.selectedHour != null && uiState.selectedMinute != null) {
            String.format(
                "%02d:%02d",
                uiState.selectedHour,
                uiState.selectedMinute
            )
        } else {
            ""
        }


    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.title,
            onValueChange = viewModel::onTitleChange,
            label = {
                Text("Başlık")
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.content,
            onValueChange = viewModel::onContentChange,
            minLines = 10,
            label = {
                Text("İçerik")
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        SelectField(
            title = "TARİH",
            value = displayDate,
            placeholder = "Tarih Seçiniz...",
            onClick = {
                viewModel.showDatePicker(true)
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        SelectField(
            title = "SAAT",
            value = displayTime,
            placeholder = "Saat Seçiniz...",
            onClick = {
                viewModel.showTimePicker(true)
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        SelectField(
            title = "ŞARKI",
            value = uiState.selectedSongUri?.substringAfterLast("/") ?: "",
            placeholder = "Şarkı Seçiniz...",
            onClick = {
                launcher.launch("audio/*")
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            modifier = Modifier.align(Alignment.End),
            onClick = {
                viewModel.saveNote(onBack)
            }
        ) {
            Text("Kaydet")
        }

        DatePickerDialogComponent(
            show = uiState.showDatePicker,
            onDismiss = {
                viewModel.showDatePicker(false)
            },
            onDateSelected = {
                viewModel.setDate(it)
                viewModel.showDatePicker(false)
            }
        )

        TimePickerDialogComponent(
            show = uiState.showTimePicker,
            onDismiss = {
                viewModel.showTimePicker(false)
            },
            onTimeSelected = { hour, minute ->
                viewModel.setTime(hour, minute)
                viewModel.showTimePicker(false)
            }
        )
    }
}