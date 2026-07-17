package com.example.todoapp.domain.folderUseCase

import com.example.todoapp.data.folder.FolderEntity
import com.example.todoapp.data.folder.FolderRepository
import javax.inject.Inject

class UpdateFolderUseCase @Inject constructor(private val repository: FolderRepository
) {
    suspend operator fun invoke(folderId: FolderEntity) {
    repository.updateFolder(folderId)
    }
}