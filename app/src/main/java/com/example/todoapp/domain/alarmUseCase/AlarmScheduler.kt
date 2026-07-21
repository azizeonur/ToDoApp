package com.example.todoapp.domain.alarmUseCase

interface AlarmScheduler {

    fun schedule(
        alarmId: Int,
        triggerTimeMillis: Long,
        title: String,
        message: String,
        noteId: Int
    )

    fun cancel(
        alarmId: Int
    )
}