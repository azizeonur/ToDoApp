package com.example.todoapp.data.folder

import com.example.todoapp.data.database.FolderWithNotes
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FolderRepositoryImpl @Inject constructor(private val folderDao: FolderDao) :
    FolderRepository {

    override fun getFoldersByEntityId(entityId: Int): Flow<List<FolderEntity>> {
        return folderDao.getFoldersByEntityId(entityId)
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
        return folderDao.updateFolder(folder)
    }

    override suspend fun deleteFolder(folder: FolderEntity) {
        return folderDao.deleteFolder(folder)
    }

    override fun getFolderWithNotes(folderId: Int): Flow<FolderWithNotes> {
        return folderDao.getFolderWithNotes(folderId = folderId)
    }
}