package com.example.todoapp.presention.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long?.toFormattedDate(): String? {
    return this?.let {
        SimpleDateFormat(
            "dd MMM yyyy",
            Locale.getDefault()
        ).format(Date(it))
    }
}