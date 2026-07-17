package com.example.todoapp.domain.alarmUseCase

import com.example.todoapp.data.alarm.AlarmEntity
import com.example.todoapp.data.alarm.AlarmRepository
import javax.inject.Inject

class DeleteAlarmUseCase @Inject constructor(private val repository: AlarmRepository
) {
    suspend operator fun invoke(alarm: AlarmEntity) =
        repository.deleteAlarm(alarm)
}