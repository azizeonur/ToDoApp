package com.example.todoapp.data.note

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val noteDao: NoteDao) : NoteRepository {



    override suspend fun getNoteByFolderId(folderId: Int): NoteEntity? {
        return noteDao.getNoteByFolderId(folderId)
    }

    override suspend fun insertNote(
        folderId: Int,
        title: String,
        content: String,
        selectedDate: String?,
        selectedTime: String?
    ): Long {
        return noteDao.insertNote(
            NoteEntity(
                folderId = folderId,
                title = title,
                content = content,
                selectedDate = selectedDate,
                selectedTime = selectedTime

            )
        )
    }

    override suspend fun updateNote(note: NoteEntity) {
        return noteDao.updateNote(note)
    }

    override suspend fun deleteNote(note: NoteEntity) {
        return noteDao.deleteNote(note)
    }

    override suspend fun getNoteById(noteId: Int): NoteEntity? {
        return noteDao.getNoteById(noteId)
    }

}