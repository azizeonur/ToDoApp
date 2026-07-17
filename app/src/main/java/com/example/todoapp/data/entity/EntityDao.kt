package com.example.todoapp.data.entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface EntityDao {

    @Query("SELECT * FROM entities ORDER BY id ASC")
    fun getAllEntities(): Flow<List<EntityEntity>>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertEntity(entity: EntityEntity): Long

    @Update
    suspend fun updateEntity(entity: EntityEntity)

    @Delete
    suspend fun deleteEntity(entity: EntityEntity)

}