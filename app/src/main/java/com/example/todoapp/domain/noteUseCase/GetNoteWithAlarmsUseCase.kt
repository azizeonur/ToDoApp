package com.example.todoapp.domain.noteUseCase

import com.example.todoapp.data.note.NoteDao
import javax.inject.Inject

class GetNoteWithAlarmsUseCase @Inject constructor(private val noteDao: NoteDao
) {
     operator fun invoke(noteId: Int) =
         noteDao.getNoteWithAlarms(noteId)
}