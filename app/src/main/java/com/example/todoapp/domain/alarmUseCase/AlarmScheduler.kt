package com.example.todoapp.domain.alarmUseCase

import com.example.todoapp.data.database.RepeatType

interface AlarmScheduler {

    fun schedule(
        alarmId: Int,
        triggerTimeMillis: Long,
        title: String,
        message: String,
        noteId: Int,
        folderId: Int,
        entityId: Int,
        repeatType: RepeatType
    )

    fun cancel(
        alarmId: Int
    )
}