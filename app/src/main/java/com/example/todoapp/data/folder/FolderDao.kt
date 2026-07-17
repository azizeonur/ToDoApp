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
    @Query("SELECT * FROM folders WHERE id = :folderId")
    fun getFolderWithNotes(folderId: Int): Flow<FolderWithNotes>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertFolder(folder: FolderEntity): Long

    @Update
    suspend fun updateFolder(folder: FolderEntity)

    @Delete
    suspend fun deleteFolder(folder: FolderEntity)
}