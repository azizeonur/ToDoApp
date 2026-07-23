package com.example.todoapp.domain.noteUseCase

import com.example.todoapp.data.database.RepeatType
import java.util.Calendar
import javax.inject.Inject

class CalculateTriggerTimeUseCase @Inject constructor() {

    operator fun invoke(
        selectedDateMillis: Long?,
        hour: Int?,
        minute: Int?,
        repeatType: RepeatType
    ): Long? {

        val h = hour ?: return null
        val m = minute ?: return null

        val calendar = Calendar.getInstance()

        if (repeatType == RepeatType.NONE) {
            selectedDateMillis?.let {
                calendar.timeInMillis = it
            }
        }

        calendar.set(Calendar.HOUR_OF_DAY, h)
        calendar.set(Calendar.MINUTE, m)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            when (repeatType) {
                RepeatType.DAILY ->
                    calendar.add(Calendar.DAY_OF_YEAR, 1)

                RepeatType.WEEKLY ->
                    calendar.add(Calendar.WEEK_OF_YEAR, 1)

                RepeatType.MONTHLY ->
                    calendar.add(Calendar.MONTH, 1)

                RepeatType.NONE -> {}

                else -> {}
            }
        }

        return calendar.timeInMillis
    }
}