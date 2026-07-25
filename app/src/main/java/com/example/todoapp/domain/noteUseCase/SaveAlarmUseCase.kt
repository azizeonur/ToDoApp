package com.example.todoapp.domain.noteUseCase

import com.example.todoapp.data.database.RepeatType
import com.example.todoapp.domain.alarmUseCase.AlarmScheduler
import com.example.todoapp.domain.alarmUseCase.GetAlarmByNoteIdUseCase
import com.example.todoapp.domain.alarmUseCase.InsertAlarmUseCase
import com.example.todoapp.domain.alarmUseCase.UpdateAlarmUseCase
import com.example.todoapp.domain.folderUseCase.GetFolderByIdUseCase
import javax.inject.Inject


class SaveAlarmUseCase @Inject constructor(
    private val calculateTriggerTimeUseCase: CalculateTriggerTimeUseCase,
    private val getAlarmByNoteIdUseCase: GetAlarmByNoteIdUseCase,
    private val insertAlarmUseCase: InsertAlarmUseCase,
    private val updateAlarmUseCase: UpdateAlarmUseCase,
    private val getFolderByIdUseCase: GetFolderByIdUseCase,
    private val alarmScheduler: AlarmScheduler
) {

    suspend operator fun invoke(
        noteId: Int,
        folderId: Int,
        title: String,
        message: String,
        selectedDateMillis: Long?,
        hour: Int?,
        minute: Int?,
        repeatType: RepeatType,
        songUrl: String?,
        songName: String?
    ) {

        val triggerTime = calculateTriggerTimeUseCase(
            selectedDateMillis = selectedDateMillis,
            hour = hour,
            minute = minute,
            repeatType = repeatType
        ) ?: return

        val entityId =
            getFolderByIdUseCase(folderId)?.entityId ?: return

        val alarm = getAlarmByNoteIdUseCase(noteId)

        val alarmId = if (alarm == null) {

            insertAlarmUseCase(
                noteId = noteId,
                triggerTimeMillis = triggerTime,
                label = title,
                repeatType = repeatType,
                songUrl = songUrl,
                songName = songName
            ).toInt()

        } else {

            alarmScheduler.cancel(alarm.id)

            updateAlarmUseCase(
                alarm.copy(
                    triggerTimeMillis = triggerTime,
                    label = title,
                    repeatType = repeatType,
                    songUrl = songUrl,
                    songName = songName
                )
            )

            alarm.id
        }

        alarmScheduler.schedule(
            triggerTimeMillis = triggerTime,
            alarmId = alarmId,
            title = title,
            message = message,
            noteId = noteId,
            folderId = folderId,
            entityId = entityId,
            repeatType = repeatType,
            songUrl = songUrl,
            songName = songName
        )
    }
}