package com.example.todoapp.data.note

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.todoapp.data.database.NoteWithAlarms
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes WHERE folderId = :folderId LIMIT 1")
    suspend fun getNoteByFolderId(folderId: Int): NoteEntity?

    @Query("SELECT * FROM notes WHERE id = :noteId")
    suspend fun getNoteById(
        noteId: Int
    ): NoteEntity?

    @Transaction
    @Query("SELECT * FROM notes WHERE id = :noteId")
    fun getNoteWithAlarms(
        noteId: Int
    ): Flow<NoteWithAlarms>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(
        note: NoteEntity
    ): Long

    @Update
    suspend fun updateNote(
        note: NoteEntity
    )

    @Delete
    suspend fun deleteNote(
        note: NoteEntity
    )
}