package com.example.todoapp.domain.noteUseCase

import com.example.todoapp.data.note.NoteRepository
import javax.inject.Inject

class InsertNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(
        folderId: Int,
        title: String,
        content: String,
        selectedDate: String?,
        selectedTime: String?
    ) = repository.insertNote(
        folderId = folderId,
        title = title,
        content = content,
        selectedDate = selectedDate,
        selectedTime = selectedTime

    )

}