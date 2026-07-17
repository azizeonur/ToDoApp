package com.example.todoapp.data.alarm

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.todoapp.data.note.NoteEntity

@Entity(
    tableName = "alarms",
    foreignKeys = [ForeignKey(
        entity = NoteEntity::class,
        parentColumns = ["id"],
        childColumns = ["noteId"],
        onDelete = ForeignKey.Companion.CASCADE
    )],
    indices = [Index("noteId")]
)
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val noteId: Int,
    val triggerTimeMillis: Long,
    val label: String = "",
    val isActive: Boolean = true
)