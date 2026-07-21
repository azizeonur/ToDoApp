package com.example.todoapp.data.alarm


import kotlinx.coroutines.flow.Flow

interface AlarmRepository {

    fun getAlarmsByNoteId(
        noteId: Int
    ): Flow<List<AlarmEntity>>

    fun getActiveAlarms(): Flow<List<AlarmEntity>>

    suspend fun insertAlarm(
        noteId: Int,
        triggerTimeMillis: Long,
        label: String
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