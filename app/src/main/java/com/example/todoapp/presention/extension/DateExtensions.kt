package com.example.todoapp.presention.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Pair<Int?, Int?>.toFormattedTime(): String? {
    val (hour, minute) = this

    return if (hour != null && minute != null) {
        String.format(
            Locale.getDefault(),
            "%02d:%02d",
            hour,
            minute
        )
    } else {
        null
    }
}