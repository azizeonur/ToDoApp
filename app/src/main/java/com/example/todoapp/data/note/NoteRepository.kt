package com.example.todoapp.data.note

import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotesByFolderId(
        folderId: Int
    ): Flow<List<NoteEntity>>

    suspend fun insertNote(
        folderId: Int,
        title: String,
        content: String
    ): Long

    suspend fun updateNote(
        note: NoteEntity
    )

    suspend fun deleteNote(
        note: NoteEntity
    )
}