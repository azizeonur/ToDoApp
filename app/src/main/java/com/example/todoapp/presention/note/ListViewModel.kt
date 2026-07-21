package com.example.todoapp.presention.note

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.note.NoteEntity
import com.example.todoapp.domain.alarmUseCase.AlarmScheduler
import com.example.todoapp.domain.alarmUseCase.GetAlarmByNoteIdUseCase
import com.example.todoapp.domain.alarmUseCase.InsertAlarmUseCase
import com.example.todoapp.domain.alarmUseCase.UpdateAlarmUseCase
import com.example.todoapp.domain.noteUseCase.DeleteNoteUseCase
import com.example.todoapp.domain.noteUseCase.GetNotesByFolderIdUseCase
import com.example.todoapp.domain.noteUseCase.InsertNoteUseCase
import com.example.todoapp.domain.noteUseCase.UpdateNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import java.util.Calendar


@HiltViewModel
class ListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val updateAlarmUseCase: UpdateAlarmUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val insertAlarmUseCase: InsertAlarmUseCase,
    private val alarmScheduler: AlarmScheduler,
    private val getAlarmByNoteIdUseCase: GetAlarmByNoteIdUseCase,
    private val getNotesByFolderIdUseCase: GetNotesByFolderIdUseCase
) : ViewModel() {

    private val folderId: Int =
        checkNotNull(savedStateHandle["folderId"])

    private val _uiState = MutableStateFlow(NoteUiState())
    val uiState = _uiState.asStateFlow()

    private val _note = MutableStateFlow<NoteEntity?>(null)
    val note = _note.asStateFlow()

    init {
        loadNote()
    }

    private fun loadNote() {
        viewModelScope.launch {
            val note = getNotesByFolderIdUseCase(folderId)
            _note.value = note

            note?.let {

                val millis = it.selectedDate?.let { date ->
                    SimpleDateFormat(
                        "dd MMM yyyy",
                        Locale.getDefault()
                    ).parse(date)?.time
                }

                val hour =
                    it.selectedTime?.split(":")?.getOrNull(0)?.toIntOrNull()

                val minute =
                    it.selectedTime?.split(":")?.getOrNull(1)?.toIntOrNull()

                _uiState.value = _uiState.value.copy(
                    title = it.title,
                    content = it.content,
                    selectedDateMillis = millis,
                    selectedHour = hour,
                    selectedMinute = minute
                )
            }
        }
    }

    fun onTitleChange(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun onContentChange(content: String) {
        _uiState.value = _uiState.value.copy(content = content)
    }

    fun showDatePicker(show: Boolean) {
        _uiState.value = _uiState.value.copy(showDatePicker = show)
    }

    fun showTimePicker(show: Boolean) {
        _uiState.value = _uiState.value.copy(showTimePicker = show)
    }

    fun setDate(date: Long?) {
        _uiState.value = _uiState.value.copy(
            selectedDateMillis = date
        )
    }

    fun setTime(hour: Int, minute: Int) {
        Log.d("AlarmTest", "setTime Hour = $hour")
        Log.d("AlarmTest", "setTime Minute = $minute")

        _uiState.value = _uiState.value.copy(
            selectedHour = hour,
            selectedMinute = minute
        )

        Log.d("AlarmTest", "UiState Hour = ${_uiState.value.selectedHour}")
        Log.d("AlarmTest", "UiState Minute = ${_uiState.value.selectedMinute}")
    }

    fun setSong(uri: String?) {
        _uiState.value = _uiState.value.copy(
            selectedSongUri = uri
        )
    }

    fun isEditMode(): Boolean = note.value != null

    fun saveNote(
        onSaved: () -> Unit
    ) {
        viewModelScope.launch {

            val state = uiState.value

            val displayDate = state.selectedDateMillis?.let {
                SimpleDateFormat(
                    "dd MMM yyyy",
                    Locale.getDefault()
                ).format(Date(it))
            }

            val displayTime = if (
                state.selectedHour != null &&
                state.selectedMinute != null
            ) {
                String.format(
                    Locale.getDefault(),
                    "%02d:%02d",
                    state.selectedHour,
                    state.selectedMinute
                )
            } else {
                null
            }

            var noteId: Int

            if (isEditMode()) {

                val currentNote = note.value!!

                updateNoteUseCase(
                    currentNote.copy(
                        title = state.title,
                        content = state.content,
                        selectedDate = displayDate,
                        selectedTime = displayTime
                    )
                )

                noteId = currentNote.id

            } else {

                noteId = insertNoteUseCase(
                    folderId = folderId,
                    title = state.title,
                    content = state.content,
                    selectedDate = displayDate,
                    selectedTime = displayTime
                ).toInt()
            }



            if (
                state.selectedDateMillis != null &&
                state.selectedHour != null &&
                state.selectedMinute != null
            ) {

                val calendar = Calendar.getInstance().apply {
                    timeInMillis = state.selectedDateMillis
                    set(Calendar.HOUR_OF_DAY, state.selectedHour)
                    set(Calendar.MINUTE, state.selectedMinute)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                val triggerTimeMillis = calendar.timeInMillis

                val alarm = getAlarmByNoteIdUseCase(noteId)

                if (alarm == null) {


                    val alarmId = insertAlarmUseCase(
                        noteId = noteId,
                        triggerTimeMillis = triggerTimeMillis,
                        label = state.title
                    ).toInt()

                    alarmScheduler.schedule(
                        alarmId = alarmId,
                        triggerTimeMillis = triggerTimeMillis,
                        title = state.title,
                        message = state.content,
                        noteId = noteId
                    )

                } else {


                    alarmScheduler.cancel(alarm.id)

                    updateAlarmUseCase(
                        alarm.copy(
                            triggerTimeMillis = triggerTimeMillis,
                            label = state.title
                        )
                    )

                    alarmScheduler.schedule(
                        alarmId = alarm.id,
                        triggerTimeMillis = triggerTimeMillis,
                        title = state.title,
                        message = state.content,
                        noteId = noteId
                    )
                }
            }

            onSaved()
        }
    }

    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch {
            deleteNoteUseCase(note)
        }
    }

    fun addAlarm(
        noteId: Int,
        triggerTimeMillis: Long,
        label: String
    ) {
        viewModelScope.launch {
            insertAlarmUseCase(
                noteId = noteId,
                triggerTimeMillis = triggerTimeMillis,
                label = label
            )
        }
    }
}