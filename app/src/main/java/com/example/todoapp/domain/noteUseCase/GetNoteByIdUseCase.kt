package com.example.todoapp.domain.noteUseCase

import com.example.todoapp.data.note.NoteRepository
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(private val noteRepository: NoteRepository
){
    suspend operator fun invoke(noteId: Int) =
        noteRepository.getNoteById(noteId)

}