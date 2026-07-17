package com.example.todoapp.data.alarm

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {

    @Query("SELECT * FROM alarms WHERE noteId = :noteId")
    fun getAlarmsByNoteId(
        noteId: Int
    ): Flow<List<AlarmEntity>>

    @Query("SELECT * FROM alarms WHERE isActive = 1 ORDER BY triggerTimeMillis ASC")
    fun getActiveAlarms(): Flow<List<AlarmEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(
        alarm: AlarmEntity
    ): Long

    @Update
    suspend fun updateAlarm(
        alarm: AlarmEntity
    )

    @Delete
    suspend fun deleteAlarm(
        alarm: AlarmEntity
    )
}