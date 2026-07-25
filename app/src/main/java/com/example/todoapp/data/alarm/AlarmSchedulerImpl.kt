package com.example.todoapp.data.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.todoapp.data.database.RepeatType
import com.example.todoapp.domain.alarmUseCase.AlarmScheduler
import com.example.todoapp.receiver.NotificationReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AlarmSchedulerImpl @Inject constructor(
    @param:ApplicationContext
    private val context: Context
) : AlarmScheduler {

    private val alarmManager =
        context.getSystemService(AlarmManager::class.java)

    override fun schedule(
        alarmId: Int,
        triggerTimeMillis: Long,
        title: String,
        message: String,
        noteId: Int,
        folderId: Int,
        entityId: Int,
        repeatType: RepeatType,
        songUrl: String?,
        songName: String?
    ) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                return
            }
        }

        val intent = Intent(
            context,
            NotificationReceiver::class.java
        ).apply {
            putExtra("entityId", entityId)
            putExtra("title", title)
            putExtra("message", message)
            putExtra("folderId", folderId)
            putExtra("alarmId", alarmId)
            putExtra("triggerTimeMillis", triggerTimeMillis)
            putExtra("repeatType", repeatType.name)
            putExtra("noteId", noteId)
            putExtra("songUrl", songUrl)
            putExtra("songName", songName)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarmId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerTimeMillis,
            pendingIntent
        )
    }

    override fun cancel(alarmId: Int) {

        val intent = Intent(
            context,
            NotificationReceiver::class.java
        )

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarmId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel(pendingIntent)
    }
}