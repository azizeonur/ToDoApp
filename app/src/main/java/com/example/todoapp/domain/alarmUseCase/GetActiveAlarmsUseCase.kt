package com.example.todoapp.domain.alarmUseCase

import com.example.todoapp.data.alarm.AlarmRepository
import javax.inject.Inject

class GetActiveAlarmsUseCase @Inject constructor(
    private val repository: AlarmRepository
) {
     operator fun invoke() =
        repository.getActiveAlarms()
}