package com.example.todoapp.domain.alarmUseCase

import com.example.todoapp.data.alarm.AlarmRepository
import javax.inject.Inject

class InsertAlarmUseCase @Inject constructor(
    private val repository: AlarmRepository
) {
    suspend operator fun invoke(noteId: Int, triggerTimeMillis: Long, label: String) =
        repository.insertAlarm(
            noteId,
            triggerTimeMillis,
            label
        )
}