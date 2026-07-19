package com.example.todoapp.data.note

import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun getNoteByFolderId(folderId: Int): NoteEntity?

    suspend fun insertNote(
        folderId: Int,
        title: String,
        content: String,
        selectedDate: String?,
        selectedTime: String?
    ): Long

    suspend fun updateNote(
        note: NoteEntity
    )

    suspend fun deleteNote(
        note: NoteEntity
    )
    suspend fun getNoteById(
        noteId: Int
    ): NoteEntity?
}