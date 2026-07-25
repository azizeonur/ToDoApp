package com.example.todoapp.data.alarm

import com.example.todoapp.data.database.RepeatType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(private val alarmDao: AlarmDao) :
    AlarmRepository {

    override fun getAlarmsByNoteId(noteId: Int): Flow<List<AlarmEntity>> {
        return alarmDao.getAlarmsByNoteId(noteId)
    }

    override fun getActiveAlarms(): Flow<List<AlarmEntity>> {
        return alarmDao.getActiveAlarms()
    }


    override suspend fun insertAlarm(
        noteId: Int,
        triggerTimeMillis: Long,
        label: String,
        repeatType: RepeatType,
        songUrl: String?,
        songName: String?

    ): Long {
        return alarmDao.insertAlarm(
            AlarmEntity(
                noteId = noteId,
                triggerTimeMillis = triggerTimeMillis,
                label = label,
                repeatType = repeatType,
                songUrl = songUrl,
                songName = songName

            )
        )
    }

    override suspend fun getAlarmByNoteId(noteId: Int): AlarmEntity? {
        return alarmDao.getAlarmByNoteId(noteId)
    }

    override suspend fun updateAlarm(alarm: AlarmEntity) {
        return alarmDao.updateAlarm(alarm)
    }

    override suspend fun deleteAlarm(alarm: AlarmEntity) {
        return alarmDao.deleteAlarm(alarm)
    }
}