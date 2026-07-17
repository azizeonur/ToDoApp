package com.example.todoapp.domain.folderUseCase

import com.example.todoapp.data.folder.FolderRepository
import javax.inject.Inject

class GetFolderWithNotesUseCase @Inject constructor(
    private val repository: FolderRepository
) {
    operator fun invoke(folderId: Int) =
        repository.getFolderWithNotes(folderId)
}