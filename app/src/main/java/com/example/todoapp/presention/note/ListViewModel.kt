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

@HiltViewModel
class ListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val insertFolderUseCase: InsertFolderUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val saveAlarmUseCase: SaveAlarmUseCase,
    private val getAlarmByNoteIdUseCase: GetAlarmByNoteIdUseCase,
    private val getNotesByFolderIdUseCase: GetNotesByFolderIdUseCase,
) : ViewModel() {

    private val folderId: Int? =
        savedStateHandle["folderId"]

    private val entityId: Int? =
        savedStateHandle["entityId"]

    private val _uiState = MutableStateFlow(NoteUiState())
    val uiState = _uiState.asStateFlow()

    private val _note = MutableStateFlow<NoteEntity?>(null)
    val note = _note.asStateFlow()

    init {
        loadNote()
    }

    private fun loadNote() {
        val id = folderId ?: return

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

                val alarm = getAlarmByNoteIdUseCase(it.id)

                val timeParts = it.selectedTime?.split(":")

                val hour = timeParts
                    ?.getOrNull(0)
                    ?.toIntOrNull()

                val minute = timeParts
                    ?.getOrNull(1)
                    ?.toIntOrNull()

                _uiState.value = _uiState.value.copy(
                    title = it.title,
                    content = it.content,
                    selectedDateMillis = millis,
                    selectedHour = hour,
                    selectedMinute = minute,
                    repeatType = alarm?.repeatType ?: RepeatType.NONE
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
        _uiState.value = _uiState.value.copy(
            selectedHour = hour,
            selectedMinute = minute
        )
    }

    fun setSong(uri: String?) {
        _uiState.value = _uiState.value.copy(
            selectedSongUri = uri
        )
    }
    fun isEditMode(): Boolean = note.value != null

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

            val (noteId, currentFolderId) = saveOrUpdateNote(state)

            saveAlarmUseCase(
                noteId = noteId,
                folderId = currentFolderId,
                title = state.title,
                message = state.content,
                selectedDateMillis = state.selectedDateMillis,
                hour = state.selectedHour,
                minute = state.selectedMinute,
                repeatType = state.repeatType
            )

            onSaved()
        }
    }

    private suspend fun saveOrUpdateNote(
        state: NoteUiState
    ): Pair<Int, Int> {

        val selectedDate = state.selectedDateMillis.toFormattedDate()
        val selectedTime = state.selectedHour.toFormattedTime(
            state.selectedMinute
        )

        return if (isEditMode()) {

            val currentNote = note.value!!

            updateNoteUseCase(
                currentNote.copy(
                    title = state.title,
                    content = state.content,
                    selectedDate = selectedDate,
                    selectedTime = selectedTime
                )
            )

            Pair(currentNote.id, currentNote.folderId)

        } else {

            val currentFolderId = folderId ?: insertFolderUseCase(
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

            Pair(noteId, currentFolderId)
        }
    }

    private fun NoteUiState.canSave(): Boolean {
        return title.isNotBlank() ||
                content.isNotBlank() ||
                selectedDateMillis != null ||
                selectedHour != null ||
                selectedMinute != null ||
                selectedSongUri != null ||
                repeatType != RepeatType.NONE
    }

    fun setRepeatType(
        repeatType: RepeatType
    ) {
        _uiState.value = _uiState.value.copy(
            repeatType = repeatType
        )
    }
}