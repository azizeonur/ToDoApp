package com.example.todoapp.domain.folderUseCase

import com.example.todoapp.data.folder.FolderRepository
import javax.inject.Inject

class GetFoldersByEntityIdUseCase @Inject constructor(
    private val repository: FolderRepository
) {
    operator fun invoke(entityId: Int) =
        repository.getFoldersByEntityId(entityId)

}