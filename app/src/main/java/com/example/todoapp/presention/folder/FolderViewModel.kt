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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FolderViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getFoldersByEntityIdUseCase: GetFoldersByEntityIdUseCase,
    private val insertFolderUseCase: InsertFolderUseCase,
    private val updateFolderUseCase: UpdateFolderUseCase,
    private val deleteFolderUseCase: DeleteFolderUseCase
) : ViewModel() {

    private val entityId: Int =
        checkNotNull(savedStateHandle["entityId"])

    val folders: StateFlow<List<FolderEntity>> =
        getFoldersByEntityIdUseCase(entityId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    fun insertFolder(
        title: String,
        description: String
    ) {
        viewModelScope.launch {
            insertFolderUseCase(
                entityId = entityId,
                title = title,
                description = description
            )
        }
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