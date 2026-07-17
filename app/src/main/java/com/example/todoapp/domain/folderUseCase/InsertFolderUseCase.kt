package com.example.todoapp.domain.folderUseCase

import com.example.todoapp.data.folder.FolderRepository
import javax.inject.Inject

class InsertFolderUseCase @Inject constructor(
    private val repository: FolderRepository
) {
   suspend operator fun invoke(entityId: Int, title: String, description: String) =
        repository.insertFolder(entityId, title, description)
}