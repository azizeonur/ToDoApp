package com.example.todoapp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import com.example.todoapp.data.database.RepeatType
import com.example.todoapp.domain.alarmUseCase.AlarmScheduler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmActionReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alarmScheduler: AlarmScheduler

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        val alarmId =
            intent.getIntExtra("alarmId", -1)

        when (intent.action) {

            ACTION_STOP -> {
                AlarmSoundPlayer.stop()

                NotificationManagerCompat
                    .from(context)
                    .cancel(alarmId)
            }

            ACTION_SNOOZE -> {
                AlarmSoundPlayer.stop()

                val title =
                    intent.getStringExtra("title")
                        ?: "Hatırlatma"

                val message =
                    intent.getStringExtra("message")
                        ?: "Yapılacak görevin var."

                val entityId =
                    intent.getIntExtra("entityId", -1)

                val folderId =
                    intent.getIntExtra("folderId", -1)

                val noteId =
                    intent.getIntExtra("noteId", -1)

                val songUrl =
                    intent.getStringExtra("songUrl")

                val songName =
                    intent.getStringExtra("songName")

                val snoozeTime =
                    System.currentTimeMillis() +
                            SNOOZE_DURATION_MILLIS

                alarmScheduler.schedule(
                    alarmId = alarmId + 100_000,
                    triggerTimeMillis = snoozeTime,
                    title = title,
                    message = message,
                    noteId = noteId,
                    folderId = folderId,
                    entityId = entityId,
                    repeatType = RepeatType.NONE,
                    songUrl = songUrl,
                    songName = songName
                )

                NotificationManagerCompat
                    .from(context)
                    .cancel(alarmId)
            }
        }
    }

    companion object {

        const val ACTION_STOP =
            "com.example.todoapp.ACTION_STOP"

        const val ACTION_SNOOZE =
            "com.example.todoapp.ACTION_SNOOZE"

        private const val SNOOZE_DURATION_MILLIS =
            10 * 60 * 1000L
    }
}