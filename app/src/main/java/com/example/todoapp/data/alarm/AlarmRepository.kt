package com.example.todoapp.data.alarm


import com.example.todoapp.data.database.RepeatType
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {

    fun getAlarmsByNoteId(
        noteId: Int
    ): Flow<List<AlarmEntity>>

    fun getActiveAlarms(): Flow<List<AlarmEntity>>

    suspend fun insertAlarm(
        noteId: Int,
        triggerTimeMillis: Long,
        label: String,
        repeatType: RepeatType

    ): Long
suspend fun getAlarmByNoteId
        (noteId: Int): AlarmEntity?

    suspend fun updateAlarm(
        alarm: AlarmEntity
    )

    suspend fun deleteAlarm(
        alarm: AlarmEntity
    )
}