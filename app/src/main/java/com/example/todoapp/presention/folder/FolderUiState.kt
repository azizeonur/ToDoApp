package com.example.todoapp.presention.folder

import com.example.todoapp.data.folder.FolderEntity

data class FolderUiState(
    val showDeleteDialog: Boolean = false,
    val folderToDelete: FolderEntity? = null
)