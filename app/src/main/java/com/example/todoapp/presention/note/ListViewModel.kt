package com.example.todoapp.presention.note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.database.RepeatType
import com.example.todoapp.data.note.NoteEntity
import com.example.todoapp.domain.alarmUseCase.GetAlarmByNoteIdUseCase
import com.example.todoapp.domain.folderUseCase.InsertFolderUseCase
import com.example.todoapp.domain.noteUseCase.GetNotesByFolderIdUseCase
import com.example.todoapp.domain.noteUseCase.InsertNoteUseCase
import com.example.todoapp.domain.noteUseCase.SaveAlarmUseCase
import com.example.todoapp.domain.noteUseCase.UpdateNoteUseCase
import com.example.todoapp.presention.extension.toFormattedDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject
import com.example.todoapp.presention.extension.toFormattedTime
import kotlinx.coroutines.flow.update

@HiltViewModel
class ListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val insertFolderUseCase: InsertFolderUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val saveAlarmUseCase: SaveAlarmUseCase,
    private val getAlarmByNoteIdUseCase: GetAlarmByNoteIdUseCase,
    private val getNotesByFolderIdUseCase: GetNotesByFolderIdUseCase
) : ViewModel() {

    private val folderId: Int? =
        savedStateHandle["folderId"]

    private val entityId: Int? =
        savedStateHandle["entityId"]

    private val _uiState =
        MutableStateFlow(NoteUiState())

    val uiState = _uiState.asStateFlow()

    private val _note =
        MutableStateFlow<NoteEntity?>(null)

    val note = _note.asStateFlow()

    init {
        loadNote()
    }

    private fun loadNote() {
        val currentFolderId = folderId ?: return

        viewModelScope.launch {
            val currentNote =
                getNotesByFolderIdUseCase(currentFolderId)

            _note.value = currentNote

            currentNote?.let { note ->

                val selectedDateMillis =
                    note.selectedDate?.let { date ->
                        SimpleDateFormat(
                            "dd MMM yyyy",
                            Locale.getDefault()
                        ).parse(date)?.time
                    }

                val alarm =
                    getAlarmByNoteIdUseCase(note.id)

                val timeParts =
                    note.selectedTime?.split(":")

                val selectedHour =
                    timeParts
                        ?.getOrNull(0)
                        ?.toIntOrNull()

                val selectedMinute =
                    timeParts
                        ?.getOrNull(1)
                        ?.toIntOrNull()

                _uiState.update { currentState ->
                    currentState.copy(
                        title = note.title,
                        content = note.content,
                        selectedDateMillis = selectedDateMillis,
                        selectedHour = selectedHour,
                        selectedMinute = selectedMinute,
                        selectedSongUri = alarm?.songUrl,
                        selectedSongName = alarm?.songName,
                        repeatType = alarm?.repeatType
                            ?: RepeatType.NONE
                    )
                }
            }
        }
    }

    fun onTitleChange(title: String) {
        _uiState.update {
            it.copy(title = title)
        }
    }

    fun onContentChange(content: String) {
        _uiState.update {
            it.copy(content = content)
        }
    }

    fun showDatePicker(show: Boolean) {
        _uiState.update {
            it.copy(showDatePicker = show)
        }
    }

    fun showTimePicker(show: Boolean) {
        _uiState.update {
            it.copy(showTimePicker = show)
        }
    }

    fun setDate(date: Long?) {
        _uiState.update {
            it.copy(
                selectedDateMillis = date
            )
        }
    }

    fun setTime(
        hour: Int,
        minute: Int
    ) {
        _uiState.update {
            it.copy(
                selectedHour = hour,
                selectedMinute = minute
            )
        }
    }

    fun setSong(
        songName: String,
        songUrl: String
    ) {
        _uiState.update {
            it.copy(
                selectedSongName = songName,
                selectedSongUri = songUrl
            )
        }
    }

    fun setRepeatType(
        repeatType: RepeatType
    ) {
        _uiState.update {
            it.copy(
                repeatType = repeatType
            )
        }
    }

    private fun isEditMode(): Boolean {
        return note.value != null
    }

    fun saveNote(
        onSaved: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            val state = uiState.value

            if (!state.canSave()) {
                onError()
                return@launch
            }

            val (noteId, currentFolderId) =
                saveOrUpdateNote(state)

            saveAlarmUseCase(
                noteId = noteId,
                folderId = currentFolderId,
                title = state.title,
                message = state.content,
                selectedDateMillis =
                    state.selectedDateMillis,
                hour = state.selectedHour,
                minute = state.selectedMinute,
                repeatType = state.repeatType,
                songUrl = state.selectedSongUri,
                songName = state.selectedSongName
            )

            onSaved()
        }
    }

    private suspend fun saveOrUpdateNote(
        state: NoteUiState
    ): Pair<Int, Int> {

        val selectedDate =
            state.selectedDateMillis.toFormattedDate()

        val selectedTime =
            state.selectedHour.toFormattedTime(
                state.selectedMinute
            )

        return if (isEditMode()) {
            val currentNote = checkNotNull(note.value)

            updateNoteUseCase(
                currentNote.copy(
                    title = state.title,
                    content = state.content,
                    selectedDate = selectedDate,
                    selectedTime = selectedTime
                )
            )

            Pair(
                currentNote.id,
                currentNote.folderId
            )
        } else {
            val currentFolderId =
                folderId ?: insertFolderUseCase(
                    entityId = checkNotNull(entityId),
                    title = "",
                    description = ""
                ).toInt()

            val noteId = insertNoteUseCase(
                folderId = currentFolderId,
                title = state.title,
                content = state.content,
                selectedDate = selectedDate,
                selectedTime = selectedTime
            ).toInt()

            Pair(
                noteId,
                currentFolderId
            )
        }
    }

    private fun NoteUiState.canSave(): Boolean {
        return title.isNotBlank() &&
                content.isNotBlank() &&
                selectedDateMillis != null &&
                selectedHour != null &&
                selectedMinute != null &&
                selectedSongName != null &&
                repeatType != RepeatType.NONE
    }
}