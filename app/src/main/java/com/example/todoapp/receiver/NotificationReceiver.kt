package com.example.todoapp.receiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.postDelayed
import com.example.todoapp.MainActivity
import com.example.todoapp.R
import com.example.todoapp.data.database.RepeatType
import com.example.todoapp.domain.alarmUseCase.AlarmScheduler
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import javax.inject.Inject




@AndroidEntryPoint
class NotificationReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alarmScheduler: AlarmScheduler

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
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

        val alarmId =
            intent.getIntExtra("alarmId", -1)

        val songUrl =
            intent.getStringExtra("songUrl")

        val songName =
            intent.getStringExtra("songName")

        val triggerTimeMillis =
            intent.getLongExtra(
                "triggerTimeMillis",
                0L
            )

        val repeatType = runCatching {
            RepeatType.valueOf(
                intent.getStringExtra("repeatType")
                    ?: RepeatType.NONE.name
            )
        }.getOrDefault(
            RepeatType.NONE
        )

        val openIntent = Intent(
            context,
            MainActivity::class.java
        ).apply {
            putExtra("entityId", entityId)
            putExtra("folderId", folderId)
            putExtra("noteId", noteId)

            flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val openPendingIntent =
            PendingIntent.getActivity(
                context,
                alarmId,
                openIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE
            )

        val stopIntent = Intent(
            context,
            AlarmActionReceiver::class.java
        ).apply {
            action =
                AlarmActionReceiver.ACTION_STOP

            putExtra("alarmId", alarmId)
        }

        val stopPendingIntent =
            PendingIntent.getBroadcast(
                context,
                alarmId,
                stopIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE
            )

        val snoozeIntent = Intent(
            context,
            AlarmActionReceiver::class.java
        ).apply {
            action =
                AlarmActionReceiver.ACTION_SNOOZE

            putExtra("alarmId", alarmId)
            putExtra("title", title)
            putExtra("message", message)
            putExtra("entityId", entityId)
            putExtra("folderId", folderId)
            putExtra("noteId", noteId)
            putExtra("songUrl", songUrl)
            putExtra("songName", songName)
        }

        val snoozePendingIntent =
            PendingIntent.getBroadcast(
                context,
                alarmId + 1,
                snoozeIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE
            )

        val notificationText =
            if (songName.isNullOrBlank()) {
                message
            } else {
                "$message\nŞarkı: $songName"
            }

        val builder =
            NotificationCompat.Builder(
                context,
                "todo_channel"
            )
                .setSmallIcon(
                    R.drawable.ic_launcher_foreground
                )
                .setContentTitle(title)
                .setContentText(notificationText)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(notificationText)
                )
                .setPriority(
                    NotificationCompat.PRIORITY_HIGH
                )
                .setCategory(
                    NotificationCompat.CATEGORY_ALARM
                )
                .setAutoCancel(false)
                .setOngoing(true)
                .setContentIntent(openPendingIntent)
                .addAction(
                    0,
                    "Durdur",
                    stopPendingIntent
                )
                .addAction(
                    0,
                    "10 dk ertele",
                    snoozePendingIntent
                )

        if (
            ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        NotificationManagerCompat
            .from(context)
            .notify(
                alarmId,
                builder.build()
            )

        if (!songUrl.isNullOrBlank()) {
            AlarmSoundPlayer.play(
                songUrl = songUrl,
                durationMillis = 30_000L
            )
        }

        scheduleNextRepeat(
            alarmId = alarmId,
            triggerTimeMillis = triggerTimeMillis,
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

    private fun scheduleNextRepeat(
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
        if (repeatType == RepeatType.NONE) {
            return
        }

        val calendar =
            Calendar.getInstance().apply {
                timeInMillis = triggerTimeMillis
            }

        when (repeatType) {
            RepeatType.DAILY -> {
                calendar.add(
                    Calendar.DAY_OF_YEAR,
                    1
                )
            }

            RepeatType.WEEKLY -> {
                calendar.add(
                    Calendar.WEEK_OF_YEAR,
                    1
                )
            }

            RepeatType.MONTHLY -> {
                calendar.add(
                    Calendar.MONTH,
                    1
                )
            }

            RepeatType.NONE -> Unit

            else -> {}
        }

        alarmScheduler.schedule(
            alarmId = alarmId,
            triggerTimeMillis =
                calendar.timeInMillis,
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