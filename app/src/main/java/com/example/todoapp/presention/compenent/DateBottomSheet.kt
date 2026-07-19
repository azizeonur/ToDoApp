package com.example.todoapp.presention.compenent


import android.app.TimePickerDialog
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.presention.note.ListViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmBottomSheet(
    noteId: Int,
    viewModel: ListViewModel,
    onDismiss: () -> Unit
) {
    var showTimePicker by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    var selectedDateMillis by remember { mutableStateOf<Long?>(null) }
    var selectedHour by remember { mutableStateOf<Int?>(null) }
    var selectedMinute by remember { mutableStateOf<Int?>(null) }
    var selectedSongUri by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val datePickerState = rememberDatePickerState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedSongUri = uri?.toString()
    }

    val triggerTimeMillis: Long? = remember(selectedDateMillis, selectedHour, selectedMinute) {
        if (selectedDateMillis != null && selectedHour != null && selectedMinute != null) {
            val cal = Calendar.getInstance().apply {
                timeInMillis = selectedDateMillis!!
                set(Calendar.HOUR_OF_DAY, selectedHour!!)
                set(Calendar.MINUTE, selectedMinute!!)
                set(Calendar.SECOND, 0)
            }
            cal.timeInMillis
        } else null
    }

    val displayDate = selectedDateMillis?.let {
        SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(it))
    } ?: "Tarih seçilmedi"

    val displayTime = if (selectedHour != null && selectedMinute != null) {
        String.format("%02d:%02d", selectedHour, selectedMinute)
    } else "Saat seçilmedi"

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Alarm Ayarla", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "📅 $displayDate",
            color = if (selectedDateMillis != null) Color(0xFF6C4AB6) else Color.Gray
        )
        OutlinedButton(onClick = { showDatePicker = true }) {
            Text("Tarih Seç")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "⏰ $displayTime",
            color = if (selectedHour != null) Color(0xFF6C4AB6) else Color.Gray
        )
        OutlinedButton(onClick = { showTimePicker = true }) {
            Text("Saat Seç")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (selectedSongUri != null) "🎵 Şarkı seçildi" else "🎵 Şarkı seçilmedi",
            color = if (selectedSongUri != null) Color(0xFF6C4AB6) else Color.Gray
        )
        OutlinedButton(onClick = { launcher.launch("audio/*") }) {
            Text("Şarkı Seç")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                triggerTimeMillis?.let { time ->
                    viewModel.addAlarm(
                        noteId = noteId,
                        triggerTimeMillis = time,
                        label = selectedSongUri ?: ""
                    )
                    onDismiss()
                }
            },
            enabled = triggerTimeMillis != null
        ) {
            Text("Kaydet")
        }
    }

    if (showTimePicker) {
        TimePickerDialog(
            context,
            { _, hour, minute ->
                selectedHour = hour
                selectedMinute = minute
                showTimePicker = false
            },
            selectedHour ?: 12,
            selectedMinute ?: 0,
            true
        ).show()
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                Button(onClick = {
                    selectedDateMillis = datePickerState.selectedDateMillis
                    showDatePicker = false
                }) {
                    Text("Tamam")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}