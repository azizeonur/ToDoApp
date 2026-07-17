package com.example.todoapp.data.note

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val noteDao: NoteDao) : NoteRepository {


    override fun getNotesByFolderId(folderId: Int): Flow<List<NoteEntity>> {
        return noteDao.getNotesByFolderId(folderId)
    }

    override suspend fun insertNote(
        folderId: Int,
        title: String,
        content: String
    ): Long {
        return noteDao.insertNote(
            NoteEntity(
                folderId = folderId,
                title = title,
                content = content
            )
        )
    }

    override suspend fun updateNote(note: NoteEntity) {
        return noteDao.updateNote(note)
    }

    override suspend fun deleteNote(note: NoteEntity) {
        return noteDao.deleteNote(note)
    }

}