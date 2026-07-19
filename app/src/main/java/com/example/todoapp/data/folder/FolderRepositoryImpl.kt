package com.example.todoapp.data.folder

import com.example.todoapp.data.database.FolderWithNotes
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FolderRepositoryImpl @Inject constructor(
    private val folderDao: FolderDao
) : FolderRepository {

    override fun getFoldersByEntityId(
        entityId: Int
    ): Flow<List<FolderEntity>> {
        return folderDao.getFoldersByEntityId(entityId)
    }

    override fun getFoldersWithNotesByEntityId(
        entityId: Int
    ): Flow<List<FolderWithNotes>> {
        return folderDao.getFoldersWithNotesByEntityId(entityId)
    }

    override suspend fun insertFolder(
        entityId: Int,
        title: String,
        description: String
    ): Long {
        return folderDao.insertFolder(
            FolderEntity(
                entityId = entityId,
                title = title,
                description = description
            )
        )
    }

    override suspend fun updateFolder(folder: FolderEntity) {
        folderDao.updateFolder(folder)
    }

    override suspend fun deleteFolder(folder: FolderEntity) {
        folderDao.deleteFolder(folder)
    }
}