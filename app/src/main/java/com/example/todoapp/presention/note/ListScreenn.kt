package com.example.todoapp.presention.note

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoapp.R
import com.example.todoapp.data.database.RepeatType
import com.example.todoapp.presention.compenent.DatePickerDialogComponent
import com.example.todoapp.presention.compenent.RepeatTypeComponent
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
                Locale.getDefault(),
                "%02d:%02d",
                uiState.selectedHour,
                uiState.selectedMinute
            )
        } else {
            ""
        }

    val dateEnabled = uiState.repeatType == RepeatType.NONE
    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.title,
            onValueChange = viewModel::onTitleChange,
            label = {
                Text(stringResource(R.string.title))
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.content,
            onValueChange = viewModel::onContentChange,
            minLines = 10,
            label = {
                Text(stringResource(R.string.content))
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        RepeatTypeComponent(
            repeatType = uiState.repeatType,
            onRepeatTypeSelected = viewModel::setRepeatType
        )
        Spacer(modifier = Modifier.height(12.dp))

        SelectField(
            title = stringResource(R.string.date),
            value = displayDate,
            placeholder = stringResource(R.string.select_date),
            onClick = {
                viewModel.showDatePicker(true)
            },
            enabled = dateEnabled,
        )

        Spacer(modifier = Modifier.height(12.dp))

        SelectField(
            title = stringResource(R.string.time),
            value = displayTime,
            placeholder = stringResource(R.string.select_time),
            onClick = {
                viewModel.showTimePicker(true)
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        SelectField(
            title = stringResource(R.string.song),
            value = uiState.selectedSongUri?.substringAfterLast("/") ?: "",
            placeholder = stringResource(R.string.select_song),
            onClick = {
                launcher.launch("audio/*")
            }
        )

        Spacer(modifier = Modifier.height(12.dp))
        val context = LocalContext.current
        Button(
            modifier = Modifier.align(Alignment.End),
            onClick = {
                viewModel.saveNote(
                    onSaved = onBack,
                    onError = {
                        Toast.makeText(
                            context,
                            context.getString(R.string.empty_note_message),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        ) {
            Text(stringResource(R.string.SAVE))
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