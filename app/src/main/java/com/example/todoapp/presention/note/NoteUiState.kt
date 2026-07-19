package com.example.todoapp.presention.note

data class NoteUiState(
    val title: String = "",
    val content: String = "",
    val selectedDateMillis: Long? = null,
    val selectedHour: Int? = null,
    val selectedMinute: Int? = null,
    val selectedSongUri: String? = null,
    val showDatePicker: Boolean = false,
    val showTimePicker: Boolean = false
)