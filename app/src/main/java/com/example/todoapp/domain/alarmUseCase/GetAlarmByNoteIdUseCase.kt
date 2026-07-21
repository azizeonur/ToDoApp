package com.example.todoapp.domain.alarmUseCase

import com.example.todoapp.data.alarm.AlarmRepository
import javax.inject.Inject

class GetAlarmByNoteIdUseCase @Inject constructor(private val repository: AlarmRepository
) {
    suspend operator fun invoke(noteId: Int) =
        repository.getAlarmByNoteId(noteId)

}