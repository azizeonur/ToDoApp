package com.example.todoapp.presention.note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.note.NoteEntity
import com.example.todoapp.domain.alarmUseCase.InsertAlarmUseCase
import com.example.todoapp.domain.noteUseCase.DeleteNoteUseCase
import com.example.todoapp.domain.noteUseCase.GetNotesByFolderIdUseCase
import com.example.todoapp.domain.noteUseCase.InsertNoteUseCase
import com.example.todoapp.domain.noteUseCase.UpdateNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getNotesByFolderIdUseCase: GetNotesByFolderIdUseCase,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val insertAlarmUseCase: InsertAlarmUseCase
) : ViewModel() {

    private val folderId: Int =
        checkNotNull(savedStateHandle["folderId"])

    val notes: StateFlow<List<NoteEntity>> =
        getNotesByFolderIdUseCase(folderId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    fun insertNote(
        title: String,
        content: String,
        onSaved: ((Int) -> Unit)? = null
    ) {
        viewModelScope.launch {
            val newId = insertNoteUseCase(
                folderId = folderId,
                title = title,
                content = content
            )

            onSaved?.invoke(newId.toInt())
        }
    }

    fun updateNote(
        note: NoteEntity
    ) {
        viewModelScope.launch {
            updateNoteUseCase(note)
        }
    }

    fun deleteNote(
        note: NoteEntity
    ) {
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