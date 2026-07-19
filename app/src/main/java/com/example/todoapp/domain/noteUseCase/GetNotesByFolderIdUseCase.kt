package com.example.todoapp.domain.noteUseCase

import com.example.todoapp.data.note.NoteRepository
import javax.inject.Inject

class GetNotesByFolderIdUseCase @Inject constructor(private val repository: NoteRepository
) {
    suspend operator fun invoke(folderId: Int) =
        repository.getNoteByFolderId(folderId)
}