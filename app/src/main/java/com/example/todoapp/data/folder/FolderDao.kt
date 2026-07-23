package com.example.todoapp.data.folder

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.todoapp.data.database.FolderWithNotes
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderDao {

    @Query(
        "SELECT * FROM folders WHERE entityId = :entityId ORDER BY createdAt DESC"
    )
    fun getFoldersByEntityId(entityId: Int): Flow<List<FolderEntity>>

    @Transaction
    @Query(
        "SELECT * FROM folders WHERE entityId = :entityId ORDER BY createdAt DESC"
    )
    fun getFoldersWithNotesByEntityId(
        entityId: Int
    ): Flow<List<FolderWithNotes>>

    @Query("SELECT * FROM folders WHERE id = :folderId")
    suspend fun getFolderById(
        folderId: Int
    ): FolderEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFolder(folder: FolderEntity): Long

    @Update
    suspend fun updateFolder(folder: FolderEntity)

    @Delete
    suspend fun deleteFolder(folder: FolderEntity)
}