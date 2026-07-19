package com.example.todoapp.data.folder

import com.example.todoapp.data.database.FolderWithNotes
import kotlinx.coroutines.flow.Flow

interface FolderRepository {

    fun getFoldersByEntityId(
        entityId: Int
    ): Flow<List<FolderEntity>>

    suspend fun insertFolder(
        entityId: Int,
        title: String,
        description: String
    ): Long

    suspend fun updateFolder(folder: FolderEntity)

    suspend fun deleteFolder(folder: FolderEntity)

    fun getFoldersWithNotesByEntityId(
        entityId: Int
    ): Flow<List<FolderWithNotes>>
}
