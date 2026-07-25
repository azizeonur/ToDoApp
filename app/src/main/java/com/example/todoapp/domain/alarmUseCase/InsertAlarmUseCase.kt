package com.example.todoapp.domain.alarmUseCase

import com.example.todoapp.data.alarm.AlarmRepository
import com.example.todoapp.data.database.RepeatType
import javax.inject.Inject

class InsertAlarmUseCase @Inject constructor(
    private val repository: AlarmRepository
) {
    suspend operator fun invoke(
        noteId: Int,
        triggerTimeMillis: Long,
        label: String,
        repeatType: RepeatType,
        songUrl: String?,
        songName: String?
    ) =
        repository.insertAlarm(
            noteId,
            triggerTimeMillis,
            label,
            repeatType,
            songUrl,
            songName
        )
}