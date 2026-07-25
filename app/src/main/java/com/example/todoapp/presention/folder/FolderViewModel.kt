package com.example.todoapp.presention.folder


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.folder.FolderEntity
import com.example.todoapp.domain.folderUseCase.DeleteFolderUseCase
import com.example.todoapp.domain.folderUseCase.GetFoldersByEntityIdUseCase
import com.example.todoapp.domain.folderUseCase.InsertFolderUseCase
import com.example.todoapp.domain.folderUseCase.UpdateFolderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.todoapp.data.database.FolderWithNotes
import com.example.todoapp.data.note.NoteEntity
import com.example.todoapp.domain.noteUseCase.UpdateNoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FolderViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val insertFolderUseCase: InsertFolderUseCase,
    private val updateFolderUseCase: UpdateFolderUseCase,
    private val deleteFolderUseCase: DeleteFolderUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val getFoldersByEntityIdUseCase: GetFoldersByEntityIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FolderUiState())
    val uiState: StateFlow<FolderUiState> = _uiState.asStateFlow()

    private val entityId: Int =
        checkNotNull(savedStateHandle["entityId"])

    val folders: StateFlow<List<FolderWithNotes>> =
        getFoldersByEntityIdUseCase(entityId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    fun toggleCompleted(
        note: NoteEntity,
        checked: Boolean
    ) {
        viewModelScope.launch {
            updateNoteUseCase(
                note.copy(
                    isCompleted = checked
                )
            )
        }
    }

    fun insertFolder(
        title: String,
        description: String,
        onInserted: (Int) -> Unit,
        onError: () -> Unit
    ) {
        if (title.isBlank() && description.isBlank()) {
            onError()
            return
        }

        viewModelScope.launch {

            val folderId = insertFolderUseCase(
                entityId = entityId,
                title = title,
                description = description
            )

            onInserted(folderId.toInt())
        }
    }
    fun showDeleteDialog(folder: FolderEntity?) {
        _uiState.value = _uiState.value.copy(
            showDeleteDialog = folder != null,
            folderToDelete = folder
        )
    }

    fun updateFolder(folder: FolderEntity) {
        viewModelScope.launch {
            updateFolderUseCase(folder)
        }
    }

    fun deleteFolder(folder: FolderEntity) {
        viewModelScope.launch {
            deleteFolderUseCase(folder)
        }
    }
}