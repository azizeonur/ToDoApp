package com.example.todoapp.receiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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
            intent.getStringExtra("title") ?: "Hatırlatma"

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

        val triggerTimeMillis =
            intent.getLongExtra("triggerTimeMillis", 0L)

        val repeatType = runCatching {
            RepeatType.valueOf(
                intent.getStringExtra("repeatType")
                    ?: RepeatType.NONE.name
            )
        }.getOrDefault(RepeatType.NONE)

        val openIntent = Intent(
            context,
            MainActivity::class.java
        ).apply {

            putExtra("entityId", entityId)
            putExtra("folderId", folderId)

            flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            folderId,
            openIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(
            context,
            "todo_channel"
        )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        if (
            ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        NotificationManagerCompat.from(context)
            .notify(alarmId, builder.build())

        if (repeatType != RepeatType.NONE) {

            val calendar = Calendar.getInstance().apply {
                timeInMillis = triggerTimeMillis
            }

            when (repeatType) {

                RepeatType.DAILY ->
                    calendar.add(
                        Calendar.DAY_OF_YEAR,
                        1
                    )

                RepeatType.WEEKLY ->
                    calendar.add(
                        Calendar.WEEK_OF_YEAR,
                        1
                    )

                RepeatType.MONTHLY ->
                    calendar.add(
                        Calendar.MONTH,
                        1
                    )

                RepeatType.NONE -> {}

                else -> {}
            }

            alarmScheduler.schedule(
                alarmId = alarmId,
                triggerTimeMillis = calendar.timeInMillis,
                title = title,
                message = message,
                noteId = noteId,
                folderId = folderId,
                entityId = entityId,
                repeatType = repeatType
            )
        }
    }
}