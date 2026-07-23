package com.example.todoapp.domain.folderUseCase

import com.example.todoapp.data.folder.FolderRepository
import javax.inject.Inject

class GetFolderByIdUseCase @Inject constructor(
    private val repository: FolderRepository
) {
    suspend operator fun invoke(
        folderId: Int
    ) = repository.getFolderById(folderId)
}