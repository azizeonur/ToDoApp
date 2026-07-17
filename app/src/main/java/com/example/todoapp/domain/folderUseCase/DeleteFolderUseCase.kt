package com.example.todoapp.domain.folderUseCase

import com.example.todoapp.data.folder.FolderEntity
import com.example.todoapp.data.folder.FolderRepository
import javax.inject.Inject

class DeleteFolderUseCase @Inject constructor(private val repository: FolderRepository
) {
    suspend operator fun invoke(folder: FolderEntity) {
        repository.deleteFolder(folder)
    }
}