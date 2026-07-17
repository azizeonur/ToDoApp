package com.example.todoapp.domain.noteUseCase

import com.example.todoapp.data.note.NoteEntity
import com.example.todoapp.data.note.NoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(private val repository: NoteRepository) {
    suspend operator fun invoke(note: NoteEntity) {
        repository.deleteNote(note)
    }
}