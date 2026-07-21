package com.example.todoapp.data.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
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
        noteId: Int
    ) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                return
            }
        }

        Log.d(
            "AlarmTest",
            "NOW = ${
                java.text.SimpleDateFormat(
                    "dd.MM.yyyy HH:mm:ss",
                    java.util.Locale.getDefault()
                ).format(java.util.Date())
            }"
        )

        Log.d(
            "AlarmTest",
            "TRIGGER = ${
                java.text.SimpleDateFormat(
                    "dd.MM.yyyy HH:mm:ss",
                    java.util.Locale.getDefault()
                ).format(java.util.Date(triggerTimeMillis))
            }"
        )

        Log.d(
            "AlarmTest",
            "FARK = ${(triggerTimeMillis - System.currentTimeMillis()) / 1000} saniye"
        )

        val intent = Intent(
            context,
            NotificationReceiver::class.java
        ).apply {
            putExtra("noteId", noteId)
            putExtra("title", title)
            putExtra("message", message)
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

        Log.d("AlarmTest", "AlarmManager.setExactAndAllowWhileIdle çağrıldı")
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