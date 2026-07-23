package com.example.todoapp.presention.extension

import java.util.Locale


fun Int?.toFormattedTime(
    minute: Int?
): String? {

    if (this == null || minute == null) return null

    return String.format(
        Locale.getDefault(),
        "%02d:%02d",
        this,
        minute
    )
}