package com.example.todoapp.data.alarm

import androidx.room.TypeConverter
import com.example.todoapp.data.database.RepeatType

class RepeatTypeConverter {

    @TypeConverter
    fun fromRepeatType(type: RepeatType): String {
        return type.name
    }

    @TypeConverter
    fun toRepeatType(value: String): RepeatType {
        return RepeatType.valueOf(value)
    }
}