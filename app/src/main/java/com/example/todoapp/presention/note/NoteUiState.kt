package com.example.todoapp.presention.note

import com.example.todoapp.data.database.RepeatType

data class NoteUiState(
    val title: String = "",
    val content: String = "",
    val repeatType: RepeatType = RepeatType.NONE,
    val selectedDateMillis: Long? = null,
    val selectedHour: Int? = null,
    val selectedMinute: Int? = null,
    val selectedSongName: String? = null,
    val selectedSongUri: String? = null,
    val showDatePicker: Boolean = false,
    val showTimePicker: Boolean = false
)